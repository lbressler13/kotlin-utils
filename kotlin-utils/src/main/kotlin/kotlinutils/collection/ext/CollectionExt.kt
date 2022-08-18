package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet

fun <T> Collection<T>.toMultiSet() = MultiSet(this)
