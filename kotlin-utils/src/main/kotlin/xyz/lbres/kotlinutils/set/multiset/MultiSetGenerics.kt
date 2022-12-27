package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMultiSet
import kotlin.math.min

/**
 * [MultiSet.plus] implementation that can be used by any MultiSet.
 * May be less efficient than class-specific implementations.
 *
 * @param other [MultiSet]<E>: values to add to this MultiSet
 * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
 */
fun <E> MultiSet<E>.genericPlus(other: MultiSet<E>): MultiSet<E> {
    val values = (distinctValues + other.distinctValues).flatMap { element ->
        val totalCount = getCountOf(element) + other.getCountOf(element)
        List(totalCount) { element }
    }

    return values.toMultiSet()
}

/**
 * [MultiSet.minus] implementation that can be used by any MultiSet.
 * May be less efficient than class-specific implementations.
 *
 * @param other [MultiSet]<E>: values to subtract from this MultiSet
 * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
 */
fun <E> MultiSet<E>.genericMinus(other: MultiSet<E>): MultiSet<E> {
    val values = distinctValues.flatMap { element ->
        val totalCount = getCountOf(element) - other.getCountOf(element)

        if (totalCount <= 0) {
            emptyList()
        } else {
            List(totalCount) { element }
        }
    }

    return values.toMultiSet()
}

/**
 * [MultiSet.intersect] implementation that can be used by any MultiSet.
 * May be less efficient than class-specific implementations.
 *
 * @param other [MultiSet]<E>: values to intersect with this MultiSet
 * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
 */
fun <E> MultiSet<E>.genericIntersect(other: MultiSet<E>): MultiSet<E> {
    val values = (distinctValues intersect other.distinctValues).flatMap { element ->
        val totalCount = min(getCountOf(element), other.getCountOf(element))
        List(totalCount) { element }
    }

    return values.toMultiSet()
}
