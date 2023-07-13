package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.iterable.ext.countElement
import kotlin.math.min

/**
 * Partial MultiSet implementation with functionality that can be shared by [MultiSetImpl] and [MutableMultiSetImpl].
 */
internal abstract class AbstractMultiSetImpl<E> : MultiSet<E> {
    /**
     * All distinct values contained in the MultiSet, without any counts.
     */
    override val distinctValues: Set<E>
        get() = elements.toSet()

    /**
     * Elements in the set.
     */
    protected abstract val elements: Collection<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = elements.countElement(element)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean = elements.contains(element)

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

        val counts = getCounts()
        val otherCountsMap = getCounts(elements)

        return otherCountsMap.keys.all {
            it in counts && otherCountsMap.getOrDefault(it, 0) <= counts[it]!!
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

        val counts = getCounts()
        return try {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            // more efficient equality check for AbstractMultiSetImpl
            if (other is AbstractMultiSetImpl<*>) {
                val finalOther = other.toMultiSet() as AbstractMultiSetImpl<E>
                return counts == finalOther.getCounts()
            }

            // less efficient equality check
            val otherDistinct = other.distinctValues
            return distinctValues == otherDistinct && distinctValues.all { counts[it] == other.getCountOf(it) }
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
        return genericBinaryOperation(other, { count, otherCount -> min(count, otherCount) }, useAllValues = false)
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        return genericBinaryOperation(other, Int::minus, useAllValues = false)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        return genericBinaryOperation(other, Int::plus)
    }

    /**
     * Execute a binary operation with another MultiSet, with special handling for the case when [other] is an AbstractMultiSetImpl.
     *
     * @param other [MultiSet]<E>: other set to use in operation
     * @param operation (Int, Int) -> Int: function which takes the count of an element in the current set and the count in the other set as parameters,
     * and returns the new count for the element
     * @param useAllValues [Boolean]: if all values from both sets should be used to generate the elements. If `false`, only the values from this set will be used.
     * Defaults to `true`
     * @return [MultiSet]<E>: new set, where each element has the number of values specified by the operation
     */
    private fun genericBinaryOperation(other: MultiSet<E>, operation: (Int, Int) -> Int, useAllValues: Boolean = true): MultiSet<E> {
        val counts = getCounts()

        val newCounts: Map<E, Int> = if (other is AbstractMultiSetImpl<E>) {
            val otherCounts = other.getCounts()
            val allValues = simpleIf(useAllValues, { counts.keys + otherCounts.keys }, { counts.keys })

            allValues.associateWith {
                val count = counts.getOrDefault(it, 0)
                val otherCount = otherCounts.getOrDefault(it, 0)
                operation(count, otherCount)
            }
        } else {
            val allValues = simpleIf(useAllValues, { counts.keys + other.distinctValues }, { counts.keys })

            allValues.associateWith {
                val count = counts.getOrDefault(it, 0)
                val otherCount = other.getCountOf(it)
                operation(count, otherCount)
            }
        }

        return createFromCountsMap(newCounts.filter { it.value > 0 })
    }

    /**
     * Creating a mapping of each element in the set to the number of occurrences of the element.
     *
     * @return [Map]<E, Int>: mapping where keys are distinct values in the set,
     * and values are the number of occurrences of the element
     */
    protected fun getCounts(): Map<E, Int> = getCounts(elements)

    /**
     * Creating a mapping of each element in a collection to the number of occurrences of the element.
     *
     * @param elements [Collection]<E>: collection to generate map for
     * @return [Map]<E, Int>: mapping where keys are distinct values from [elements],
     * and values are the number of occurrences of the element
     */
    protected fun getCounts(elements: Collection<E>): Map<E, Int> {
        val counts: MutableMap<E, Int> = mutableMapOf()

        elements.forEach {
            counts[it] = counts.getOrDefault(it, 0) + 1
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
    override fun isEmpty(): Boolean = elements.isEmpty()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        if (elements.isEmpty()) {
            return "[]"
        }

        val elementsString = elements.joinToString(", ")
        return "[$elementsString]"
    }

    override fun hashCode(): Int {
        var result = getCounts().hashCode()
        result = 31 * result + "MultiSet".hashCode()
        return result
    }
}
