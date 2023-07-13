package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMultiSet
import xyz.lbres.kotlinutils.iterable.ext.countElement
import kotlin.math.min

/**
 * Partial MultiSet implementation with functionality that can be shared by [MultiSetImpl] and [MutableMultiSetImpl]
 */
internal abstract class AbstractMultiSetImpl<E> : MultiSet<E> {
    /**
     * All distinct values contained in the MultiSet, without any counts
     */
    override val distinctValues: Set<E>
        get() = hashElements.toSet()

    /**
     * Elements used to generate [hashCodes].
     * Used to determine if mutable values have changed.
     */
    protected abstract val hashElements: Collection<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = hashElements.countElement(element)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean = hashElements.contains(element)

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

        val countsMap = createCountsMap()
        val otherCountsMap = createCountsMap(elements)

        return otherCountsMap.keys.all {
            it in countsMap && otherCountsMap.getOrDefault(it, 0) <= countsMap[it]!!
        }
    }

    /**
     * If two MultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: `true` if [other] is a non-null MultiSet which contains the same values as the current set, `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        val countsMap = createCountsMap()
        return try {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            // more efficient equality check for AbstractMultiSetImpl
            if (other is AbstractMultiSetImpl<*>) {
                val finalOther = other.toMultiSet() as AbstractMultiSetImpl<E>
                return countsMap == finalOther.createCountsMap()
            }

            // less efficient equality check
            val otherDistinct = other.distinctValues
            return distinctValues == otherDistinct && distinctValues.all { countsMap[it] == other.getCountOf(it) }
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
        val countsMap = createCountsMap()
        val newCounts = countsMap.keys.associateWith {
            val count = countsMap[it]!!
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
        val countsMap = createCountsMap()
        val newCounts = countsMap.keys.associateWith {
            countsMap[it]!! - other.getCountOf(it)
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
        val countsMap = createCountsMap()
        val allValues = countsMap.keys + other.distinctValues

        val newCounts = allValues.associateWith {
            countsMap.getOrDefault(it, 0) + other.getCountOf(it)
        }

        return createFromCountsMap(newCounts)
    }

    /**
     * Perform any modifications on generated counts before assigning to countsMap property.
     * Default is to perform no modifications. Can be overwritten.
     *
     * @param counts [Map]<E, Int>: generated counts
     * @return [Map]<E, Int>: modified counts map
     */
    protected open fun finalizeCounts(counts: Map<E, Int>): Map<E, Int> = counts

    protected fun createCountsMap(): Map<E, Int> = createCountsMap(hashElements)

    protected fun createCountsMap(elements: Collection<E>): Map<E, Int> {
        val counts: MutableMap<E, Int> = mutableMapOf()

        elements.forEach {
            counts[it] = (counts[it] ?: 0) + 1
        }

        return counts
    }

    /**
     * Initialize a new MultiSet from an existing counts map.
     */
    protected abstract fun createFromCountsMap(counts: Map<E, Int>): MultiSet<E>

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = hashElements.isEmpty() // countsMap.isEmpty()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        if (hashElements.isEmpty()) {
            return "[]"
        }

        val elementsString = hashElements.joinToString(", ")
        return "[$elementsString]"
    }

    override fun hashCode(): Int {
        var result = createCountsMap().hashCode()
        result = 31 * result + "MultiSet".hashCode()
        return result
    }
}
