package xyz.lbres.kotlinutils.set.multiset

/**
 * Create a list with the results of applying the transform function to each value in the current MultiSet.
 *
 * @param transform (E) -> T: transformation function
 * @return [List]<T>: list with transformed values
 */
inline fun <E, T> MultiSet<E>.map(transform: (E) -> T): List<T> {
    val list = distinctValues.flatMap {
        val transformedValue = transform(it)
        List(getCountOf(it)) { transformedValue }
    }

    return list
}

/**
 * Create a list containing only elements that match the given predicate.
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [List]<E>: list containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filter(predicate: (E) -> Boolean): List<E> {
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
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [List]<E>: list containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNot(predicate: (E) -> Boolean): List<E> {
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
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `true`
 */
inline fun <E> MultiSet<E>.filterToSet(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    distinctValues.forEach {
        val element = it
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
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `false`
 */
inline fun <E> MultiSet<E>.filterNotToSet(predicate: (E) -> Boolean): MultiSet<E> {
    val newSet = mutableMultiSetOf<E>()

    distinctValues.forEach {
        val element = it
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
 * Returns `true` if at least one element matches the given [predicate].
 *
 * @param predicate (E) -> [Boolean]
 */
inline fun <E> MultiSet<E>.any(predicate: (E) -> Boolean): Boolean = distinctValues.any(predicate)

/**
 * Returns `true` if all elements match the given [predicate].
 *
 * @param predicate (E) -> [Boolean]
 */
inline fun <E> MultiSet<E>.all(predicate: (E) -> Boolean): Boolean = distinctValues.all(predicate)

/**
 * Returns `true` if no element matches the given [predicate].
 *
 * @param predicate (E) -> [Boolean]
 */
inline fun <E> MultiSet<E>.none(predicate: (E) -> Boolean): Boolean = distinctValues.none(predicate)
