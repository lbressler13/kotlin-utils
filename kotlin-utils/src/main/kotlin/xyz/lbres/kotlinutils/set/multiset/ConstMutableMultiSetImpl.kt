package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager
import xyz.lbres.kotlinutils.set.multiset.manager.MultiSetManager

internal class ConstMutableMultiSetImpl<E>(elements: Collection<E>): ConstMutableMultiSet<E>() {
    override val manager: MultiSetManager<E> = ConstMultiSetManager(elements, true)
}
