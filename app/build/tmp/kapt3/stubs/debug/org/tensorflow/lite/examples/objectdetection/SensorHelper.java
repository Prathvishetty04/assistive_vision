package org.tensorflow.lite.examples.objectdetection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00112\u0006\u0010 \u001a\u00020\rH\u0016J\u0010\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#H\u0016J\u0006\u0010$\u001a\u00020\u001eJ\u0006\u0010%\u001a\u00020\u001eJ\u0006\u0010&\u001a\u00020\u001eR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001b\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\r@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000f\u00a8\u0006\'"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/SensorHelper;", "Landroid/hardware/SensorEventListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "ALPHA_FAST", "", "ALPHA_SLOW", "<set-?>", "azimuth", "getAzimuth", "()F", "azimuthInt", "", "getAzimuthInt", "()I", "gSensor", "Landroid/hardware/Sensor;", "geomagnetic", "", "gravity", "mSensor", "orientation", "rotationMatrix", "sSensor", "sensorManager", "Landroid/hardware/SensorManager;", "stepCount", "getStepCount", "onAccuracyChanged", "", "sensor", "accuracy", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "resetSteps", "start", "stop", "app_debug"})
public final class SensorHelper implements android.hardware.SensorEventListener {
    @org.jetbrains.annotations.NotNull()
    private final android.hardware.SensorManager sensorManager = null;
    @org.jetbrains.annotations.Nullable()
    private final android.hardware.Sensor gSensor = null;
    @org.jetbrains.annotations.Nullable()
    private final android.hardware.Sensor mSensor = null;
    @org.jetbrains.annotations.Nullable()
    private final android.hardware.Sensor sSensor = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] gravity = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] geomagnetic = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] rotationMatrix = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] orientation = null;
    private final float ALPHA_FAST = 0.3F;
    private final float ALPHA_SLOW = 0.1F;
    
    /**
     * Smoothed compass heading in degrees, 0–360, as a Float.
     * 0° = North, 90° = East, 180° = South, 270° = West.
     *
     * Changed from Int → Float so SpatialMapper and DirectionEngine can do
     * sub-degree angle arithmetic without rounding loss.
     */
    private float azimuth = 0.0F;
    private int stepCount = 0;
    
    public SensorHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Smoothed compass heading in degrees, 0–360, as a Float.
     * 0° = North, 90° = East, 180° = South, 270° = West.
     *
     * Changed from Int → Float so SpatialMapper and DirectionEngine can do
     * sub-degree angle arithmetic without rounding loss.
     */
    public final float getAzimuth() {
        return 0.0F;
    }
    
    public final int getAzimuthInt() {
        return 0;
    }
    
    public final int getStepCount() {
        return 0;
    }
    
    public final void start() {
    }
    
    public final void stop() {
    }
    
    @java.lang.Override()
    public void onSensorChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override()
    public void onAccuracyChanged(@org.jetbrains.annotations.Nullable()
    android.hardware.Sensor sensor, int accuracy) {
    }
    
    public final void resetSteps() {
    }
}