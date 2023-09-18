package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager

internal class ConstMultiSetImpl<E>(elements: Collection<E>): ConstMultiSet<E>() {
    override val manager: ConstMultiSetManager<E> = ConstMultiSetManager(elements, false)
}
