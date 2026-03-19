package org.tensorflow.lite.examples.objectdetection.fragments;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u001c\u001a\u00020\u0010H\u0002J$\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\rH\u0016J\u0016\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,J\b\u0010-\u001a\u00020&H\u0016J0\u0010.\u001a\u00020&2\u000e\u0010/\u001a\n\u0012\u0004\u0012\u000201\u0018\u0001002\u0006\u00102\u001a\u00020\u00122\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0016J\b\u00106\u001a\u00020&H\u0016J\u001a\u00107\u001a\u00020&2\u0006\u00108\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0010\u00109\u001a\u00020&2\u0006\u0010:\u001a\u00020\rH\u0002J\b\u0010;\u001a\u00020&H\u0002J\u0010\u0010<\u001a\u00020&2\u0006\u0010=\u001a\u00020\rH\u0002J\b\u0010>\u001a\u00020&H\u0002J\u0018\u0010?\u001a\u00020&2\u0006\u0010=\u001a\u00020\r2\u0006\u0010+\u001a\u00020,H\u0002J\u0010\u0010@\u001a\u00020&2\u0006\u0010A\u001a\u00020\rH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00170\u00170\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006B"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/ARFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "()V", "_binding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentArBinding;", "arSession", "Lcom/google/ar/core/Session;", "binding", "getBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentArBinding;", "houseMemory", "", "", "Lcom/google/ar/core/Anchor;", "isScanning", "", "lastGuidanceTime", "", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "speechRecognizerLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "surfaceView", "Landroid/opengl/GLSurfaceView;", "targetObject", "isTracking", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onError", "", "error", "onFrameProcessed", "bitmap", "Landroid/graphics/Bitmap;", "frame", "Lcom/google/ar/core/Frame;", "onPause", "onResults", "results", "", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$Detection;", "inferenceTime", "imageHeight", "", "imageWidth", "onResume", "onViewCreated", "view", "processVoiceCommand", "command", "setupARSurface", "startNavigation", "label", "startVoiceCommand", "updateNavigationGuidance", "updateStatus", "text", "app_debug"})
public final class ARFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener {
    @org.jetbrains.annotations.Nullable()
    private org.tensorflow.lite.examples.objectdetection.databinding.FragmentArBinding _binding;
    @org.jetbrains.annotations.Nullable()
    private com.google.ar.core.Session arSession;
    private android.opengl.GLSurfaceView surfaceView;
    private org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper objectDetectorHelper;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.google.ar.core.Anchor> houseMemory = null;
    private boolean isScanning = false;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String targetObject;
    private long lastGuidanceTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> speechRecognizerLauncher = null;
    
    public ARFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentArBinding getBinding() {
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
    
    private final void setupARSurface() {
    }
    
    private final boolean isTracking() {
        return false;
    }
    
    private final void updateStatus(java.lang.String text) {
    }
    
    public final void onFrameProcessed(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull()
    com.google.ar.core.Frame frame) {
    }
    
    @java.lang.Override()
    public void onResults(@org.jetbrains.annotations.Nullable()
    java.util.List<org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection> results, long inferenceTime, int imageHeight, int imageWidth) {
    }
    
    private final void startVoiceCommand() {
    }
    
    private final void processVoiceCommand(java.lang.String command) {
    }
    
    private final void startNavigation(java.lang.String label) {
    }
    
    private final void updateNavigationGuidance(java.lang.String label, com.google.ar.core.Frame frame) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onError(@org.jetbrains.annotations.NotNull()
    java.lang.String error) {
    }
}