package kotlinutils.classes.multiset

import kotlinutils.collection.ext.toMultiSet

// O(n)
fun <T> multiSetOf(vararg elements: T): MultiSet<T> = elements.toList().toMultiSet()
