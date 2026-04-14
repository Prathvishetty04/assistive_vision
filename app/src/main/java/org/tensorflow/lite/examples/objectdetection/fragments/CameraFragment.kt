package org.tensorflow.lite.examples.objectdetection.fragments

import BluetoothHelper
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.tensorflow.lite.examples.objectdetection.Directionengine
import org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper
import org.tensorflow.lite.examples.objectdetection.Roommemory
import org.tensorflow.lite.examples.objectdetection.SensorHelper
import org.tensorflow.lite.examples.objectdetection.Spatialmapper
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.roundToInt

class CameraFragment : Fragment(),
    ObjectDetectorHelper.DetectorListener,
    ObjectDetectorHelper.SpatialCallback {

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------
    private lateinit var btHelper: BluetoothHelper
    private var _fragmentCameraBinding: FragmentCameraBinding? = null
    private val binding get() = _fragmentCameraBinding!!

    private lateinit var objectDetectorHelper: ObjectDetectorHelper
    private lateinit var sensorHelper: SensorHelper
    private lateinit var spatialMapper: Spatialmapper
    private lateinit var roomMemory: Roommemory
    private lateinit var directionEngine: Directionengine

    private var toneGenerator: ToneGenerator? = null

    private var isScanning   = false
    private var isNavigating = false
    private var targetLabel: String? = null

    private var lastBeepTime:  Long = 0
    private var lastVoiceTime: Long = 0

    // ── Continuous compass loop ───────────────────────────────────────────────
    // Runs on the main thread every COMPASS_POLL_MS while navigating.
    // Driven purely by time — independent of camera frames — so the user gets
    // live spoken updates and beeps as they physically rotate toward the object.
    private val compassHandler  = Handler(Looper.getMainLooper())
    private val compassRunnable = object : Runnable {
        override fun run() {
            if (isNavigating) {
                updateNavigationState()
                compassHandler.postDelayed(this, COMPASS_POLL_MS)
            }
        }
    }

    // -------------------------------------------------------------------------
    // Voice launcher
    // -------------------------------------------------------------------------

    private val speechLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
            if (res.resultCode == Activity.RESULT_OK) {
                val spoken = res.data
                    ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    ?.firstOrNull() ?: return@registerForActivityResult
                handleVoiceQuery(spoken)
            }
        }

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spatialMapper   = Spatialmapper()
        roomMemory      = Roommemory()
        directionEngine = Directionengine(requireContext())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions(
                arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
                1
            )
        }
        btHelper = BluetoothHelper()

        Thread {
            val connected = btHelper.connect("ESP32_VISION")
            Log.d("BT", "Connected: $connected")
        }.start()

        objectDetectorHelper = ObjectDetectorHelper(
            context                = requireContext(),
            objectDetectorListener = this,
            spatialCallback        = this
        )
        sensorHelper  = SensorHelper(requireContext())
        toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

        // ── Scan button ───────────────────────────────────────────────────────
        binding.scanButton.setOnClickListener {
            if (!isScanning) {
                isScanning   = true
                isNavigating = false
                stopCompassLoop()
                directionEngine.stopNavigation()
                roomMemory.reset()
                objectDetectorHelper.speak("Scan started. Slowly pan the camera around the room.")
                binding.scanButton.text = "Stop Scan"
            } else {
                isScanning = false
                roomMemory.finaliseScan()
                val found = roomMemory.getAllLabels()
                val msg = if (found.isEmpty()) "Scan saved. No objects detected."
                else "Scan saved. I found: ${found.joinToString(", ")}."
                objectDetectorHelper.speak(msg)
                binding.scanButton.text = "Start Scan"
            }
        }

        // ── Voice button ──────────────────────────────────────────────────────
        binding.voiceCommandButton.setOnClickListener {
            if (!roomMemory.scanComplete) {
                objectDetectorHelper.speak("Please scan the room first.")
                return@setOnClickListener
            }
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Say: where is my bottle?")
            }
            speechLauncher.launch(intent)
        }

        setUpCamera()
    }

    override fun onResume() {
        super.onResume()
        sensorHelper.start()
    }

    override fun onPause() {
        super.onPause()
        sensorHelper.stop()
        stopCompassLoop()
    }

    override fun onDestroyView() {
        stopCompassLoop()
        _fragmentCameraBinding = null
        toneGenerator?.release()
        directionEngine.release()
        super.onDestroyView()
    }

    // -------------------------------------------------------------------------
    // Compass loop
    // -------------------------------------------------------------------------

    private fun startCompassLoop() {
        compassHandler.removeCallbacks(compassRunnable)  // no duplicates
        compassHandler.post(compassRunnable)
    }

    private fun stopCompassLoop() {
        compassHandler.removeCallbacks(compassRunnable)
    }

    // -------------------------------------------------------------------------
    // Core navigation update — runs every COMPASS_POLL_MS
    // -------------------------------------------------------------------------

    private fun updateNavigationState() {
        val label   = targetLabel ?: return
        val target  = roomMemory.getBestMatch(label) ?: return
        val heading = sensorHelper.azimuth                    // Float 0–360°

        // Signed delta: positive = turn right, negative = turn left
        var delta = ((target.compassAngle - heading + 360f) % 360f)
        if (delta > 180f) delta -= 360f
        val absDelta = abs(delta)
        // 🔵 Send direction to ESP32
        when {
            absDelta <= 15f -> btHelper.send("S")  // straight
            delta > 0       -> btHelper.send("R")  // right
            else            -> btHelper.send("L")  // left
        }

        // ── Arrived ──────────────────────────────────────────────────────────
        if (absDelta <= ARRIVED_DEG) {
            isNavigating = false
            stopCompassLoop()
            val dist = if (target.estimatedDistanceM > 0)
                ", about ${target.estimatedDistanceM.roundToInt()} metres away" else ""
            directionEngine.speak(
                "${label.replaceFirstChar { it.uppercase() }} is right in front of you$dist."
            )
            triggerArrivalVibration()
            return
        }

        // ── Beep when facing roughly the right direction ──────────────────────
        if (absDelta < BEEP_CONE_DEG) {
            val speed = when {
                target.estimatedDistanceM < 1.5f -> 150L
                target.estimatedDistanceM < 3f   -> 300L
                else                              -> 500L
            }
            val now = System.currentTimeMillis()
            if (now - lastBeepTime > speed) {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 80)
                lastBeepTime = now
            }
        }

        // ── Wrong direction haptic via DirectionEngine ────────────────────────
        directionEngine.updateHeading(heading)

        // ── Spoken instruction every VOICE_REFRESH_MS ────────────────────────
        val now = System.currentTimeMillis()
        if (now - lastVoiceTime > VOICE_REFRESH_MS) {
            lastVoiceTime = now

            val dist = if (target.estimatedDistanceM > 0)
                ", ${target.estimatedDistanceM.roundToInt()} metres away" else ""

            val instruction = when {
                absDelta <= 15f && delta > 0 -> "Almost there, slightly right$dist."
                absDelta <= 15f              -> "Almost there, slightly left$dist."
                absDelta <= 30f && delta > 0 -> "Slightly right$dist."
                absDelta <= 30f              -> "Slightly left$dist."
                absDelta <= 90f && delta > 0 -> "${absDelta.roundToInt()} degrees right$dist."
                absDelta <= 90f              -> "${absDelta.roundToInt()} degrees left$dist."
                delta > 0                    -> "Turn right$dist."
                else                         -> "Turn left$dist."
            }
            directionEngine.speak(instruction)
        }
    }

    // -------------------------------------------------------------------------
    // Voice query handler
    // -------------------------------------------------------------------------

    private fun handleVoiceQuery(spoken: String) {
        Log.d(TAG, "Voice query: $spoken")

        val cleaned = spoken.lowercase()
            .replace(Regex("where\\s+is|find|the|my|a|an"), "")
            .trim()

        // Try cleaned phrase first, then word-by-word fallback
        val query = when {
            roomMemory.getBestMatch(cleaned) != null -> cleaned
            else -> spoken.lowercase().split(" ")
                .lastOrNull { roomMemory.getBestMatch(it) != null } ?: cleaned
        }

        if (roomMemory.getBestMatch(query) == null) {
            directionEngine.speak("Sorry, I don't know where the $query is. Try scanning again.")
            return
        }

        targetLabel   = query
        isNavigating  = true
        lastVoiceTime = 0L   // force immediate instruction on first poll

        // Speak the first instruction right now, then the loop takes over
        val target  = roomMemory.getBestMatch(query)!!
        val heading = sensorHelper.azimuth
        var delta   = ((target.compassAngle - heading + 360f) % 360f)
        if (delta > 180f) delta -= 360f
        val absDelta = abs(delta)
        val dist = if (target.estimatedDistanceM > 0)
            ", about ${target.estimatedDistanceM.roundToInt()} metres away" else ""

        val firstMsg = when {
            absDelta <= ARRIVED_DEG -> "${query.replaceFirstChar { it.uppercase() }} is right ahead$dist."
            delta > 0               -> "Turn right ${absDelta.roundToInt()} degrees$dist."
            else                    -> "Turn left ${absDelta.roundToInt()} degrees$dist."
        }
        directionEngine.speak(firstMsg)

        startCompassLoop()   // ← live updates begin here
    }

    // -------------------------------------------------------------------------
    // SpatialCallback — feeds scan data into RoomMemory
    // -------------------------------------------------------------------------

    override fun onDetectionsForSpatialMap(
        detections: List<ObjectDetectorHelper.Detection>,
        imageHeight: Int,
        imageWidth: Int
    ) {
        if (!isScanning) return
        val objects = spatialMapper.mapDetections(
            detections   = detections,
            compassAngle = sensorHelper.azimuth,
            imageHeight  = imageHeight,
            imageWidth   = imageWidth
        )
        if (objects.isNotEmpty()) roomMemory.addObjects(objects)
    }

    // -------------------------------------------------------------------------
    // DetectorListener — UI overlay only
    // -------------------------------------------------------------------------

    override fun onResults(
        results: List<ObjectDetectorHelper.Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int
    ) {
        if (_fragmentCameraBinding == null) return
        activity?.runOnUiThread {
            binding.overlay.setResults(results ?: emptyList(), imageHeight, imageWidth)
            binding.overlay.invalidate()
        }
    }

    override fun onError(error: String) { Log.e(TAG, error) }

    // -------------------------------------------------------------------------
    // Camera setup (unchanged)
    // -------------------------------------------------------------------------

    private fun setUpCamera() {
        val future = ProcessCameraProvider.getInstance(requireContext())
        future.addListener({
            val provider = future.get()
            val preview  = Preview.Builder().build()
            val analyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build().also {
                    it.setAnalyzer(Executors.newSingleThreadExecutor()) { img ->
                        val bmp = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)
                        img.use { bmp.copyPixelsFromBuffer(img.planes[0].buffer) }
                        objectDetectorHelper.detect(bmp, img.imageInfo.rotationDegrees)
                    }
                }
            provider.unbindAll()
            provider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, analyzer)
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    // -------------------------------------------------------------------------
    // Vibration
    // -------------------------------------------------------------------------

    private fun triggerArrivalVibration() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = requireContext()
                    .getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vm.defaultVibrator.vibrate(
                    VibrationEffect.createWaveform(longArrayOf(0, 200, 100, 200), -1)
                )
            } else {
                @Suppress("DEPRECATION")
                val v = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 200, 100, 200), -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(longArrayOf(0, 200, 100, 200), -1)
                }
            }
        } catch (e: Exception) { Log.e(TAG, "Vibration error: ${e.message}") }
    }

    // -------------------------------------------------------------------------
    companion object {
        private const val TAG              = "CameraFragment"
        private const val COMPASS_POLL_MS  = 200L    // loop cadence
        private const val VOICE_REFRESH_MS = 3_000L  // speak every 3 seconds
        private const val ARRIVED_DEG      = 15f     // within 15° = arrived
        private const val BEEP_CONE_DEG    = 20f     // beep only in 20° cone
    }
}