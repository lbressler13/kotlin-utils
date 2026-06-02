package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.multiset.MultiSet

/**
 * [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal class MultiSetImpl<E>(elements: Collection<E>) : AbstractMultiSetImpl<E>(elements)
