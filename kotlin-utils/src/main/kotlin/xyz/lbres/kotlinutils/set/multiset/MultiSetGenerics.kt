package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.min

/**
 * [MultiSet.plus] implementation that can be used by any MultiSet.
 * May be less efficient than class-specific implementations.
 *
 * @param other [MultiSet]<E>: values to add to this MultiSet
 * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
 */
fun <E> MultiSet<E>.genericPlus(other: MultiSet<E>): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    (distinctValues + other.distinctValues).forEach { element ->
        val totalCount = getCountOf(element) + other.getCountOf(element)
        repeat(totalCount) { newSet.add(element) }
    }

    return newSet
}

/**
 * [MultiSet.minus] implementation that can be used by any MultiSet.
 * May be less efficient than class-specific implementations.
 *
 * @param other [MultiSet]<E>: values to subtract from this MultiSet
 * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
 */
fun <E> MultiSet<E>.genericMinus(other: MultiSet<E>): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    distinctValues.forEach { element ->
        val totalCount = getCountOf(element) - other.getCountOf(element)
        if (totalCount > 0) {
            repeat(totalCount) { newSet.add(element) }
        }
    }

    return newSet
}

/**
 * [MultiSet.intersect] implementation that can be used by any MultiSet.
 * May be less efficient than class-specific implementations.
 *
 * @param other [MultiSet]<E>: values to intersect with this MultiSet
 * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
 */
fun <E> MultiSet<E>.genericIntersect(other: MultiSet<E>): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    (distinctValues intersect other.distinctValues).forEach { element ->
        val totalCount = min(getCountOf(element), other.getCountOf(element))
        repeat(totalCount) { newSet.add(element) }
    }

    return newSet
}
