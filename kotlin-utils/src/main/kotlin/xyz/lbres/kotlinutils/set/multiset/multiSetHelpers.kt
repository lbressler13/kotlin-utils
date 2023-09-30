package xyz.lbres.kotlinutils.set.multiset

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
