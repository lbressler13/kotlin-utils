package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.tryDefault
import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import kotlin.math.min

/**
 * Partial MultiSet implementation with functionality that can be shared by [MultiSetImpl] and [MutableMultiSetImpl].
 */
internal abstract class AbstractMultiSetImpl<E> : MultiSet<E> {
    /**
     * All distinct values contained in the MultiSet.
     */
    override val distinctValues: Set<E>
        get() = values.toSet()

    /**
     * Elements in the set.
     */
    protected abstract val values: Collection<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = values.countElement(element)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean = values.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If the collection contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as the collection.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if the current set contains at least as many occurrences of each value as the collection, `false` otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val counts = getCounts()
        val otherCounts = getCounts(elements)

        return otherCounts.all {
            it.key in counts && it.value <= counts[it.key]!!
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

        return tryDefault(false) {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>
            getCounts() == getCounts(other)
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
     * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        return genericBinaryOperation(other, Int::minus, useAllValues = false)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        return genericBinaryOperation(other, Int::plus)
    }

    /**
     * Execute a binary operation with another MultiSet, with special handling for the case of another AbstractMultiSetImpl.
     *
     * @param other [MultiSet]<E>: other set to use in operation
     * @param operation (Int, Int) -> Int: function which uses the count of an element in this set and the count in another set, and returns the new count for the element
     * @param useAllValues [Boolean]: if all values from both sets should be used to generate the new set. If `false`, only the values from this set will be used.
     * Defaults to `true`
     * @return [MultiSet]<E>: new set where each element has the number of occurrences specified by the operation
     */
    private fun genericBinaryOperation(other: MultiSet<E>, operation: (count: Int, otherCount: Int) -> Int, useAllValues: Boolean = true): MultiSet<E> {
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

        return createFromCounts(newCounts.filter { it.value > 0 })
    }

    /**
     * Create a mapping of each element in the set to the number of occurrences of the element.
     *
     * @return [Map]<E, Int>: mapping where keys are distinct elements in the set,
     * and values are the number of occurrences of the element in [values]
     */
    protected fun getCounts(): Map<E, Int> = getCounts(values)

    /**
     * Create a mapping of each element in a collection to the number of occurrences of the element.
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
     * Initialize a new MultiSet from existing counts.
     */
    protected abstract fun createFromCounts(counts: Map<E, Int>): MultiSet<E>

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = values.isEmpty()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        if (values.isEmpty()) {
            return "[]"
        }

        val elementsString = values.joinToString(", ")
        return "[$elementsString]"
    }

    /**
     * Generate hash code for set.
     *
     * @return [Int]
     */
    override fun hashCode(): Int {
        var result = getCounts().hashCode()
        result = 31 * result + MultiSet::class.java.name.hashCode()
        return result
    }
}
