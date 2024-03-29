package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * [MultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMultiSet<E> : MultiSet<E>
