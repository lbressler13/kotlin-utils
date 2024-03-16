package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.math.min

/**
 * Common functionality for ConstMultiSet implementations
 */
internal interface AbstractConstMultiSetImpl<E> {
    val counts: CountsMap<E>

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
            val count = operation(counts.getCountOf(value), other.getCountOf(value))
            if (count > 0) {
                newCounts[value] = count
                repeat(count) { newElements.add(value) }
            }
        }

        return simpleIf(const, { ConstMultiSetImpl(newElements, CountsMap(newCounts)) }, { MultiSetImpl(newElements) })
    }

    fun equalsSet(other: MultiSet<*>): Boolean {
        return if (other is AbstractConstMultiSetImpl<*>) {
            counts == other.counts
        } else {
            counts == CountsMap.from(other)
        }
    }

    fun getHashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
