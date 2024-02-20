package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.countsToList
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

internal class ConstMultiSetManager<E>(private val elements: Collection<E>, private val counts: Map<E, Int>) {

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
        val newCounts = combineCounts(other, Int::plus, true)
        return MultiSetImpl(countsToList(newCounts))
    }
    fun minus(other: MultiSet<E>): MultiSet<E> {
        val newCounts = combineCounts(other, Int::minus, false)
        return MultiSetImpl(countsToList(newCounts))
    }
    fun intersect(other: MultiSet<E>): MultiSet<E> {
        val newCounts = combineCounts(other, { val1, val2 -> min(val1, val2) }, false)
        return MultiSetImpl(countsToList(newCounts))
    }
    fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        val newCounts = combineCounts(other, Int::plus, true)
        return ConstMultiSetImpl(countsToList(newCounts), newCounts)
    }
    fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        val newCounts = combineCounts(other, Int::minus, false)
        return ConstMultiSetImpl(countsToList(newCounts), newCounts)
    }
    infix fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        val newCounts = combineCounts(other, { val1, val2 -> min(val1, val2) }, false)
        return ConstMultiSetImpl(countsToList(newCounts), newCounts)
    }

    // TODO return new multiset
    private fun combineCounts(other: MultiSet<E>, operation: (count: Int, otherCount: Int) -> Int, useAllValues: Boolean): Map<E, Int> {
        val distinctValues = counts.keys
        val values = simpleIf(useAllValues, { distinctValues + other.distinctValues }, { distinctValues })
        return values.associateWith {
            operation(getCountOf(it), other.getCountOf(it))
        }.filter { it.value > 0 }
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
