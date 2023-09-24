package xyz.lbres.kotlinutils.set.multiset.constimpl

import xyz.lbres.kotlinutils.set.multiset.ConstMultiSet

/**
 * Implementation of [ConstMultiSet]
 *
 * @param elements [Collection]<E>: elements in the set
 */
internal class ConstMultiSetImpl<E>(elements: Collection<E>) : ConstMultiSet<E>(elements)
