package xyz.lbres.kotlinutils.set.multiset

/**
 * Create a list with the results of applying the transform function to each value in the current MultiSet.
 * The [transform] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [mapConsistent] method.
 *
 * @param transform (E) -> T: transformation function
 * @return [List]<T>: list with transformed values
 */
inline fun <E, T> MultiSet<E>.map(transform: (E) -> T): List<T> {
    return toList().map(transform)
}

/**
 * Create a list containing only elements that match the given predicate.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [filterConsistent] method.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [List]<E>: list containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filter(predicate: (E) -> Boolean): List<E> {
    return toList().filter(predicate)
}

/**
 * Create a list containing only elements that do not match the given predicate.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [filterConsistent] method.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [List]<E>: list containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNot(predicate: (E) -> Boolean): List<E> {
    return toList().filterNot(predicate)
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
    forEach { acc = operation(acc, it) }

    return acc
}

/**
 * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
 * The [transform] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [mapToSetConsistent] method.
 *
 * @param transform (E) -> T: transformation function
 * @return [MultiSet]<T>: new MultiSet with transformed values
 */
inline fun <E, T> MultiSet<E>.mapToSet(transform: (E) -> T): MultiSet<T> {
    val newSet: MutableMultiSet<T> = mutableMultiSetOf()
    forEach { newSet.add(transform(it)) }

    return newSet
}

/**
 * Create a new MultiSet containing only elements that match the given predicate.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [filterToSetConsistent] method.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filterToSet(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()
    forEach {
        if (predicate(it)) {
            newSet.add(it)
        }
    }

    return newSet
}

/**
 * Create a new MultiSet containing only elements that do not match the given predicate.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [filterNotToSetConsistent] method.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNotToSet(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()
    forEach {
        if (!predicate(it)) {
            newSet.add(it)
        }
    }

    return newSet
}

/**
 * Returns `true` if at least one element matches the given [predicate]. Returns `false` if MultiSet is empty.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [anyConsistent] method.
 *
 * @param predicate (E) -> [Boolean]
 * @return [Boolean]: `true` if the MultiSet is non-empty and at least one element matches the predicate, `false` otherwise
 */
inline fun <E> MultiSet<E>.any(predicate: (E) -> Boolean): Boolean {
    forEach {
        if (predicate(it)) {
            return true
        }
    }

    return false
}

/**
 * Returns `true` if all elements match the given [predicate]. Returns `true` if MultiSet is empty.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [allConsistent] method.
 *
 * @param predicate (E) -> [Boolean]
 * @return [Boolean]: `true` if the MultiSet is empty or all elements match the predicate, `false` otherwise
 */
inline fun <E> MultiSet<E>.all(predicate: (E) -> Boolean): Boolean {
    forEach {
        if (!predicate(it)) {
            return false
        }
    }

    return true
}

/**
 * Returns `true` if no element matches the given [predicate]. Returns `true` if MultiSet is empty.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [noneConsistent] method.
 *
 * @param predicate (E) -> [Boolean]
 * @return [Boolean]: `true` if the MultiSet is empty or no elements match the predicate, `false` otherwise
 */
inline fun <E> MultiSet<E>.none(predicate: (E) -> Boolean): Boolean {
    forEach {
        if (predicate(it)) {
            return false
        }
    }

    return true
}

/**
 * Returns an element yielding the smallest value of the given function or `null` if there are no elements.
 * May not always return the same element if there are multiple elements which yield the smallest value.
 * The [selector] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [minByOrNullConsistent] method.
 *
 * @param selector (E) -> R: function to compare elements
 * @return [E]?: a value that yields the smallest value from [selector], or `null` if the MultiSet is empty
 */
inline fun <E, R : Comparable<R>> MultiSet<E>.minByOrNull(selector: (E) -> R): E? {
    when (size) {
        0 -> return null
        1 -> return distinctValues.first()
    }

    var minPair: Pair<E?, R?> = Pair(null, null)

    forEach {
        val currentMin = minPair.second
        val result = selector(it)

        if (currentMin == null || result < currentMin) {
            minPair = Pair(it, result)
        }
    }

    return minPair.first
}

/**
 * Returns an element yielding the largest value of the given function or `null` if there are no elements.
 * May not always return the same element if there are multiple elements which yield the largest value.
 * The [selector] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [maxByOrNullConsistent] method.
 *
 * @param selector (E) -> R: function to compare elements
 * @return [E]?: a value that yields the largest value from [selector], or `null` if the MultiSet is empty
 */
inline fun <E, R : Comparable<R>> MultiSet<E>.maxByOrNull(selector: (E) -> R): E? {
    when (size) {
        0 -> return null
        1 -> return distinctValues.first()
    }

    var maxPair: Pair<E?, R?> = Pair(null, null)

    forEach {
        val currentMax = maxPair.second
        val result = selector(it)

        if (currentMax == null || result > currentMax) {
            maxPair = Pair(it, result)
        }
    }

    return maxPair.first
}
