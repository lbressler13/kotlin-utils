package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMutableMultiSet<E>: MutableMultiSet<E>, ConstMultiSet<E>() {
    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    abstract override fun iterator(): MutableIterator<E>
}
