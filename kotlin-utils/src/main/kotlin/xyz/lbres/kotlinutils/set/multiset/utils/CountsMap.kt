package xyz.lbres.kotlinutils.set.multiset.utils

/**
 * Mapping of occurrences to the number of times that they occur
 */
@JvmInline
internal value class CountsMap<E>(private val counts: Map<E, Int>) {
    /**
     * Distinct elements in the map
     */
    val distinct: Set<E>
        get() = counts.keys

    init {
        if (counts.values.any { it <= 0 }) {
            throw IllegalArgumentException("Counts must be greater than zero")
        }
    }

    /**
     * Get number of occurrences of a single element
     *
     * @param element E: element to get count of
     * @return [Int]: number of occurrences
     */
    fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)

    /**
     * If the map is empty
     *
     * @return [Boolean]: `true` if the map contains 0 elements, `false` otherwise
     */
    fun isEmpty(): Boolean = counts.isEmpty()

    /**
     * If an element exists in the map
     *
     * @param element E: element to check
     * @return [Boolean]: `true` if the element exists in the map, `false` otherwise
     */
    fun contains(element: E): Boolean = counts.contains(element)

    /**
     * If all values in a collection of elements exist in the map
     *
     * @param elements [Collection]<E>: elements to check
     * @return [Boolean]: `true` if the number of occurrences of each element in the given collection is no more
     * than the number of occurrences in the map
     */
    fun containsAll(elements: Collection<E>): Boolean {
        val otherCounts = from(elements)

        return otherCounts.counts.all { (element, otherCount) ->
            otherCount <= getCountOf(element)
        }
    }

    /**
     * Iterate through elements in the map
     *
     * @param function (E, Int) -> Unit: function to apply to each key/value pair
     */
    fun forEach(function: (element: E, count: Int) -> Unit) = counts.forEach(function)

    /**
     * Cast to a [List]
     *
     * @return [List]<E>: list containing exactly the elements that are in the map
     */
    fun toList(): List<E> {
        val list: MutableList<E> = mutableListOf()
        counts.forEach { (element, count) ->
            repeat(count) { list.add(element) }
        }

        return list
    }

    /**
     * Create a string representation of the map
     *
     * @return [String]
     */
    override fun toString(): String {
        if (counts.isEmpty()) {
            return "[]"
        }

        var elementsString = ""
        counts.forEach { (element, count) ->
            elementsString += "$element, ".repeat(count)
        }
        elementsString = elementsString.substring(0 until elementsString.lastIndex - 1) // remove trailing ", "

        return "[$elementsString]"
    }

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
