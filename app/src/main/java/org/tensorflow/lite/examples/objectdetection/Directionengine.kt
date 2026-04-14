package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * DirectionEngine converts a stored SpatialObject and the device's current
 * compass heading into:
 *   1. A spoken direction instruction via TTS  ("Turn right, about 2 metres away")
 *   2. A haptic pulse if the user is heading the wrong way
 *
 * Usage (from CameraFragment / voice query handler):
 *
 *   val engine = DirectionEngine(requireContext())
 *
 *   // After user asks "where is my bottle?":
 *   val target = roomMemory.getBestMatch("bottle")
 *   engine.guide(target, currentCompassHeading)
 *
 *   // On every compass update while the user is walking toward the object:
 *   engine.updateHeading(currentCompassHeading)
 *
 *   // When done:
 *   engine.release()
 */
class Directionengine(private val context: Context) : TextToSpeech.OnInitListener {

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private var tts: TextToSpeech? = null
    private var ttsReady = false

    /** The object the user is currently navigating to, null if idle. */
    private var activeTarget: Spatialmapper.SpatialObject? = null

    /** Spoken label used in instructions, e.g. "bottle". */
    private var activeLabel: String = ""

    // -------------------------------------------------------------------------
    // Init
    // -------------------------------------------------------------------------

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.setLanguage(Locale.US)
            ttsReady = true
        } else {
            Log.e(TAG, "TTS init failed")
        }
    }

    // -------------------------------------------------------------------------
    // Core public API
    // -------------------------------------------------------------------------

    /**
     * Start guiding the user toward [target].
     * Speaks the first direction instruction immediately.
     *
     * @param target          SpatialObject from RoomMemory
     * @param currentHeading  Live compass heading (0–360°) from SensorHelper
     * @param queryLabel      The label the user asked for, e.g. "bottle"
     */
    fun guide(
        target: Spatialmapper.SpatialObject?,
        currentHeading: Float,
        queryLabel: String
    ) {
        if (target == null) {
            speak("Sorry, I could not find $queryLabel in my memory. Try scanning the room again.")
            return
        }

        activeTarget = target
        activeLabel  = queryLabel

        val instruction = buildInstruction(target, currentHeading)
        speak(instruction)
    }

    /**
     * Call this on every compass sensor update while navigation is active.
     * Vibrates if the user is turning the wrong way, and gives periodic
     * refreshed instructions.
     *
     * @param currentHeading  Live compass heading (0–360°)
     */
    fun updateHeading(currentHeading: Float) {
        val target = activeTarget ?: return

        val delta = angleDifference(currentHeading, target.compassAngle)

        when {
            abs(delta) <= ARRIVAL_THRESHOLD_DEG -> {
                // User is now facing the object
                speak("${activeLabel.replaceFirstChar { it.uppercase() }} should be right in front of you.")
                activeTarget = null
            }
            isWrongDirection(delta) -> {
                // User is turning away — vibrate to warn
                vibrateWrongDirection()
            }
        }
    }

    /**
     * Refresh the spoken instruction on demand (e.g. user taps screen or shakes
     * device to hear the direction again).
     */
    fun repeatInstruction(currentHeading: Float) {
        val target = activeTarget ?: run {
            speak("No active navigation. Ask me where something is first.")
            return
        }
        speak(buildInstruction(target, currentHeading))
    }

    /** Stop navigation and silence TTS. */
    fun stopNavigation() {
        activeTarget = null
        tts?.stop()
    }

    /** Release all resources. Call from Fragment.onDestroy(). */
    fun release() {
        activeTarget = null
        tts?.stop()
        tts?.shutdown()
    }

    // -------------------------------------------------------------------------
    // Direction instruction builder
    // -------------------------------------------------------------------------

    /**
     * Builds a human-friendly spoken direction string.
     *
     * Examples:
     *   "Turn right about 45 degrees. Bottle is roughly 2 metres away."
     *   "Turn slightly left. Chair is roughly 5 metres away."
     *   "Turn around. Remote is roughly 1 metre away."
     */
    private fun buildInstruction(
        target: Spatialmapper.SpatialObject,
        currentHeading: Float
    ): String {
        val delta    = angleDifference(currentHeading, target.compassAngle)
        val absDelta = abs(delta)
        val label    = target.label.replaceFirstChar { it.uppercase() }

        val turnPhrase = when {
            absDelta <= ARRIVAL_THRESHOLD_DEG          -> "is right ahead of you"
            absDelta <= SLIGHT_TURN_DEG  && delta > 0  -> "Turn slightly right"
            absDelta <= SLIGHT_TURN_DEG  && delta < 0  -> "Turn slightly left"
            absDelta <= MEDIUM_TURN_DEG  && delta > 0  -> "Turn right about ${absDelta.roundToInt()} degrees"
            absDelta <= MEDIUM_TURN_DEG  && delta < 0  -> "Turn left about ${absDelta.roundToInt()} degrees"
            absDelta <= LARGE_TURN_DEG   && delta > 0  -> "Turn right"
            absDelta <= LARGE_TURN_DEG   && delta < 0  -> "Turn left"
            else                                        -> "Turn around"
        }

        val distancePhrase = if (target.estimatedDistanceM == Spatialmapper.UNKNOWN_DISTANCE) {
            ""
        } else {
            val d = target.estimatedDistanceM.roundToInt()
            val unit = if (d == 1) "metre" else "metres"
            ". $label is roughly $d $unit away."
        }

        return if (absDelta <= ARRIVAL_THRESHOLD_DEG) {
            "$label $turnPhrase$distancePhrase"
        } else {
            "$turnPhrase$distancePhrase"
        }
    }

    // -------------------------------------------------------------------------
    // Haptic
    // -------------------------------------------------------------------------

    /**
     * Short double-pulse vibration to signal "wrong direction".
     * Pattern: wait 0 ms, vibrate 80 ms, pause 100 ms, vibrate 80 ms.
     */
    private fun vibrateWrongDirection() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vib = vm.defaultVibrator
                vib.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 80, 100, 80),
                        intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE),
                        -1   // don't repeat
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib.vibrate(
                        VibrationEffect.createWaveform(longArrayOf(0, 80, 100, 80), -1)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    vib.vibrate(longArrayOf(0, 80, 100, 80), -1)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Vibration failed: ${e.message}")
        }
    }

    // -------------------------------------------------------------------------
    // TTS helper
    // -------------------------------------------------------------------------

    internal fun speak(text: String) {
        if (!ttsReady) {
            Log.w(TAG, "TTS not ready, dropping: $text")
            return
        }
        Log.d(TAG, "Speaking: $text")
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "DIR")
    }

    // -------------------------------------------------------------------------
    // Maths helpers
    // -------------------------------------------------------------------------

    /**
     * Signed angle difference from [from] to [to] in range (-180, +180].
     * Positive = clockwise (turn right), Negative = counter-clockwise (turn left).
     */
    private fun angleDifference(from: Float, to: Float): Float {
        var diff = ((to - from) % 360f + 360f) % 360f
        if (diff > 180f) diff -= 360f
        return diff
    }

    /**
     * Returns true when the user's heading movement is taking them further
     * away from the target (used to trigger haptic warning).
     *
     * Simple heuristic: if |delta| > WRONG_DIR_THRESHOLD we consider it "wrong".
     * In a real device this would also compare consecutive heading readings to
     * detect the direction of rotation.
     */
    private fun isWrongDirection(delta: Float): Boolean =
        abs(delta) > WRONG_DIR_THRESHOLD_DEG

    // -------------------------------------------------------------------------
    companion object {
        private const val TAG = "DirectionEngine"

        const val ARRIVAL_THRESHOLD_DEG  = 15f   // within 15° = "right ahead"
        const val SLIGHT_TURN_DEG        = 30f
        const val MEDIUM_TURN_DEG        = 90f
        const val LARGE_TURN_DEG         = 150f
        const val WRONG_DIR_THRESHOLD_DEG = 60f  // vibrate if more than 60° off
    }
}