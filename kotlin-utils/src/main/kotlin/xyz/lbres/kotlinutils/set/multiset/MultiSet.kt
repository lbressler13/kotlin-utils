package xyz.lbres.kotlinutils.set.multiset

/**
 * Interface for set that allows multiple occurrences of a value.
 * The interface supports only read access to the values.
 * Read/write access is available through the [MutableMultiSet] interface.
 */
interface MultiSet<E> : Set<E> {
    val distinctValues: Set<E>
    fun getCountOf(element: E): Int
    operator fun minus(other: MultiSet<E>): MultiSet<E>
    operator fun plus(other: MultiSet<E>): MultiSet<E>
}
