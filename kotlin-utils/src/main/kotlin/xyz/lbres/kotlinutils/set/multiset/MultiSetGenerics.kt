package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.min

/**
 * Generic MultiSet plus implementation that can be applied to any two MultiSets.
 *
 * @param multiSet1 [MultiSet]<E>: first MultiSet in addition
 * @param multiSet2 [MultiSet]<E>: first MultiSet in addition
 * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
 */
internal fun <E> genericPlus(multiSet1: MultiSet<E>, multiSet2: MultiSet<E>): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()
    val distinctValues = multiSet1.distinctValues + multiSet2.distinctValues

    distinctValues.forEach { element ->
        val totalCount = multiSet1.getCountOf(element) + multiSet2.getCountOf(element)
        repeat(totalCount) { newSet.add(element) }
    }

    return newSet
}

/**
 * Generic MultiSet minus implementation that can be applied to any two MultiSets.
 *
 * @param multiSet1 [MultiSet]<E>: first MultiSet in subtraction
 * @param multiSet2 [MultiSet]<E>: first MultiSet in subtraction
 * @return [MultiSet]<E>: MultiSet containing the items in the first MultiSet but not the second
 */
internal fun <E> genericMinus(multiSet1: MultiSet<E>, multiSet2: MultiSet<E>): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()
    val distinctValues = multiSet1.distinctValues + multiSet2.distinctValues

    distinctValues.forEach { element ->
        val totalCount = multiSet1.getCountOf(element) - multiSet2.getCountOf(element)
        repeat(totalCount) { newSet.add(element) }
    }

    return newSet
}

/**
 * Generic MultiSet intersect implementation that can be applied to any two MultiSets.
 *
 * @param multiSet1 [MultiSet]<E>: first MultiSet in intersect
 * @param multiSet2 [MultiSet]<E>: first MultiSet in intersect
 * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
 */
internal fun <E> genericIntersect(multiSet1: MultiSet<E>, multiSet2: MultiSet<E>): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()
    val distinctValues = multiSet1.distinctValues intersect multiSet2.distinctValues

    distinctValues.forEach { element ->
        val totalCount = min(multiSet1.getCountOf(element), multiSet2.getCountOf(element))
        repeat(totalCount) { newSet.add(element) }
    }

    return newSet
}
