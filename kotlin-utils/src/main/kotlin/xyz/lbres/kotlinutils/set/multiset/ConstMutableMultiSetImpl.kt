package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager
import xyz.lbres.kotlinutils.set.multiset.manager.MultiSetManager

/**
 * Implementation of [ConstMutableMultiSet]
 *
 * @param elements [Collection]<E>: initial elements in the set
 */
internal class ConstMutableMultiSetImpl<E>(elements: Collection<E>) : ConstMutableMultiSet<E>() {
    override val manager: MultiSetManager<E> = ConstMultiSetManager(elements, true)
}
