package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.countsToList
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

internal class ConstMultiSetManager<E>(private val elements: Collection<E>, private val counts: Map<E, Int>) {
    private val distinctValues: Set<E>
        get() = counts.keys

    fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)
    fun isEmpty(): Boolean = counts.isEmpty()

    fun contains(element: E): Boolean = counts.contains(element)
    fun containsAll(elements: Collection<E>): Boolean {
        val otherCounts = createCountsMap(elements)

        return otherCounts.all { (element, otherCount) ->
            otherCount <= getCountOf(element)
        }
    }

    fun plus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(other, Int::plus, true, const = false)
    }
    fun minus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(other, Int::minus, false, const = false)
    }
    fun intersect(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(other, { val1, val2 -> min(val1, val2) }, false, const = false)
    }
    fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(other, Int::plus, true, const = true) as ConstMultiSet<E>
    }
    fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(other, Int::minus, false, const = true) as ConstMultiSet<E>
    }
    infix fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(other, { val1, val2 -> min(val1, val2) }, false, const = true) as ConstMultiSet<E>
    }

    // TODO return new multiset
    private fun combineCounts(other: MultiSet<E>, operation: (count: Int, otherCount: Int) -> Int, useAllValues: Boolean, const: Boolean): MultiSet<E> {
        val values: Set<E> = simpleIf(useAllValues, { distinctValues + other.distinctValues }, { distinctValues })
        val newCounts: Map<E, Int> = values.associateWith {
            operation(getCountOf(it), other.getCountOf(it))
        }.filter { it.value > 0 }
        val newElements = countsToList(newCounts)

        return simpleIf(const, { ConstMultiSetImpl(newElements, newCounts) }, { MultiSetImpl(newElements) })
    }

    // TODO add equals
    // TODO add toString

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    fun getIterator(): Iterator<E> = elements.iterator()

    /**
     * Get hash code for set.
     *
     * @return [Int]
     */
    fun getHashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
