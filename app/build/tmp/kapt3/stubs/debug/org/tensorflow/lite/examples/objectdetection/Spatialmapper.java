package org.tensorflow.lite.examples.objectdetection;

/**
 * SpatialMapper stores detected objects with the compass heading at the moment
 * they were seen, plus an estimated distance based on bounding-box size.
 *
 * Called from CameraFragment every time ObjectDetectorHelper fires onResults().
 * The app stays in "scan mode" while the user slowly pans the camera around the
 * room. Once scanning is done the snapshot lives in RoomMemory.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00102\u00020\u0001:\u0002\u0010\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ2\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b\u00a8\u0006\u0012"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/Spatialmapper;", "", "()V", "estimateDistance", "", "boundingBox", "Landroid/graphics/RectF;", "imageHeight", "", "mapDetections", "", "Lorg/tensorflow/lite/examples/objectdetection/Spatialmapper$SpatialObject;", "detections", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$Detection;", "compassAngle", "imageWidth", "Companion", "SpatialObject", "app_debug"})
public final class Spatialmapper {
    private static final float REAL_OBJECT_HEIGHT_M = 0.5F;
    private static final float FOV_FACTOR = 0.6F;
    private static final float HORIZONTAL_FOV_DEG = 60.0F;
    public static final float UNKNOWN_DISTANCE = -1.0F;
    @org.jetbrains.annotations.NotNull()
    public static final org.tensorflow.lite.examples.objectdetection.Spatialmapper.Companion Companion = null;
    
    public Spatialmapper() {
        super();
    }
    
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
    public final float estimateDistance(@org.jetbrains.annotations.NotNull()
    android.graphics.RectF boundingBox, int imageHeight) {
        return 0.0F;
    }
    
    /**
     * Process a fresh list of detections from ObjectDetectorHelper.
     * Returns a list of SpatialObjects ready to be stored in RoomMemory.
     *
     * @param detections    Raw detections from the model
     * @param compassAngle  Current compass heading from SensorHelper
     * @param imageHeight   Height of the camera frame in pixels
     * @param imageWidth    Width of the camera frame in pixels
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<org.tensorflow.lite.examples.objectdetection.Spatialmapper.SpatialObject> mapDetections(@org.jetbrains.annotations.NotNull()
    java.util.List<org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection> detections, float compassAngle, int imageHeight, int imageWidth) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/Spatialmapper$Companion;", "", "()V", "FOV_FACTOR", "", "HORIZONTAL_FOV_DEG", "REAL_OBJECT_HEIGHT_M", "UNKNOWN_DISTANCE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    /**
     * A single snapshot of one detected object.
     *
     * @param label        COCO label string, e.g. "bottle", "chair"
     * @param compassAngle Device compass heading (0–360°) at detection time
     * @param estimatedDistanceM  Rough metre estimate from bbox height ratio
     * @param confidence   Model score 0–1
     * @param timestamp    SystemClock.uptimeMillis() at detection time
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\tH\u00c6\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/Spatialmapper$SpatialObject;", "", "label", "", "compassAngle", "", "estimatedDistanceM", "confidence", "timestamp", "", "(Ljava/lang/String;FFFJ)V", "getCompassAngle", "()F", "getConfidence", "getEstimatedDistanceM", "getLabel", "()Ljava/lang/String;", "getTimestamp", "()J", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class SpatialObject {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String label = null;
        private final float compassAngle = 0.0F;
        private final float estimatedDistanceM = 0.0F;
        private final float confidence = 0.0F;
        private final long timestamp = 0L;
        
        public SpatialObject(@org.jetbrains.annotations.NotNull()
        java.lang.String label, float compassAngle, float estimatedDistanceM, float confidence, long timestamp) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLabel() {
            return null;
        }
        
        public final float getCompassAngle() {
            return 0.0F;
        }
        
        public final float getEstimatedDistanceM() {
            return 0.0F;
        }
        
        public final float getConfidence() {
            return 0.0F;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final float component2() {
            return 0.0F;
        }
        
        public final float component3() {
            return 0.0F;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final long component5() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final org.tensorflow.lite.examples.objectdetection.Spatialmapper.SpatialObject copy(@org.jetbrains.annotations.NotNull()
        java.lang.String label, float compassAngle, float estimatedDistanceM, float confidence, long timestamp) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}