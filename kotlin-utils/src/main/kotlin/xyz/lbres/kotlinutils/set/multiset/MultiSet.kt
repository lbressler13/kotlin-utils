package xyz.lbres.kotlinutils.set.multiset

/**
 * Interface for set that allows multiple occurrences of a value.
 * The interface supports only read access to the values.
 * Read/write access is available through the [MutableMultiSet] interface.
 */
interface MultiSet<E> : Collection<E> {
    /**
     * All distinct values contained in the MultiSet
     */
    val distinctValues: Set<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: The number of occurrences of [element]. 0 if the element does not exist.
     */
    fun getCountOf(element: E): Int

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    operator fun minus(other: MultiSet<E>): MultiSet<E>

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    operator fun plus(other: MultiSet<E>): MultiSet<E>

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [MultiSet]<E>: values to intersect with this MultiSet
     * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
     */
    infix fun intersect(other: MultiSet<E>): MultiSet<E>

    companion object {
        /**
         * [plus] implementation that can be used with any [MultiSet] implementation.
         * May be less efficient than class-specific implementations.
         *
         * @param multiSet1 [MultiSet]<E>: first MultiSet in addition
         * @param multiSet2 [MultiSet]<E>: first MultiSet in addition
         * @return [MultiSet]<E>: MultiSet containing all values from both sets
         */
        fun <E> genericPlus(multiSet1: MultiSet<E>, multiSet2: MultiSet<E>): MultiSet<E> = genericMultiSetPlus(multiSet1, multiSet2)

        /**
         * [minus] implementation that can be used with any [MultiSet] implementation.
         * May be less efficient than class-specific implementations.
         *
         * @param multiSet1 [MultiSet]<E>: first MultiSet in subtraction
         * @param multiSet2 [MultiSet]<E>: first MultiSet in subtraction
         * @return [MultiSet]<E>: MultiSet containing the items in the first MultiSet but not the second
         */
        fun <E> genericMinus(multiSet1: MultiSet<E>, multiSet2: MultiSet<E>): MultiSet<E> = genericMultiSetMinus(multiSet1, multiSet2)

        /**
         * [minus] implementation that can be used with any [MultiSet] implementation.
         * May be less efficient than class-specific implementations.
         *
         * @param multiSet1 [MultiSet]<E>: first MultiSet in intersect
         * @param multiSet2 [MultiSet]<E>: first MultiSet in intersect
         * @return [MultiSet]<E>: MultiSet containing only values that are in both sets
         */
        fun <E> genericIntersect(multiSet1: MultiSet<E>, multiSet2: MultiSet<E>): MultiSet<E> = genericMultiSetIntersect(multiSet1, multiSet2)
    }
}
