package org.tensorflow.lite.examples.objectdetection;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0014\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u000289B5\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0002J\u0016\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020\u0005J\u0010\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020\u0005H\u0016J!\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00112\f\u00101\u001a\b\u0012\u0004\u0012\u00020302H\u0002\u00a2\u0006\u0002\u00104J\b\u00105\u001a\u00020*H\u0002J\u000e\u00106\u001a\u00020*2\u0006\u00107\u001a\u00020\u0012R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0014\"\u0004\b\u001a\u0010\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006:"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "Landroid/speech/tts/TextToSpeech$OnInitListener;", "threshold", "", "numThreads", "", "maxResults", "context", "Landroid/content/Context;", "objectDetectorListener", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "(FIILandroid/content/Context;Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;)V", "getContext", "()Landroid/content/Context;", "interpreter", "Lorg/tensorflow/lite/Interpreter;", "labels", "", "", "getMaxResults", "()I", "setMaxResults", "(I)V", "numChannel", "numElements", "getNumThreads", "setNumThreads", "getObjectDetectorListener", "()Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "tensorHeight", "tensorWidth", "getThreshold", "()F", "setThreshold", "(F)V", "tts", "Landroid/speech/tts/TextToSpeech;", "convertBitmapToByteBuffer", "Ljava/nio/ByteBuffer;", "bitmap", "Landroid/graphics/Bitmap;", "detect", "", "image", "imageRotation", "onInit", "status", "processOutput", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$Detection;", "modelOutput", "", "", "([[F)Ljava/util/List;", "setupObjectDetector", "speak", "message", "Detection", "DetectorListener", "app_debug"})
public final class ObjectDetectorHelper implements android.speech.tts.TextToSpeech.OnInitListener {
    private float threshold;
    private int numThreads;
    private int maxResults;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private final org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener objectDetectorListener = null;
    @org.jetbrains.annotations.Nullable()
    private org.tensorflow.lite.Interpreter interpreter;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> labels;
    @org.jetbrains.annotations.Nullable()
    private android.speech.tts.TextToSpeech tts;
    private int tensorWidth = 0;
    private int tensorHeight = 0;
    private int numChannel = 0;
    private int numElements = 0;
    
    public ObjectDetectorHelper(float threshold, int numThreads, int maxResults, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener objectDetectorListener) {
        super();
    }
    
    public final float getThreshold() {
        return 0.0F;
    }
    
    public final void setThreshold(float p0) {
    }
    
    public final int getNumThreads() {
        return 0;
    }
    
    public final void setNumThreads(int p0) {
    }
    
    public final int getMaxResults() {
        return 0;
    }
    
    public final void setMaxResults(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener getObjectDetectorListener() {
        return null;
    }
    
    @java.lang.Override()
    public void onInit(int status) {
    }
    
    private final void setupObjectDetector() {
    }
    
    public final void detect(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, int imageRotation) {
    }
    
    private final java.util.List<org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection> processOutput(float[][] modelOutput) {
        return null;
    }
    
    public final void speak(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    private final java.nio.ByteBuffer convertBitmapToByteBuffer(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J3\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r\u00a8\u0006\u0019"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$Detection;", "", "boundingBox", "Landroid/graphics/RectF;", "categories", "", "", "scores", "", "(Landroid/graphics/RectF;Ljava/util/List;Ljava/util/List;)V", "getBoundingBox", "()Landroid/graphics/RectF;", "getCategories", "()Ljava/util/List;", "getScores", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class Detection {
        @org.jetbrains.annotations.NotNull()
        private final android.graphics.RectF boundingBox = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> categories = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.Float> scores = null;
        
        public Detection(@org.jetbrains.annotations.NotNull()
        android.graphics.RectF boundingBox, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> categories, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.Float> scores) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.graphics.RectF getBoundingBox() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getCategories() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Float> getScores() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.graphics.RectF component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Float> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection copy(@org.jetbrains.annotations.NotNull()
        android.graphics.RectF boundingBox, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> categories, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.Float> scores) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J0\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH&\u00a8\u0006\u000f"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "", "onError", "", "error", "", "onResults", "results", "", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$Detection;", "inferenceTime", "", "imageHeight", "", "imageWidth", "app_debug"})
    public static abstract interface DetectorListener {
        
        public abstract void onError(@org.jetbrains.annotations.NotNull()
        java.lang.String error);
        
        public abstract void onResults(@org.jetbrains.annotations.Nullable()
        java.util.List<org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.Detection> results, long inferenceTime, int imageHeight, int imageWidth);
    }
}