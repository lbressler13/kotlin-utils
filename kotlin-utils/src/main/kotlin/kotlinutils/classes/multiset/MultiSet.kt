package kotlinutils.classes.multiset

interface MultiSet<E> : Set<E> {
    fun getCountOf(element: E): Int
}
