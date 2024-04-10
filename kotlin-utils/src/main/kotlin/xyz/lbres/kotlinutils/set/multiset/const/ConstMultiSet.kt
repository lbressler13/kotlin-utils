package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.performIntersect
import xyz.lbres.kotlinutils.set.multiset.utils.performMinus
import xyz.lbres.kotlinutils.set.multiset.utils.performPlus

/**
 * [MultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMultiSet<E> : MultiSet<E> {
    // required to be CountsMap<E>
    protected abstract val counts: Any
    private val _counts: CountsMap<E>
        @Suppress(Suppressions.UNCHECKED_CAST)
        get() = counts as CountsMap<E>

    override fun plus(other: MultiSet<E>): MultiSet<E> = performPlus(_counts, other)
    override fun minus(other: MultiSet<E>): MultiSet<E> = performMinus(_counts, other)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = performIntersect(_counts, other)

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
    fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> = performPlus(_counts, other, true) as ConstMultiSet<E>

    /**
     * Alternate version of [minus], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: values to subtract from this set
     * @return [ConstMultiSet]<E>: ConstMultiSet containing the items in this set but not the other
     */
    fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> = performMinus(_counts, other, true) as ConstMultiSet<E>

    /**
     * Alternate version of [intersect], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: ConstMultiSet to intersect with current
     * @return [ConstMultiSet]<E>: ConstMultiSet containing only values that are in both sets
     */
    infix fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> = performIntersect(_counts, other, true) as ConstMultiSet<E>

    override fun getCountOf(element: E): Int = _counts.getCountOf(element)
    override fun contains(element: E): Boolean = _counts.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = _counts.containsAll(elements)
    override fun isEmpty(): Boolean = _counts.isEmpty()

    override fun hashCode(): Int {
        val hashCode = _counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ConstMultiSet<*>) {
            _counts == other._counts
        } else {
            tryOrDefault(false, listOf(ClassCastException::class)) {
                @Suppress(Suppressions.UNCHECKED_CAST)
                other as MultiSet<E>
                _counts == CountsMap.from(other)
            }
        }
    }
}
