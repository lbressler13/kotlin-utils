package xyz.lbres.kotlinutils.set.multiset

internal abstract class AbstractMultiSet<E>: MultiSet<E> {
    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than zero.
     */
    protected abstract var countsMap: Map<E, Int>

    /**
     * Store the hash codes for all the values in the set.
     * Used to determine if any mutable values have changed.
     */
    protected abstract var hashCodes: Map<Int, Int>

    /**
     * String representation of the set.
     */
    protected abstract var string: String

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int {
        updateValues()
        return getCountWithoutUpdate(element)
    }

    /**
     * Get number of occurrences of a given element, without updating the values in the counts map.
     * Assumes that values have already been updated.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    protected fun getCountWithoutUpdate(element: E): Int = countsMap.getOrDefault(element, 0)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean {
        updateValues()
        return countsMap.contains(element)
    }

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If [elements] contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as [elements].
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if the current set contains at least as many occurrences of each value as [elements], `false` otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        updateValues()
        val newSet = MultiSetImpl(elements)
        return newSet.distinctValues.all {
            getCountWithoutUpdate(it) > 0 && newSet.getCountWithoutUpdate(it) <= getCountWithoutUpdate(it)
        }
    }

    protected abstract fun updateValues()

    protected abstract fun createString(): String

    protected abstract fun getCurrentHashCodes(): Map<Int, Int>

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = countsMap.isEmpty()


    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        updateValues()
        return string
    }
}
