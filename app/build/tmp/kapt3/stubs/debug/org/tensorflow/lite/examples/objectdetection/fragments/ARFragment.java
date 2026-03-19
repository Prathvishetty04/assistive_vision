package org.tensorflow.lite.examples.objectdetection.fragments;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u00060\u0016j\u0002`\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J&\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0014H\u0016J+\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u00062\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040&2\u0006\u0010\'\u001a\u00020(H\u0016\u00a2\u0006\u0002\u0010)J\b\u0010*\u001a\u00020\u0014H\u0016J\b\u0010+\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006,"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/ARFragment;", "Landroidx/fragment/app/Fragment;", "()V", "CAMERA_PERMISSION", "", "CAMERA_PERMISSION_CODE", "", "arSession", "Lcom/google/ar/core/Session;", "displayRotationHelper", "Lorg/tensorflow/lite/examples/objectdetection/helpers/DisplayRotationHelper;", "surfaceView", "Landroid/opengl/GLSurfaceView;", "tapHelper", "Lorg/tensorflow/lite/examples/objectdetection/helpers/TapHelper;", "getTapHelper", "()Lorg/tensorflow/lite/examples/objectdetection/helpers/TapHelper;", "setTapHelper", "(Lorg/tensorflow/lite/examples/objectdetection/helpers/TapHelper;)V", "handleException", "", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "hasCameraPermission", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onRequestPermissionsResult", "requestCode", "permissions", "", "results", "", "(I[Ljava/lang/String;[I)V", "onResume", "requestCameraPermission", "app_debug"})
public final class ARFragment extends androidx.fragment.app.Fragment {
    private final int CAMERA_PERMISSION_CODE = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String CAMERA_PERMISSION = "android.permission.CAMERA";
    @org.jetbrains.annotations.Nullable()
    private com.google.ar.core.Session arSession;
    private android.opengl.GLSurfaceView surfaceView;
    private org.tensorflow.lite.examples.objectdetection.helpers.DisplayRotationHelper displayRotationHelper;
    public org.tensorflow.lite.examples.objectdetection.helpers.TapHelper tapHelper;
    
    public ARFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.tensorflow.lite.examples.objectdetection.helpers.TapHelper getTapHelper() {
        return null;
    }
    
    public final void setTapHelper(@org.jetbrains.annotations.NotNull()
    org.tensorflow.lite.examples.objectdetection.helpers.TapHelper p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    private final boolean hasCameraPermission() {
        return false;
    }
    
    private final void requestCameraPermission() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] results) {
    }
    
    private final void handleException(java.lang.Exception e) {
    }
}