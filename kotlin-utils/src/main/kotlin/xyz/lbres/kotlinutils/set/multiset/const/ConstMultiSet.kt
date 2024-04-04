package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.AbstractMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.combineCounts
import kotlin.math.min

/**
 * [MultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMultiSet<E> : MultiSet<E> {
    protected abstract val counts: CountsMap<E>

    override fun plus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(counts, other, Int::plus, true, const = false)
    }

    override fun minus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(counts, other, Int::minus, true, const = false)
    }

    override fun intersect(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(counts, other, ::min, false, const = false)
    }

    @Suppress(Suppressions.FUNCTION_NAME)
    infix fun `+c`(other: ConstMultiSet<E>): ConstMultiSet<E> = plusC(other)
    @Suppress(Suppressions.FUNCTION_NAME)
    infix fun `-c`(other: ConstMultiSet<E>): ConstMultiSet<E> = minusC(other)

    /**
     * Alternate version of [plus], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: values to add to this set
     * @return [ConstMultiSet]<E>: ConstMultiSet containing all values from both sets
     */
    infix fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(counts, other, Int::plus, true, const = true) as ConstMultiSet<E>
    }

    /**
     * Alternate version of [minus], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: values to subtract from this set
     * @return [ConstMultiSet]<E>: ConstMultiSet containing the items in this set but not the other
     */
    infix fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(counts, other, Int::minus, false, const = true) as ConstMultiSet<E>
    }

    /**
     * Alternate version of [intersect], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: ConstMultiSet to intersect with current
     * @return [ConstMultiSet]<E>: ConstMultiSet containing only values that are in both sets
     */
    infix fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> {
        return combineCounts(counts, other, ::min, false, const = true) as ConstMultiSet<E>
    }

    override fun getCountOf(element: E): Int = counts.getCountOf(element)
    override fun contains(element: E): Boolean = counts.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = counts.containsAll(elements)
    override fun isEmpty(): Boolean = counts.isEmpty()

    override fun hashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is AbstractMultiSetImpl<*> -> counts == CountsMap.from(other)
            is AbstractConstMultiSetImpl<*> -> counts == other.counts
            is MultiSet<*> -> {
                @Suppress(Suppressions.UNCHECKED_CAST)
                other as MultiSet<E>
                size == other.size && counts.distinct.all { counts.getCountOf(it) == other.getCountOf(it) }
            }
            else -> false
        }
    }
}
