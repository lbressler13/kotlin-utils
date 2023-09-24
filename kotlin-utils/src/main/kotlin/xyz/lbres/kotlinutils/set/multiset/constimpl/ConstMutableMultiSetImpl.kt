package xyz.lbres.kotlinutils.set.multiset.constimpl

import xyz.lbres.kotlinutils.set.multiset.ConstMutableMultiSet

/**
 * Implementation of [ConstMutableMultiSet]
 *
 * @param elements [Collection]<E>: initial elements in the set
 */
internal class ConstMutableMultiSetImpl<E>(elements: Collection<E>) : ConstMutableMultiSet<E>(elements)
