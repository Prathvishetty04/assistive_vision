package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.roundToInt

class SensorHelper(context: Context) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // HARDCODED IDs to prevent "Unresolved Reference" errors:
    // 1 = Accelerometer, 2 = Magnetometer, 18 = Step Detector
    private val gSensor: Sensor? = sensorManager.getDefaultSensor(1)
    private val mSensor: Sensor? = sensorManager.getDefaultSensor(2)
    private val sSensor: Sensor? = sensorManager.getDefaultSensor(18)

    private val gravity      = FloatArray(3)
    private val geomagnetic  = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientation  = FloatArray(3)

    // ── Smoothing ─────────────────────────────────────────────────────────────
    // Two alphas:
    //   ALPHA_FAST  (0.3) — used for accelerometer so beep response stays snappy
    //   ALPHA_SLOW  (0.1) — used for magnetometer so compass heading is stable
    //     and direction instructions don't jitter on a slow pan
    private val ALPHA_FAST = 0.3f
    private val ALPHA_SLOW = 0.1f

    // ── Public state ──────────────────────────────────────────────────────────

    /**
     * Smoothed compass heading in degrees, 0–360, as a Float.
     * 0° = North, 90° = East, 180° = South, 270° = West.
     *
     * Changed from Int → Float so SpatialMapper and DirectionEngine can do
     * sub-degree angle arithmetic without rounding loss.
     */
    var azimuth: Float = 0f
        private set

    /**
     * Integer azimuth for any legacy code that still needs it.
     * Derived from [azimuth] so they're always in sync.
     */
    val azimuthInt: Int get() = azimuth.roundToInt()

    var stepCount: Int = 0
        private set

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    fun start() {
        gSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        mSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        sSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST) }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    // ── SensorEventListener ───────────────────────────────────────────────────

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            1 -> {  // Accelerometer — fast alpha for responsive beep
                for (i in 0..2) {
                    gravity[i] = ALPHA_FAST * event.values[i] + (1f - ALPHA_FAST) * gravity[i]
                }
            }
            2 -> {  // Magnetometer — slow alpha for stable compass heading
                for (i in 0..2) {
                    geomagnetic[i] = ALPHA_SLOW * event.values[i] + (1f - ALPHA_SLOW) * geomagnetic[i]
                }
            }
            18 -> { // Step Detector
                stepCount++
            }
        }

        // Recompute azimuth whenever either fusion sensor updates
        if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
            SensorManager.getOrientation(rotationMatrix, orientation)
            // orientation[0] is in radians, convert and normalise to 0–360
            val rawDeg = Math.toDegrees(orientation[0].toDouble()).toFloat()
            azimuth = (rawDeg + 360f) % 360f
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { /* no-op */ }

    // ── Helpers ───────────────────────────────────────────────────────────────

    fun resetSteps() {
        stepCount = 0
    }
}