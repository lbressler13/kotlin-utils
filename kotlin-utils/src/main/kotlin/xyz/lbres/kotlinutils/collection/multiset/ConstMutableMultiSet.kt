package xyz.lbres.kotlinutils.collection.multiset

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMutableMultiSet<E> : MutableMultiSet<E>, ConstMultiSet<E>() {
    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    abstract override fun iterator(): MutableIterator<E>
}

internal abstract class IConstMutableMultiSet<E> : ConstMutableMultiSet<E>()
