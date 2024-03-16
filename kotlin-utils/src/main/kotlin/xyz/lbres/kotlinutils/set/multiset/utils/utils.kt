package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Create a mapping of each element in a collection to the number of occurrences of the element.
 *
 * @param elements [Collection]<E>: collection to generate map for
 * @return [Map]<E, Int>: mapping where keys are distinct values from [elements],
 * and values are the number of occurrences of the element
 */
internal fun <E> createCountsMap(elements: Collection<E>): Map<E, Int> {
    val counts: MutableMap<E, Int> = mutableMapOf()
    elements.forEach {
        counts[it] = counts.getOrDefault(it, 0) + 1
    }

    return counts
}

/**
 * Create a string representation of a counts map
 *
 * @param counts [Map]<E, Int>: map to use in creating string
 * @return [String]: string representation of [counts]
 */
internal fun <E> countsToString(counts: Map<E, Int>): String {
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

/**
 * Create a list containing all values in a counts map.
 * Allows values with count of 0.
 *
 * @param counts [Map]<E, Int>: counts map
 * @return [List]<E>: list containing all values in [counts]
 */
internal fun <E> countsToList(counts: Map<E, Int>): List<E> {
    val list: MutableList<E> = mutableListOf()
    counts.forEach { (element, count) ->
        repeat(count) { list.add(element) }
    }

    return list
}

/**
 * Create hash code for set
 *
 * @param counts [Map]<E, Int>: counts map
 */
internal fun <E> createHashCode(counts: Map<E, Int>): Int {
    val hashCode = counts.hashCode()
    return 31 * hashCode + MultiSet::class.java.name.hashCode()
}
