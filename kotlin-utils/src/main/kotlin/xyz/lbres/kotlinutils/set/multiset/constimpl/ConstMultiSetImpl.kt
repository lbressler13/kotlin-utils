package xyz.lbres.kotlinutils.set.multiset.constimpl

import xyz.lbres.kotlinutils.set.multiset.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager

/**
 * Implementation of [ConstMultiSet]
 *
 * @param elements [Collection]<E>: elements in the set
 */
internal class ConstMultiSetImpl<E>(elements: Collection<E>) : ConstMultiSet<E>(elements) {
    // override val manager: ConstMultiSetManager<E> = ConstMultiSetManager(elements, false)
}
