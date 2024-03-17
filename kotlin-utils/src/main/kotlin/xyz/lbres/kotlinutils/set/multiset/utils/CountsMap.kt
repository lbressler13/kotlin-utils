package xyz.lbres.kotlinutils.set.multiset.utils

/**
 * Mapping of occurrences to the number of times that they occur
 */
@JvmInline
internal value class CountsMap<E>(private val counts: Map<E, Int>) {
    val distinct: Set<E>
        get() = counts.keys

    /**
     * Number of occurrences of a single element
     *
     * @param element E: element to get count of
     * @return [Int]: number of occurrences
     */
    fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)

    companion object {
        /**
         * Create a CountsMap from a collection of elements
         *
         * @param elements [Collection]<E>: elements to include in the map
         * @return [CountsMap]<E>: map containing exactly the elements in the given collection
         */
        fun <E> from(elements: Collection<E>): CountsMap<E> {
            val counts: MutableMap<E, Int> = mutableMapOf()
            elements.forEach {
                counts[it] = counts.getOrDefault(it, 0) + 1
            }

            return CountsMap(counts)
        }
    }
}
