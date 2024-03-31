package xyz.lbres.kotlinutils.set.multiset.const

/**
 * Create a new ConstMultiSet with the results of applying the transform function to each value in the current ConstMultiSet.
 * The [transform] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [mapToConstSetConsistent] method.
 *
 * @param transform (E) -> T: transformation function
 * @return [ConstMultiSet]<T>: new ConstMultiSet with transformed values
 */
inline fun <E, T> ConstMultiSet<E>.mapToConstSet(transform: (E) -> T): ConstMultiSet<T> {
    // avoid overhead of creating list
    val newSet: ConstMutableMultiSet<T> = constMutableMultiSetOf()
    forEach { newSet.add(transform(it)) }

    return newSet
}

/**
 * Create a new ConstMultiSet containing only elements that match the given predicate.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [filterToConstSetConsistent] method.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [ConstMultiSet]<E>: ConstMultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> ConstMultiSet<E>.filterToConstSet(predicate: (E) -> Boolean): ConstMultiSet<E> {
    // avoid overhead of creating list
    val newSet: ConstMutableMultiSet<E> = constMutableMultiSetOf()

    forEach {
        if (predicate(it)) {
            newSet.add(it)
        }
    }

    return newSet
}

/**
 * Create a new ConstMultiSet containing only elements that do not match the given predicate.
 * The [predicate] function can return different values for different occurrences of an element.
 * If the function returns the same value for every occurrence of an element, see the more efficient [filterNotToConstSetConsistent] method.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [ConstMultiSet]<E>: ConstMultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> ConstMultiSet<E>.filterNotToConstSet(predicate: (E) -> Boolean): ConstMultiSet<E> {
    // avoid overhead of creating list
    val newSet: ConstMutableMultiSet<E> = constMutableMultiSetOf()

    forEach {
        if (!predicate(it)) {
            newSet.add(it)
        }
    }

    return newSet
}

/**
 * Create a new ConstMultiSet with the results of applying the transform function to each value in the current ConstMultiSet.
 * Requires a [transform] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [mapToConstSet].
 *
 * @param transform (E) -> T: transformation function, which returns the same value for every occurrence of an element
 * @return [ConstMultiSet]<T>: new ConstMultiSet with transformed values
 */
inline fun <E, T> ConstMultiSet<E>.mapToConstSetConsistent(transform: (E) -> T): ConstMultiSet<T> {
    val newSet: ConstMutableMultiSet<T> = constMutableMultiSetOf()

    distinctValues.forEach {
        val mappedValue = transform(it)
        val count = getCountOf(it)
        // using repeat instead of List(count) to avoid overhead of creating list
        repeat(count) { newSet.add(mappedValue) }
    }

    return newSet
}

/**
 * Create a new ConstMultiSet containing only elements that match the given predicate.
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [filterToConstSet].
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering, which returns the same value for every occurrence of an element
 * @return [ConstMultiSet]<E>: ConstMultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> ConstMultiSet<E>.filterToConstSetConsistent(predicate: (E) -> Boolean): ConstMultiSet<E> {
    val newSet = constMutableMultiSetOf<E>()

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
 * Create a new ConstMultiSet containing only elements that do not match the given predicate.
 * Requires a [predicate] function that returns the same value for every occurrence of an element.
 * To use a function that does not return the same value for every occurrence, see [filterNotToConstSet].
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering, which returns the same value for every occurrence of an element
 * @return [ConstMultiSet]<E>: ConstMultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> ConstMultiSet<E>.filterNotToConstSetConsistent(predicate: (E) -> Boolean): ConstMultiSet<E> {
    val newSet = constMutableMultiSetOf<E>()

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
