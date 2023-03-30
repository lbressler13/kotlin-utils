package xyz.lbres.kotlinutils.set.multiset

/**
 * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
 * The [transform] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [mapToSetConsistent] method.
 *
 * @param transform (E) -> T: transformation function
 * @return [MultiSet]<T>: new MultiSet with transformed values
 */
inline fun <E, T> MultiSet<E>.mapToSet(transform: (E) -> T): MultiSet<T> {
    // avoid overhead of creating list
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
    // avoid overhead of creating list
    val newSet: MutableMultiSet<E> = mutableMultiSetOf()

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
    // avoid overhead of creating list
    val newSet: MutableMultiSet<E> = mutableMultiSetOf()

    forEach {
        if (!predicate(it)) {
            newSet.add(it)
        }
    }

    return newSet
}

/**
 * Create a list with the results of applying the transform function to each value in the current MultiSet.
 * Requires a [transform] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [map].
 *
 * @param transform (E) -> T: transformation function, which returns the same value for every occurrence of an element
 * @return [List]<T>: list with transformed values
 */
inline fun <E, T> MultiSet<E>.mapConsistent(transform: (E) -> T): List<T> {
    val list = distinctValues.flatMap {
        val transformedValue = transform(it)
        List(getCountOf(it)) { transformedValue }
    }

    return list
}

/**
 * Create a list containing only elements that match the given predicate.
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [filter].
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering, which returns the same value for every occurrence of an element
 * @return [List]<E>: list containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filterConsistent(predicate: (E) -> Boolean): List<E> {
    val list = distinctValues.flatMap { element ->
        val matchesPredicate = predicate(element)
        if (matchesPredicate) {
            List(getCountOf(element)) { element }
        } else {
            emptyList()
        }
    }

    return list
}

/**
 * Create a list containing only elements that do not match the given predicate.
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [filterNot].
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering, which returns the same value for every occurrence of an element
 * @return [List]<E>: list containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNotConsistent(predicate: (E) -> Boolean): List<E> {
    val list = distinctValues.flatMap { element ->
        val matchesPredicate = predicate(element)
        if (matchesPredicate) {
            emptyList()
        } else {
            List(getCountOf(element)) { element }
        }
    }

    return list
}

/**
 * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
 * Requires a [transform] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [mapToSet].
 *
 * @param transform (E) -> T: transformation function, which returns the same value for every occurrence of an element
 * @return [MultiSet]<T>: new MultiSet with transformed values
 */
inline fun <E, T> MultiSet<E>.mapToSetConsistent(transform: (E) -> T): MultiSet<T> {
    val newSet: MutableMultiSet<T> = mutableMultiSetOf()

    distinctValues.forEach {
        val mappedValue = transform(it)
        val count = getCountOf(it)
        // using repeat instead of List(count) to avoid overhead of creating list
        repeat(count) { newSet.add(mappedValue) }
    }

    return newSet
}

/**
 * Create a new MultiSet containing only elements that match the given predicate.
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [filterToSet].
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering, which returns the same value for every occurrence of an element
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filterToSetConsistent(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    distinctValues.forEach { element ->
        val matchesPredicate = predicate(element)
        val count = getCountOf(element)

        if (matchesPredicate) {
            // using repeat instead of List(count) to avoid overhead of creating list
            repeat(count) { newSet.add(element) }
        }
    }

    return newSet
}

/**
 * Create a new MultiSet containing only elements that do not match the given predicate.
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [filterNotToSet].
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering, which returns the same value for every occurrence of an element
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNotToSetConsistent(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    distinctValues.forEach { element ->
        val matchesPredicate = predicate(element)
        val count = getCountOf(element)

        if (!matchesPredicate) {
            // using repeat instead of List(count) to avoid overhead of creating list
            repeat(count) { newSet.add(element) }
        }
    }

    return newSet
}

/**
 * Determine if at least one element matches the given [predicate].
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [any].
 *
 * @param predicate (E) -> [Boolean]: function which returns the same value for every occurrence of an element
 * @return [Boolean]: `true` if the MultiSet is non-empty and at least one element matches the predicate, or `false` otherwise
 */
inline fun <E> MultiSet<E>.anyConsistent(predicate: (E) -> Boolean): Boolean {
    return distinctValues.any(predicate)
}

/**
 * Determine if all elements match the given [predicate].
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [all].
 *
 * @param predicate (E) -> [Boolean]: function which returns the same value for every occurrence of an element
 * @return [Boolean]: `true` if the MultiSet is empty or all elements match the predicate, or `false` otherwise
 */
inline fun <E> MultiSet<E>.allConsistent(predicate: (E) -> Boolean): Boolean {
    return distinctValues.all(predicate)
}

/**
 * Determine if no element matches the given [predicate].
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [none].
 *
 * @param predicate (E) -> [Boolean]: function which returns the same value for every occurrence of an element
 * @return [Boolean]: `true` if the MultiSet is empty or no elements match the predicate, or `false` otherwise
 */
inline fun <E> MultiSet<E>.noneConsistent(predicate: (E) -> Boolean): Boolean {
    return distinctValues.none(predicate)
}

/**
 * Returns an element yielding the smallest value of the given function or `null` if there are no elements.
 * Requires a [selector] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [minByOrNull].
 *
 * @param selector (E) -> R: function to compare elements, which returns the same value for every occurrence of an element
 * @return [E]?: a value that yields the smallest value from [selector], or `null` if the MultiSet is empty
 */
inline fun <E, R : Comparable<R>> MultiSet<E>.minByOrNullConsistent(selector: (E) -> R): E? {
    if (isEmpty()) {
        return null
    }

    return distinctValues.minByOrNull(selector)
}

/**
 * Returns an element yielding the largest value of the given function or `null` if there are no elements.
 * Requires a [selector] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [maxByOrNull].
 *
 * @param selector (E) -> R: function to compare elements, which returns the same value for every occurrence of an element
 * @return [E]?: a value that yields the largest value from [selector], or `null` if the MultiSet is empty
 */
inline fun <E, R : Comparable<R>> MultiSet<E>.maxByOrNullConsistent(selector: (E) -> R): E? {
    if (isEmpty()) {
        return null
    }

    return distinctValues.maxByOrNull(selector)
}
