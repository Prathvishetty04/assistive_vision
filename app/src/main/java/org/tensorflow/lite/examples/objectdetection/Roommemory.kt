package org.tensorflow.lite.examples.objectdetection

/**
 * RoomMemory holds the complete spatial snapshot of the room built during scan mode.
 *
 * Storage strategy:
 *   - Key  = object label (e.g. "bottle", "chair")
 *   - Value = list of SpatialObjects for that label (there may be multiple chairs)
 *
 * When the user asks "where is my bottle?" we look up "bottle" and return the
 * best (highest-confidence, most-recent) match.
 *
 * Thread safety: all public methods are @Synchronized so they can be called from
 * the camera background thread (writes) and the voice-query main thread (reads).
 */
class Roommemory {

    // label → list of spatial observations, newest first
    private val memory = HashMap<String, MutableList<Spatialmapper.SpatialObject>>()

    // Whether the user has finished scanning and memory is "locked in"
    var scanComplete: Boolean = false
        private set

    // -------------------------------------------------------------------------
    // Write API (called during scan mode)
    // -------------------------------------------------------------------------

    /**
     * Add a batch of newly observed spatial objects.
     * Duplicate entries for the same label are kept; [getBestMatch] picks the winner.
     */
    @Synchronized
    fun addObjects(objects: List<Spatialmapper.SpatialObject>) {
        for (obj in objects) {
            val list = memory.getOrPut(obj.label) { mutableListOf() }
            list.add(0, obj)                           // prepend so index-0 is newest
            if (list.size > MAX_OBSERVATIONS_PER_LABEL) {
                list.removeAt(list.lastIndex)          // drop oldest when over the cap
            }
        }
    }

    /** Call this when the user taps "done scanning". */
    @Synchronized
    fun finaliseScan() {
        scanComplete = true
    }

    /** Wipe memory and start a new scan. */
    @Synchronized
    fun reset() {
        memory.clear()
        scanComplete = false
    }

    // -------------------------------------------------------------------------
    // Read API (called when user asks a query)
    // -------------------------------------------------------------------------

    /**
     * Returns the single best-matching SpatialObject for [query], or null if
     * no match is found.
     *
     * Matching is fuzzy: "my bottle" → checks if any stored label *contains*
     * "bottle", or if "bottle" contains the stored label.
     *
     * Ranking: highest (confidence × recency-weight) wins.
     */
    @Synchronized
    fun getBestMatch(query: String): Spatialmapper.SpatialObject? {
        val normalised = query.lowercase().trim()

        val candidates = memory.entries
            .filter { (label, _) ->
                label.contains(normalised) || normalised.contains(label)
            }
            .flatMap { (_, objects) -> objects }

        if (candidates.isEmpty()) return null

        val now = System.currentTimeMillis()
        return candidates.maxByOrNull { obj ->
            val ageSeconds = (now - obj.timestamp) / 1000f
            // Recency weight: halves every 5 minutes
            val recencyWeight = Math.pow(0.5, (ageSeconds / 300.0)).toFloat()
            obj.confidence * recencyWeight
        }
    }

    /**
     * Returns all stored labels so the app can tell the user what was found
     * during scanning, e.g. "I found: chair, bottle, remote, laptop".
     */
    @Synchronized
    fun getAllLabels(): List<String> = memory.keys.sorted()

    /** True if at least one object has been stored. */
    @Synchronized
    fun isEmpty(): Boolean = memory.isEmpty()

    // -------------------------------------------------------------------------
    companion object {
        /** Cap per label to keep memory bounded (phone RAM friendly). */
        private const val MAX_OBSERVATIONS_PER_LABEL = 20
    }
}