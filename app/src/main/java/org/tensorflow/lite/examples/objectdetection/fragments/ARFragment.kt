package org.tensorflow.lite.examples.objectdetection.fragments
import com.google.ar.core.ArCoreApk
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Pose
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentArBinding
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.sqrt

class ARFragment : Fragment(), ObjectDetectorHelper.DetectorListener {

    private var _binding: FragmentArBinding? = null
    private val binding get() = _binding!!

    private var arSession: Session? = null
    private lateinit var surfaceView: GLSurfaceView
    private lateinit var objectDetectorHelper: ObjectDetectorHelper

    // HOUSE MEMORY: Map of Object Label to its AR Anchor
    private val houseMemory = mutableMapOf<String, Anchor>()
    private var isScanning = false
    private var targetObject: String? = null
    private var lastGuidanceTime: Long = 0

    // Speech Recognition Launcher
    private val speechRecognizerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            spokenText?.let { processVoiceCommand(it) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        objectDetectorHelper = ObjectDetectorHelper(
            context = requireContext(),
            objectDetectorListener = this
        )

        setupARSurface()

        // 1. SCAN BUTTON: Toggle saving mode
        binding.btnScan.setOnClickListener {
            if (isTracking()) {
                isScanning = !isScanning
                val msg = if (isScanning) "Scanning started. Move slowly." else "Scan stopped."
                objectDetectorHelper.speak(msg)
                binding.btnScan.text = if (isScanning) "Stop Scan" else "Start Scan"
                updateStatus(msg)
            } else {
                updateStatus("Waiting for AR to find floor...")
            }
        }

        // 2. VOICE BUTTON: Trigger Speech-to-Text
        binding.btnVoice.setOnClickListener {
            startVoiceCommand()
        }

        // 3. MANUAL FIND: Hardcoded for quick testing
        binding.btnFind.setOnClickListener {
            startNavigation("clock")
        }
    }

    private fun setupARSurface() {
        surfaceView = GLSurfaceView(requireContext()).apply {
            preserveEGLContextOnPause = true
            setEGLContextClientVersion(2) // THIS IS CRITICAL
            setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        }
        binding.arSurfaceViewContainer.addView(surfaceView)
    }

    private fun isTracking(): Boolean {
        val frame = try { arSession?.update() } catch (e: Exception) { null }
        return frame?.camera?.trackingState == TrackingState.TRACKING
    }

    private fun updateStatus(text: String) {
        activity?.runOnUiThread { binding.tvStatus.text = text }
    }

    // Called by ARCoreRenderer every frame
    fun onFrameProcessed(bitmap: Bitmap, frame: Frame) {
        if (isScanning && frame.camera.trackingState == TrackingState.TRACKING) {
            objectDetectorHelper.detect(bitmap, 0)
        }

        targetObject?.let { updateNavigationGuidance(it, frame) }

        // Update status if tracking is lost
        if (frame.camera.trackingState != TrackingState.TRACKING) {
            updateStatus("AR Tracking Lost. Look at the floor.")
        }
    }

    override fun onResults(results: List<ObjectDetectorHelper.Detection>?, inferenceTime: Long, imageHeight: Int, imageWidth: Int) {
        val session = arSession ?: return
        val frame = try { session.update() } catch (e: Exception) { return }
        if (frame.camera.trackingState != TrackingState.TRACKING) return

        results?.forEach { detection ->
            val label = detection.categories.first().lowercase()

            if (isScanning && !houseMemory.containsKey(label)) {
                // Anchor the object 1.5m in front of current camera position
                val cameraPose = frame.camera.pose
                val objectPose = cameraPose.compose(Pose.makeTranslation(0f, 0f, -1.5f))
                val anchor = session.createAnchor(objectPose)

                houseMemory[label] = anchor
                objectDetectorHelper.speak("Saved $label to house memory.")
                updateStatus("Memory: Added $label")
            }
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
        } catch (e: Exception) {
            Toast.makeText(context, "Voice input not supported", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processVoiceCommand(command: String) {
        val target = command.lowercase().split(" ").last() // Simple logic: take last word
        startNavigation(target)
    }

    private fun startNavigation(label: String) {
        if (houseMemory.containsKey(label)) {
            targetObject = label
            objectDetectorHelper.speak("Navigating to $label. Follow my voice.")
        } else {
            objectDetectorHelper.speak("I don't remember where the $label is.")
        }
    }

    private fun updateNavigationGuidance(label: String, frame: Frame) {
        val targetAnchor = houseMemory[label] ?: return
        if (frame.camera.trackingState != TrackingState.TRACKING) return

        val cameraPose = frame.camera.pose
        val targetPose = targetAnchor.pose

        val dx = targetPose.tx() - cameraPose.tx()
        val dz = targetPose.tz() - cameraPose.tz()
        val distance = sqrt((dx * dx + dz * dz).toDouble())

        // Calculate Angle using Camera Quaternion
        val cameraQuat = cameraPose.rotationQuaternion
        val siny_cosp = 2 * (cameraQuat[3] * cameraQuat[1] + cameraQuat[0] * cameraQuat[2])
        val cosy_cosp = 1 - 2 * (cameraQuat[1] * cameraQuat[1] + cameraQuat[2] * cameraQuat[2])
        val cameraYaw = Math.toDegrees(atan2(siny_cosp.toDouble(), cosy_cosp.toDouble()))
        val angleToTarget = Math.toDegrees(atan2(dx.toDouble(), -dz.toDouble()))

        val relativeAngle = (angleToTarget - cameraYaw + 360) % 360

        // Speak every 3 seconds
        if (System.currentTimeMillis() - lastGuidanceTime > 3000) {
            val instruction = when {
                distance < 0.8 -> { targetObject = null; "Arrived at $label." }
                relativeAngle in 25.0..180.0 -> "Turn right."
                relativeAngle in 180.0..335.0 -> "Turn left."
                else -> "Go straight for ${distance.toInt()} meters."
            }
            objectDetectorHelper.speak(instruction)
            updateStatus("Navigating: $instruction")
            lastGuidanceTime = System.currentTimeMillis()
        }
    }

    override fun onResume() {
        super.onResume()

        // 1. Wait 200ms to ensure CameraX from the previous screen is DEAD
        binding.arSurfaceViewContainer.postDelayed({
            try {
                if (arSession == null) {
                    val installStatus = ArCoreApk.getInstance().requestInstall(requireActivity(), true)
                    if (installStatus == ArCoreApk.InstallStatus.INSTALLED) {
                        arSession = Session(requireContext())
                        val config = Config(arSession)
                        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                        config.focusMode = Config.FocusMode.AUTO
                        arSession!!.configure(config)
                    } else {
                        return@postDelayed // Still installing
                    }
                }

                // 2. Only resume if session was created successfully
                arSession?.let {
                    it.resume()
                    surfaceView.setRenderer(ARCoreRenderer(requireContext(), it, this))
                    updateStatus("AR Ready. Move phone slowly.")
                }

            } catch (e: Exception) {
                Log.e("AR_ERROR", "Session start failed", e)
                activity?.runOnUiThread {
                    Toast.makeText(context, "AR Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }, 200)
    }

    override fun onPause() {
        super.onPause()
        arSession?.pause()
    }

    override fun onError(error: String) { Log.e("ARFragment", error) }
}