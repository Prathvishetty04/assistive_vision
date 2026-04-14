package org.tensorflow.lite.examples.objectdetection

import android.graphics.RectF

/**
 * SpatialMapper stores detected objects with the compass heading at the moment
 * they were seen, plus an estimated distance based on bounding-box size.
 *
 * Called from CameraFragment every time ObjectDetectorHelper fires onResults().
 * The app stays in "scan mode" while the user slowly pans the camera around the
 * room. Once scanning is done the snapshot lives in RoomMemory.
 */
class Spatialmapper {

    /**
     * A single snapshot of one detected object.
     *
     * @param label        COCO label string, e.g. "bottle", "chair"
     * @param compassAngle Device compass heading (0–360°) at detection time
     * @param estimatedDistanceM  Rough metre estimate from bbox height ratio
     * @param confidence   Model score 0–1
     * @param timestamp    SystemClock.uptimeMillis() at detection time
     */
    data class SpatialObject(
        val label: String,
        val compassAngle: Float,
        val estimatedDistanceM: Float,
        val confidence: Float,
        val timestamp: Long = System.currentTimeMillis()
    )

    // -------------------------------------------------------------------------
    // Distance estimation
    // -------------------------------------------------------------------------

    /**
     * Rough distance estimate using the bounding-box height as a fraction of the
     * full image height. Assumes an average object height of ~0.5 m and a vertical
     * field-of-view of ~60°.
     *
     * Formula:  distance = (REAL_HEIGHT_M / bbox_height_ratio) * FOV_FACTOR
     *
     * This is a heuristic — replace with depth sensor data once the ESP-32
     * ultrasonic sensor is integrated via Bluetooth.
     */
    fun estimateDistance(boundingBox: RectF, imageHeight: Int): Float {
        if (imageHeight == 0) return UNKNOWN_DISTANCE
        val bboxHeightRatio = (boundingBox.bottom - boundingBox.top) / imageHeight.toFloat()
        if (bboxHeightRatio <= 0f) return UNKNOWN_DISTANCE
        // Clamp to a sensible range (0.3 m – 10 m)
        return (REAL_OBJECT_HEIGHT_M / bboxHeightRatio * FOV_FACTOR).coerceIn(0.3f, 10f)
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Process a fresh list of detections from ObjectDetectorHelper.
     * Returns a list of SpatialObjects ready to be stored in RoomMemory.
     *
     * @param detections    Raw detections from the model
     * @param compassAngle  Current compass heading from SensorHelper
     * @param imageHeight   Height of the camera frame in pixels
     * @param imageWidth    Width of the camera frame in pixels
     */
    fun mapDetections(
        detections: List<ObjectDetectorHelper.Detection>,
        compassAngle: Float,
        imageHeight: Int,
        imageWidth: Int
    ): List<SpatialObject> {
        return detections.mapNotNull { detection ->
            val label = detection.categories.firstOrNull() ?: return@mapNotNull null
            val score = detection.scores.firstOrNull() ?: return@mapNotNull null
            val box   = detection.boundingBox

            // Horizontal offset of bbox centre from image centre → adjust compass angle
            val boxCentreX      = (box.left + box.right) / 2f
            val horizontalRatio = (boxCentreX / imageWidth) - 0.5f   // -0.5 … +0.5
            val angleOffset     = horizontalRatio * HORIZONTAL_FOV_DEG
            val finalAngle      = (compassAngle + angleOffset + 360f) % 360f

            val distance = estimateDistance(box, imageHeight)

            SpatialObject(
                label              = label.lowercase().trim(),
                compassAngle       = finalAngle,
                estimatedDistanceM = distance,
                confidence         = score
            )
        }
    }

    // -------------------------------------------------------------------------
    companion object {
        private const val REAL_OBJECT_HEIGHT_M  = 0.5f   // assumed average object height
        private const val FOV_FACTOR            = 0.6f   // empirical vertical-FOV correction
        private const val HORIZONTAL_FOV_DEG    = 60f    // typical phone horizontal FOV
        const val UNKNOWN_DISTANCE              = -1f
    }
}