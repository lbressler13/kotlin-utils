package xyz.lbres.kotlinutils.set.multiset

// TODO unit tests
internal fun <E> createCountsMap(elements: Collection<E>): Map<E, Int> {
    val counts: MutableMap<E, Int> = mutableMapOf()
    elements.forEach {
        counts[it] = counts.getOrDefault(it, 0) + 1
    }

    return counts
}
