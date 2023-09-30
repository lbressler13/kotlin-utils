package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal class MultiSetImpl<E> : AbstractMultiSetImpl<E> {
    /**
     * Initialize set from a collection of values.
     */
    internal constructor(elements: Collection<E>) : super(elements)

    /**
     * Initialize set from existing counts.
     */
    internal constructor(counts: Map<E, Int>) : super(counts)
}
