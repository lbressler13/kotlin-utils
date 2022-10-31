package xyz.lbres.kotlinutils.classes.multiset

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Interface for set that allows multiple occurrences of a value.
 * The interface supports only read access to the values.
 * Read/write access is available through the [MutableMultiSet] interface.
 */
typealias MultiSet<E> = MultiSet<E>
