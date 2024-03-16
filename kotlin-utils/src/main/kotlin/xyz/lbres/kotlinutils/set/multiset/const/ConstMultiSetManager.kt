package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.createHashCode
import kotlin.math.min

/**
 * Manager class for common ConstMultiSet code
 *
 * @param counts [Map]<E, Int>: counts map for set
 */
internal class ConstMultiSetManager<E>(private val counts: CountsMap<E>) {
    fun getCountOf(element: E): Int = counts.getCountOf(element)
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
        return combineCounts(other, ::min, false, const = false)
    }
    fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(other, Int::plus, true, const = true) as ConstMultiSet<E>
    }
    fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(other, Int::minus, false, const = true) as ConstMultiSet<E>
    }
    fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(other, ::min, false, const = true) as ConstMultiSet<E>
    }

    /**
     * Combine counts with another MultiSet, using the given operation
     *
     * @param other [MultiSet]<E>: MultiSet to combine with
     * @param operation (Int, Int) -> Int: combination function
     * @param useAllValues [Boolean]: if all values from both sets should be used to generate the new set. If `false`, only the values from this set will be used.
     * @param const [Boolean]: if the returned MultiSet should be a ConstMultiSet
     * @return [MultiSet]<E>: new set where each element has the number of occurrences specified by the operation. If [const] is `true`, the set will be a const multi set
     */
    private fun combineCounts(other: MultiSet<E>, operation: (count: Int, otherCount: Int) -> Int, useAllValues: Boolean, const: Boolean): MultiSet<E> {
        val values: Set<E> = simpleIf(useAllValues, { counts.distinct + other.distinctValues }, { counts.distinct })
        val newCounts: MutableMap<E, Int> = mutableMapOf()
        val newElements: MutableList<E> = mutableListOf()

        values.forEach { value ->
            val count = operation(getCountOf(value), other.getCountOf(value))
            if (count > 0) {
                newCounts[value] = count
                repeat(count) { newElements.add(value) }
            }
        }

        return simpleIf(const, { ConstMultiSetImpl(newElements, CountsMap(newCounts)) }, { MultiSetImpl(newElements) })
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
            is MultiSet<*> -> counts == CountsMap.from(other)
            else -> false
        }
    }

    fun getHashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
