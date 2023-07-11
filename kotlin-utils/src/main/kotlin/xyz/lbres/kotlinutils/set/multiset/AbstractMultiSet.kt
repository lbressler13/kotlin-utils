package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.min

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

    /**
     * If two MultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: true if [other] is a non-null MultiSet which contains the same values as the current set, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        updateValues()
        return try {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            // TODO
//            if (other is AbstractMultiSet<*>) {
//                other as AbstractMultiSet<E>
//                // other.updateValues()
//                return countsMap == other.countsMap
//            }

            // less efficient equality check
            val otherDistinct = other.distinctValues
            return distinctValues == otherDistinct && distinctValues.all { getCountWithoutUpdate(it) == other.getCountOf(it) }
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
     */
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        updateValues()
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            val count = getCountWithoutUpdate(it)
            val otherCount = other.getCountOf(it)
            min(count, otherCount)
        }.filter { it.value > 0 }

        return createFromCountsMap(newCounts)
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        updateValues()
        val newCounts = distinctValues.associateWith {
            getCountWithoutUpdate(it) - other.getCountOf(it)
        }.filter { it.value > 0 }

        return createFromCountsMap(newCounts)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            getCountWithoutUpdate(it) + other.getCountOf(it)
        }

        return createFromCountsMap(newCounts)
    }

    protected abstract fun createFromCountsMap(counts: Map<E, Int>): MultiSet<E>

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
