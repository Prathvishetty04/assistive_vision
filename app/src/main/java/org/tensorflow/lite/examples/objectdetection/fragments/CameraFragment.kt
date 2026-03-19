package org.tensorflow.lite.examples.objectdetection.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.SensorHelper
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment(), ObjectDetectorHelper.DetectorListener {

    private val TAG = "CameraFragment"

    private var _fragmentCameraBinding: FragmentCameraBinding? = null
    private val fragmentCameraBinding get() = _fragmentCameraBinding!!

    private lateinit var objectDetectorHelper: ObjectDetectorHelper
    private lateinit var bitmapBuffer: Bitmap
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var sensorHelper: SensorHelper
    private var objectMap = mutableMapOf<String, Int>()
    private var isScanning = false

    private val speechRecognizerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            if (spokenText != null) {
                provideGuidance(spokenText)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorHelper.start()
    }

    override fun onPause() {
        super.onPause()
        sensorHelper.stop()
    }

    override fun onDestroyView() {
        // Stop detection and unbind camera before nullifying binding
        imageAnalyzer?.clearAnalyzer()
        cameraProvider?.unbindAll()
        _fragmentCameraBinding = null
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false)
        return fragmentCameraBinding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        objectDetectorHelper = ObjectDetectorHelper(
            context = requireContext(),
            objectDetectorListener = this
        )
        sensorHelper = SensorHelper(requireContext())
        cameraExecutor = Executors.newSingleThreadExecutor()

        fragmentCameraBinding.viewFinder.post {
            setUpCamera()
        }

        // 1. SCAN BUTTON
        fragmentCameraBinding.scanButton.setOnClickListener {
            isScanning = !isScanning
            if (isScanning) {
                objectMap.clear()
                objectDetectorHelper.speak("Starting room scan. Please turn slowly.")
            } else {
                objectDetectorHelper.speak("Room scan complete.")
            }
        }

        // 2. VOICE COMMAND BUTTON
        fragmentCameraBinding.voiceCommandButton.setOnClickListener {
            startVoiceCommand()
        }

        // 3. AR MODE BUTTON (Safe Navigation)
        fragmentCameraBinding.arModeButton.setOnClickListener {
            // Clean up CameraX immediately to release hardware for ARCore
            imageAnalyzer?.clearAnalyzer()
            cameraProvider?.unbindAll()

            // Short delay to allow hardware reset
            view.postDelayed({
                if (isAdded) {
                    findNavController().navigate(R.id.action_cameraFragment_to_ARFragment)
                }
            }, 100)
        }
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases()
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider ?: return
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor) { image ->
                    if (!::bitmapBuffer.isInitialized) {
                        bitmapBuffer = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
                    }
                    detectObjects(image)
                }
            }

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
            preview?.setSurfaceProvider(fragmentCameraBinding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    private fun detectObjects(image: ImageProxy) {
        image.use { bitmapBuffer.copyPixelsFromBuffer(it.planes[0].buffer) }
        val imageRotation = image.imageInfo.rotationDegrees
        objectDetectorHelper.detect(bitmapBuffer, imageRotation)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation = fragmentCameraBinding.viewFinder.display.rotation
    }

    private fun updateMapWithDetections(results: List<ObjectDetectorHelper.Detection>?) {
        results?.forEach { detection ->
            val label = detection.categories.first()
            if (!objectMap.containsKey(label)) {
                objectMap[label] = sensorHelper.azimuth
                Log.d("RoomScan", "Object Added: $label at ${sensorHelper.azimuth}°")
            }
        }
    }

    override fun onResults(
        results: List<ObjectDetectorHelper.Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int
    ) {
        // CRITICAL SAFETY CHECK: Prevent crash during fragment transition
        if (_fragmentCameraBinding == null) return

        activity?.runOnUiThread {
            _fragmentCameraBinding?.let { binding ->
                binding.overlay.setResults(
                    results ?: emptyList(),
                    imageHeight,
                    imageWidth
                )

                if (isScanning) {
                    updateMapWithDetections(results)
                }
                binding.overlay.invalidate()
            }
        }
    }

    override fun onError(error: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startVoiceCommand() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "What do you want to find?")
        }
        try {
            speechRecognizerLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Your device does not support speech input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun provideGuidance(command: String) {
        val targetObject = command.lowercase(Locale.ROOT)
            .replace("find the", "")
            .replace("find my", "")
            .replace("find a", "")
            .replace("where is the", "")
            .trim()

        if (objectMap.containsKey(targetObject)) {
            val targetDirection = objectMap[targetObject]!!
            val currentDirection = sensorHelper.azimuth
            val difference = (targetDirection - currentDirection + 360) % 360

            val instruction = when {
                difference < 15 || difference > 345 -> "You are facing the $targetObject. Walk straight."
                difference <= 180 -> "The $targetObject is to your right. Please turn right."
                else -> "The $targetObject is to your left. Please turn left."
            }
            objectDetectorHelper.speak(instruction)
        } else {
            objectDetectorHelper.speak("Sorry, I have not seen a $targetObject in this room.")
        }
    }
}