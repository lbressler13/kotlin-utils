package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager

internal class ConstMutableMultiSetImpl<E>: ConstMutableMultiSet<E> {
    override val manager: ConstMultiSetManager<E>

    /**
     * Initialize set from a collection of values.
     */
    internal constructor(elements: Collection<E>) {
        manager = ConstMultiSetManager(elements, true)
    }

    /**
     * Initialize set from existing counts.
     */
    internal constructor(counts: Map<E, Int>) {
        manager = ConstMultiSetManager(counts, true)
    }
}
