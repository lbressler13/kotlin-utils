package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMultiSet
import xyz.lbres.kotlinutils.general.ternaryIf

/**
 * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
 *
 * @param transform (E) -> T: transformation function
 * @return [MultiSet]<T>: new MultiSet with transformed values
 */
inline fun <E, T> MultiSet<E>.map(transform: (E) -> T): List<T> {
    val list = distinctValues.flatMap {
        val transformedValue = transform(it)
        List(getCountOf(it)) { transformedValue }
    }

    return list
}

/**
 * Create a new MultiSet containing only elements that match the given predicate.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filter(predicate: (E) -> Boolean): List<E> {
    val list = distinctValues.flatMap { element ->
        val matchesPredicate = predicate(element)
        ternaryIf(matchesPredicate, List(getCountOf(element)) { element }, emptyList())
    }

    return list
}

/**
 * Create a new MultiSet containing only elements that do not match the given predicate.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNot(predicate: (E) -> Boolean): List<E> {
    val list = distinctValues.flatMap { element ->
        val matchesPredicate = predicate(element)
        ternaryIf(matchesPredicate, emptyList(), List(getCountOf(element)) { element })
    }

    return list
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right
 * to current accumulator value and each element.
 *
 * Returns the specified [initial] value if the collection is empty.
 *
 * @param initial [T]: initial value for applying operation
 * @param [operation] (T, E) -> T: function that takes current accumulator value and an element, and calculates the next accumulator value.
 * @return [T]: the accumulated value, or [initial] if the MultiSet is empty
 */
inline fun <E, T> MultiSet<E>.fold(initial: T, operation: (acc: T, E) -> T): T {
    var acc = initial

    distinctValues.forEach {
        val value = it
        val count = getCountOf(it)
        repeat(count) { acc = operation(acc, value) }
    }

    return acc
}

/**
 * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
 *
 * @param transform (E) -> T: transformation function
 * @return [MultiSet]<T>: new MultiSet with transformed values
 */
inline fun <E, T> MultiSet<E>.mapToSet(transform: (E) -> T): MultiSet<T> {
    val newSet: MutableMultiSet<T> = mutableMultiSetOf()

    distinctValues.associateWith {
        val mappedValue = transform(it)
        val count = getCountOf(it)
        newSet.addAll(List(count) { mappedValue })
    }

    return newSet
}

/**
 * Create a new MultiSet containing only elements that match the given predicate.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filterToSet(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = distinctValues.flatMap {
        val element = it
        val matchesPredicate = predicate(element)
        val count = getCountOf(element)
        ternaryIf(matchesPredicate, List(count) { element }, emptyList())
    }.toMultiSet()

    return newSet
}

/**
 * Create a new MultiSet containing only elements that do not match the given predicate.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNotToSet(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = distinctValues.flatMap {
        val element = it
        val matchesPredicate = predicate(element)
        val count = getCountOf(element)
        ternaryIf(matchesPredicate, emptyList(), List(count) { element })
    }.toMultiSet()

    return newSet
}
