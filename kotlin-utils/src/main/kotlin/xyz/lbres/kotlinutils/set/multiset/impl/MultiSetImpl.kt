package xyz.lbres.kotlinutils.set.multiset.impl

// TODO standard multiset cleanup classes

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
