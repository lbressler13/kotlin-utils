package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal class MultiSetImpl<E>(elements: Collection<E>) : AbstractMultiSetImpl<E>(elements)
