package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.AbstractMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl

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
 * Combine counts map and MultiSet, using the given operation
 *
 * @param counts [CountsMap]<E>: counts map
 * @param multiset [MultiSet]<E>: MultiSet to combine with
 * @param operation (Int, Int) -> Int: combination function
 * @param useAllValues [Boolean]: if all values from both sets should be used to generate the new set. If `false`, only the values from this set will be used.
 * @param const [Boolean]: if the returned MultiSet should be a ConstMultiSet
 * @return [MultiSet]<E>: new set where each element has the number of occurrences specified by the operation. If [const] is `true`, the set will be a const multi set.
 * Defaults to `false`.
 */
internal fun <E> combineCounts(counts: CountsMap<E>, multiset: MultiSet<E>, operation: (Int, Int) -> Int, useAllValues: Boolean, const: Boolean = false): MultiSet<E> {
    var otherCount: (E) -> Int = { multiset.getCountOf(it) }
    var otherDistinct: () -> Set<E> = { multiset.distinctValues }

    // increase efficiency for AbstractMultiSetImpl
    if (multiset is AbstractMultiSetImpl<E>) {
        val otherCounts = CountsMap.from(multiset)
        otherCount = { otherCounts.getCountOf(it) }
        otherDistinct = { otherCounts.distinct }
    }

    val values: Set<E> = simpleIf(useAllValues, { counts.distinct + otherDistinct() }, { counts.distinct })
    val newCounts: MutableMap<E, Int> = mutableMapOf()
    val newElements: MutableList<E> = mutableListOf()

    values.forEach { value ->
        val count = operation(counts.getCountOf(value), otherCount(value))
        if (count > 0) {
            newCounts[value] = count
            repeat(count) { newElements.add(value) }
        }
    }

    return simpleIf(const, { ConstMultiSetImpl(newElements, newCounts) }, { MultiSetImpl(newElements) })
}
