package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.combineCounts
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

/**
 * Manager class for common ConstMultiSet code
 *
 * @param counts [Map]<E, Int>: counts map for set
 */
internal class ConstMultiSetManager<E>(private val counts: Map<E, Int>) {
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
        return combineCounts(CountsMap(counts), other, Int::plus, true, const = false)
    }
    fun minus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap(counts), other, Int::minus, false, const = false)
    }
    fun intersect(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap(counts), other, ::min, false, const = false)
    }
    fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(CountsMap(counts), other, Int::plus, true, const = true) as ConstMultiSet<E>
    }
    fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(CountsMap(counts), other, Int::minus, false, const = true) as ConstMultiSet<E>
    }
    fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(CountsMap(counts), other, ::min, false, const = true) as ConstMultiSet<E>
    }

    /**
     * Check equality of the managed set to another MultiSet
     *
     * @param other [Any]?
     * @return [Boolean] `true` if [other] is a MultiSet containing the same elements as the counts map, `false` otherwise
     */
    fun equalsSet(other: Any?): Boolean {
        return when (other) {
            is ConstMultiSetImpl<*> -> counts == other.counts
            is ConstMutableMultiSetImpl<*> -> counts == other.counts
            is MultiSet<*> -> counts == createCountsMap(other)
            else -> false
        }
    }

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
