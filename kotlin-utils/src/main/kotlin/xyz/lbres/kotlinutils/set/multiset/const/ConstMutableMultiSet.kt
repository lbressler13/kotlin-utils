package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMutableMultiSet<E> constructor(initialElements: Collection<E>) : MutableMultiSet<E>, ConstMultiSet<E>(initialElements) {
    override val counts: MutableMap<E, Int> = initializeCounts().toMutableMap()

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    abstract override fun iterator(): MutableIterator<E>
}
