package org.tensorflow.lite.examples.objectdetection.fragments;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\u0018\u0000 I2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001IB\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020%H\u0002J$\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u0001002\b\u00101\u001a\u0004\u0018\u000102H\u0016J\b\u00103\u001a\u00020)H\u0016J&\u00104\u001a\u00020)2\f\u00105\u001a\b\u0012\u0004\u0012\u000207062\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0016J\u0010\u0010;\u001a\u00020)2\u0006\u0010<\u001a\u00020%H\u0016J\b\u0010=\u001a\u00020)H\u0016J0\u0010>\u001a\u00020)2\u000e\u0010?\u001a\n\u0012\u0004\u0012\u000207\u0018\u0001062\u0006\u0010@\u001a\u00020\u00162\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0016J\b\u0010A\u001a\u00020)H\u0016J\u001a\u0010B\u001a\u00020)2\u0006\u0010C\u001a\u00020,2\b\u00101\u001a\u0004\u0018\u000102H\u0016J\b\u0010D\u001a\u00020)H\u0002J\b\u0010E\u001a\u00020)H\u0002J\b\u0010F\u001a\u00020)H\u0002J\b\u0010G\u001a\u00020)H\u0002J\b\u0010H\u001a\u00020)H\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010 \u001a\u0010\u0012\f\u0012\n #*\u0004\u0018\u00010\"0\"0!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006J"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/CameraFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$SpatialCallback;", "()V", "_fragmentCameraBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentCameraBinding;", "binding", "getBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentCameraBinding;", "btHelper", "LBluetoothHelper;", "compassHandler", "Landroid/os/Handler;", "compassRunnable", "Ljava/lang/Runnable;", "directionEngine", "Lorg/tensorflow/lite/examples/objectdetection/Directionengine;", "isNavigating", "", "isScanning", "lastBeepTime", "", "lastVoiceTime", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "roomMemory", "Lorg/tensorflow/lite/examples/objectdetection/Roommemory;", "sensorHelper", "Lorg/tensorflow/lite/examples/objectdetection/SensorHelper;", "spatialMapper", "Lorg/tensorflow/lite/examples/objectdetection/Spatialmapper;", "speechLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "targetLabel", "", "toneGenerator", "Landroid/media/ToneGenerator;", "handleVoiceQuery", "", "spoken", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onDetectionsForSpatialMap", "detections", "", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$Detection;", "imageHeight", "", "imageWidth", "onError", "error", "onPause", "onResults", "results", "inferenceTime", "onResume", "onViewCreated", "view", "setUpCamera", "startCompassLoop", "stopCompassLoop", "triggerArrivalVibration", "updateNavigationState", "Companion", "app_debug"})
public final class CameraFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener, org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.SpatialCallback {
    private BluetoothHelper btHelper;
    @org.jetbrains.annotations.Nullable()
    private org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding _fragmentCameraBinding;
    private org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper objectDetectorHelper;
    private org.tensorflow.lite.examples.objectdetection.SensorHelper sensorHelper;
    private org.tensorflow.lite.examples.objectdetection.Spatialmapper spatialMapper;
    private org.tensorflow.lite.examples.objectdetection.Roommemory roomMemory;
    private org.tensorflow.lite.examples.objectdetection.Directionengine directionEngine;
    @org.jetbrains.annotations.Nullable()
    private android.media.ToneGenerator toneGenerator;
    private boolean isScanning = false;
    private boolean isNavigating = false;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String targetLabel;
    private long lastBeepTime = 0L;
    private long lastVoiceTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler compassHandler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable compassRunnable = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> speechLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CameraFragment";
    private static final long COMPASS_POLL_MS = 200L;
    private static final long VOICE_REFRESH_MS = 3000L;
    private static final float ARRIVED_DEG = 15.0F;
    private static final float BEEP_CONE_DEG = 20.0F;
    @org.jetbrains.annotations.NotNull()
    public static final org.tensorflow.lite.examples.objectdetection.fragments.CameraFragment.Companion Companion = null;
    
    public CameraFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    private final void startCompassLoop() {
    }
    
    private final void stopCompassLoop() {
    }
    
    private final void updateNavigationState() {
    }
    
    private final void handleVoiceQuery(java.lang.String spoken) {
    }
    
    @java.lang.Override()
    public void onDetectionsForSpatialMap(@org.jetbrains.annotations.NotNull()
    java.util.List<org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection> detections, int imageHeight, int imageWidth) {
    }
    
    @java.lang.Override()
    public void onResults(@org.jetbrains.annotations.Nullable()
    java.util.List<org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection> results, long inferenceTime, int imageHeight, int imageWidth) {
    }
    
    @java.lang.Override()
    public void onError(@org.jetbrains.annotations.NotNull()
    java.lang.String error) {
    }
    
    private final void setUpCamera() {
    }
    
    private final void triggerArrivalVibration() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/CameraFragment$Companion;", "", "()V", "ARRIVED_DEG", "", "BEEP_CONE_DEG", "COMPASS_POLL_MS", "", "TAG", "", "VOICE_REFRESH_MS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}