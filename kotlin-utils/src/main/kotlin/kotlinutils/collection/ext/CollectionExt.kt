package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet

/**
 * Create a MultiSet with the elements in the current collection.
 *
 * @return [MultiSet<T>]
 */
fun <T> Collection<T>.toMultiSet() = MultiSet(this)
