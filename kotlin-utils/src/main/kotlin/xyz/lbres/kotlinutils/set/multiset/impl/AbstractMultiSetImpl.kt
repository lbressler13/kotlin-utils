package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.createHashCode
import kotlin.math.min

/**
 * Partial [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal abstract class AbstractMultiSetImpl<E>(initialElements: Collection<E>) : MultiSet<E> {
    protected open val elements: Collection<E> = initialElements

    override val distinctValues: Set<E>
        get() = elements.toSet()

    override val size: Int
        get() = elements.size

    override fun getCountOf(element: E): Int = elements.countElement(element)

    override fun contains(element: E): Boolean = elements.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val counts = getCounts()
        val otherCounts = createCountsMap(elements)

        return otherCounts.all { (element, otherCount) ->
            otherCount <= getCount(counts, element)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is MultiSet<*>) {
            return false
        }

        @Suppress(Suppressions.UNCHECKED_CAST)
        return tryOrDefault(false, listOf(ClassCastException::class)) {
            val counts = getCounts()
            val distinct = counts.keys

            // more efficient equality check for AbstractMultiSetImpl
            if (other is AbstractMultiSetImpl<*>) {
                val otherCounts = other.getCounts()
                counts == otherCounts
            } else {
                other as MultiSet<E>
                size == other.size && distinct.all { counts[it] == other.getCountOf(it) }
            }
        }
    }

    override operator fun plus(other: MultiSet<E>): MultiSet<E> = combineCounts(other, Int::plus)
    override operator fun minus(other: MultiSet<E>): MultiSet<E> = combineCounts(other, Int::minus, useAllValues = false)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = combineCounts(other, ::min, useAllValues = false)

    /**
     * Combine counts with another MultiSet, using the given operation. Has special handling when [other] is an instance of AbstractMultiSetImpl
     *
     * @param other [MultiSet]<E>: other set to use in operation
     * @param operation (Int, Int) -> Int: function which uses the count of an element in this set and the count in another set, and returns the new count for the element
     * @param useAllValues [Boolean]: if all values from both sets should be used to generate the new set. If `false`, only the values from this set will be used.
     * Defaults to `true`
     * @return [MultiSet]<E>: new set where each element has the number of occurrences specified by the operation
     */
    private fun combineCounts(other: MultiSet<E>, operation: (count: Int, otherCount: Int) -> Int, useAllValues: Boolean = true): MultiSet<E> {
        val counts = getCounts()
        val distinct = counts.keys

        var getOtherCount: (E) -> Int = { other.getCountOf(it) }
        var getOtherDistinct: () -> Set<E> = { other.distinctValues }
        // increase efficiency of operation with other AbstractMultiSetImpl
        if (other is AbstractMultiSetImpl<E>) {
            val otherCounts = other.getCounts()
            getOtherCount = { getCount(otherCounts, it) }
            getOtherDistinct = { otherCounts.keys }
        }

        val newElements: MutableList<E> = mutableListOf()

        val values = simpleIf(useAllValues, { distinct + getOtherDistinct() }, { distinct })
        values.forEach {
            val count = getCount(counts, it)
            val otherCount = getOtherCount(it)
            val newCount = operation(count, otherCount)

            repeat(newCount) { _ -> newElements.add(it) }
        }

        return MultiSetImpl(newElements)
    }

    /**
     * Create a mapping of each element in the set to the number of occurrences of the element.
     *
     * @return [Map]<E, Int>: mapping where keys are distinct elements in the set,
     * and values are the number of occurrences of the element in [elements]
     */
    protected fun getCounts(): Map<E, Int> = createCountsMap(elements)

    /**
     * Get number of occurrences of an element in a given counts map
     *
     * @param counts [Map]<E, Int>: counts map
     * @param element E: element to get count of
     * @return [Int]: number of occurrences of the element in the map
     */
    private fun getCount(counts: Map<E, Int>, element: E) = counts.getOrDefault(element, 0)

    override fun isEmpty(): Boolean = elements.isEmpty()
    override fun iterator(): Iterator<E> = elements.toList().iterator()

    override fun toString(): String {
        val elementsString = elements.joinToString(", ")
        return "[$elementsString]"
    }

    override fun hashCode(): Int = createHashCode(getCounts())
}
