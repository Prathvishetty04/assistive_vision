package org.tensorflow.lite.examples.objectdetection.helpers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0006\u0010\u0011\u001a\u00020\rJ\u0006\u0010\u0012\u001a\u00020\rJ\u0016\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\nJ\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/helpers/DisplayRotationHelper;", "Landroid/hardware/display/DisplayManager$DisplayListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "display", "Landroid/view/Display;", "viewportChanged", "", "viewportHeight", "", "viewportWidth", "onDisplayAdded", "", "displayId", "onDisplayChanged", "onDisplayRemoved", "onPause", "onResume", "onSurfaceChanged", "width", "height", "updateSessionIfNeeded", "session", "Lcom/google/ar/core/Session;", "app_debug"})
public final class DisplayRotationHelper implements android.hardware.display.DisplayManager.DisplayListener {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private boolean viewportChanged = false;
    private int viewportWidth = 0;
    private int viewportHeight = 0;
    @org.jetbrains.annotations.NotNull()
    private final android.view.Display display = null;
    
    public DisplayRotationHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void onResume() {
    }
    
    public final void onPause() {
    }
    
    public final void onSurfaceChanged(int width, int height) {
    }
    
    public final void updateSessionIfNeeded(@org.jetbrains.annotations.NotNull()
    com.google.ar.core.Session session) {
    }
    
    @java.lang.Override()
    public void onDisplayAdded(int displayId) {
    }
    
    @java.lang.Override()
    public void onDisplayRemoved(int displayId) {
    }
    
    @java.lang.Override()
    public void onDisplayChanged(int displayId) {
    }
}