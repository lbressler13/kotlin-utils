package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * [MultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMultiSet<E> : MultiSet<E> {

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
    abstract fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E>

    /**
     * Alternate version of [minus], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: values to subtract from this set
     * @return [ConstMultiSet]<E>: ConstMultiSet containing the items in this set but not the other
     */
    abstract fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E>

    /**
     * Alternate version of [intersect], which returns a ConstMultiSet
     *
     * @param other [ConstMultiSet]<E>: ConstMultiSet to intersect with current
     * @return [ConstMultiSet]<E>: ConstMultiSet containing only values that are in both sets
     */
    abstract infix fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E>
}
