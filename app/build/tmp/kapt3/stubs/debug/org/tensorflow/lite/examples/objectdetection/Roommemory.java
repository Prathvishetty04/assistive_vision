package org.tensorflow.lite.examples.objectdetection;

/**
 * RoomMemory holds the complete spatial snapshot of the room built during scan mode.
 *
 * Storage strategy:
 *  - Key  = object label (e.g. "bottle", "chair")
 *  - Value = list of SpatialObjects for that label (there may be multiple chairs)
 *
 * When the user asks "where is my bottle?" we look up "bottle" and return the
 * best (highest-confidence, most-recent) match.
 *
 * Thread safety: all public methods are @Synchronized so they can be called from
 * the camera background thread (writes) and the voice-query main thread (reads).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010J\u0006\u0010\u0011\u001a\u00020\u000eJ\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\u0005J\u0006\u0010\u0015\u001a\u00020\tJ\u0006\u0010\u0016\u001a\u00020\u000eR \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/Roommemory;", "", "()V", "memory", "Ljava/util/HashMap;", "", "", "Lorg/tensorflow/lite/examples/objectdetection/Spatialmapper$SpatialObject;", "<set-?>", "", "scanComplete", "getScanComplete", "()Z", "addObjects", "", "objects", "", "finaliseScan", "getAllLabels", "getBestMatch", "query", "isEmpty", "reset", "Companion", "app_debug"})
public final class Roommemory {
    @org.jetbrains.annotations.NotNull()
    private final java.util.HashMap<java.lang.String, java.util.List<org.tensorflow.lite.examples.objectdetection.Spatialmapper.SpatialObject>> memory = null;
    private boolean scanComplete = false;
    
    /**
     * Cap per label to keep memory bounded (phone RAM friendly).
     */
    private static final int MAX_OBSERVATIONS_PER_LABEL = 20;
    @org.jetbrains.annotations.NotNull()
    public static final org.tensorflow.lite.examples.objectdetection.Roommemory.Companion Companion = null;
    
    public Roommemory() {
        super();
    }
    
    public final boolean getScanComplete() {
        return false;
    }
    
    /**
     * Add a batch of newly observed spatial objects.
     * Duplicate entries for the same label are kept; [getBestMatch] picks the winner.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void addObjects(@org.jetbrains.annotations.NotNull()
    java.util.List<org.tensorflow.lite.examples.objectdetection.Spatialmapper.SpatialObject> objects) {
    }
    
    /**
     * Call this when the user taps "done scanning".
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void finaliseScan() {
    }
    
    /**
     * Wipe memory and start a new scan.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized void reset() {
    }
    
    /**
     * Returns the single best-matching SpatialObject for [query], or null if
     * no match is found.
     *
     * Matching is fuzzy: "my bottle" → checks if any stored label *contains*
     * "bottle", or if "bottle" contains the stored label.
     *
     * Ranking: highest (confidence × recency-weight) wins.
     */
    @kotlin.jvm.Synchronized()
    @org.jetbrains.annotations.Nullable()
    public final synchronized org.tensorflow.lite.examples.objectdetection.Spatialmapper.SpatialObject getBestMatch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    /**
     * Returns all stored labels so the app can tell the user what was found
     * during scanning, e.g. "I found: chair, bottle, remote, laptop".
     */
    @kotlin.jvm.Synchronized()
    @org.jetbrains.annotations.NotNull()
    public final synchronized java.util.List<java.lang.String> getAllLabels() {
        return null;
    }
    
    /**
     * True if at least one object has been stored.
     */
    @kotlin.jvm.Synchronized()
    public final synchronized boolean isEmpty() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/Roommemory$Companion;", "", "()V", "MAX_OBSERVATIONS_PER_LABEL", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}