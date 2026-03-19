package org.tensorflow.lite.examples.objectdetection.helpers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0016J\b\u0010\u000f\u001a\u0004\u0018\u00010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/helpers/TapHelper;", "Landroid/view/View$OnTouchListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "gestureDetector", "Landroid/view/GestureDetector;", "queuedSingleTaps", "Ljava/util/concurrent/BlockingQueue;", "Landroid/view/MotionEvent;", "onTouch", "", "v", "Landroid/view/View;", "event", "poll", "app_debug"})
public final class TapHelper implements android.view.View.OnTouchListener {
    @org.jetbrains.annotations.NotNull()
    private final android.view.GestureDetector gestureDetector = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.BlockingQueue<android.view.MotionEvent> queuedSingleTaps = null;
    
    public TapHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.view.MotionEvent poll() {
        return null;
    }
    
    @java.lang.Override()
    public boolean onTouch(@org.jetbrains.annotations.NotNull()
    android.view.View v, @org.jetbrains.annotations.NotNull()
    android.view.MotionEvent event) {
        return false;
    }
}