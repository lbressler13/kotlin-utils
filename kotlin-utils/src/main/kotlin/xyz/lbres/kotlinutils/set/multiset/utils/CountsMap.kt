package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import kotlin.math.max
import kotlin.math.min

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
     * than the number of occurrences in the map, `false` otherwise
     */
    fun containsAll(elements: Collection<E>): Boolean {
        val otherCounts = from(elements)

        return otherCounts.counts.all { (element, otherCount) ->
            otherCount <= getCountOf(element)
        }
    }

    /**
     * Create a new CountsMap with all values from both maps.
     * If there are multiple occurrences of a value, the number of occurrences in the other map will be added to the number in this CountsMap.
     *
     * @param other [CountsMap]<E>: values to add to this CountsMap
     * @return [CountsMap]<E>: CountsMap containing all values from both CountsMaps
     */
    operator fun plus(other: CountsMap<E>): CountsMap<E> {
        val allKeys = distinct + other.distinct
        val newCounts = allKeys.associateWith { getCountOf(it) + other.getCountOf(it) }
        return CountsMap(newCounts)
    }

    /**
     * Create a new CountsMap with values that are in this map but not the other map.
     * If there are multiple occurrences of a value, the number of occurrences in the other map will be subtracted from the number in this map.
     *
     * @param other [CountsMap]<E>: values to subtract from this CountsMap
     * @return [CountsMap]<E>: CountsMap containing the items in this CountsMap but not the other
     */
    operator fun minus(other: CountsMap<E>): CountsMap<E> {
        val allKeys = distinct + other.distinct
        val newCounts = allKeys
            .associateWith { max(getCountOf(it) - other.getCountOf(it), 0) }
            .filter { it.value > 0 }
        return CountsMap(newCounts)
    }

    /**
     * Create a new CountsMap with values that are shared between the maps.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [CountsMap]<E>: values to intersect with this CountsMap
     * @return [CountsMap]<E>: CountsMap containing only values that are in both CountsMap
     */
    infix fun intersect(other: CountsMap<E>): CountsMap<E> {
        val newCounts = counts
            .mapValues { min(getCountOf(it.key), other.getCountOf(it.key)) }
            .filter { it.value > 0 }
        return CountsMap(newCounts)
    }

    /**
     * Iterate through elements in the map
     *
     * @param action (E, Int) -> Unit: action to apply to each key/value pair
     */
    fun forEach(action: (element: E, count: Int) -> Unit) = counts.forEach(action)

    /**
     * Cast to list
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

    override fun toString(): String {
        var elementsString = ""
        counts.forEach { (element, count) ->
            val repeats = simpleIf(elementsString.isEmpty(), count - 1, count)

            if (elementsString.isEmpty()) {
                elementsString = element.toString()
            }
            elementsString += ", $element".repeat(repeats)
        }

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
            @Suppress(Suppressions.UNCHECKED_CAST)
            try {
                when (elements) {
                    is ConstMultiSetImpl<*> -> return elements.toCountsMap() as CountsMap<E>
                    is ConstMutableMultiSetImpl<*> -> return elements.toCountsMap() as CountsMap<E>
                }
            } catch (_: ClassCastException) {}

            val counts: MutableMap<E, Int> = mutableMapOf()
            elements.forEach {
                counts[it] = counts.getOrDefault(it, 0) + 1
            }

            return CountsMap(counts)
        }
    }
}
