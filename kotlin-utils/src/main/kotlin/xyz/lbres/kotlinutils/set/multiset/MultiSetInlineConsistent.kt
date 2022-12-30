package xyz.lbres.kotlinutils.set.multiset

inline fun <E, T> MultiSet<E>.mapConsistent(transform: (E) -> T): List<T> {
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
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
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
 * Accumulates value starting with [initial] value and applying [operation] from left to right
 * to current accumulator value and each element.
 *
 * Returns the specified [initial] value if the collection is empty.
 *
 * @param initial [T]: initial value for applying operation
 * @param [operation] (T, E) -> T: function that takes current accumulator value and an element, and calculates the next accumulator value.
 * @return [T]: the accumulated value, or [initial] if the MultiSet is empty
 */
inline fun <E, T> MultiSet<E>.foldConsistent(initial: T, operation: (acc: T, E) -> T): T {
    var acc = initial

    distinctValues.forEach { element ->
        val count = getCountOf(element)
        repeat(count) { acc = operation(acc, element) }
    }

    return acc
}

/**
 * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
 *
 * @param transform (E) -> T: transformation function
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
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
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
 *
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
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
 * Returns `true` if at least one element matches the given [predicate].
 * Returns `false` if MultiSet is empty.
 *
 * @param predicate (E) -> [Boolean]
 * @return [Boolean]: `true` if the MultiSet is non-empty and at least one element matches the predicate, `false` otherwise
 */
inline fun <E> MultiSet<E>.anyConsistent(predicate: (E) -> Boolean): Boolean {
    distinctValues.forEach {
        if (predicate(it)) {
            return true
        }
    }

    return false
}

/**
 * Returns `true` if all elements match the given [predicate].
 * Returns `true` if MultiSet is empty.
 *
 * @param predicate (E) -> [Boolean]
 * @return [Boolean]: `true` if the MultiSet is empty or all elements match the predicate, `false` otherwise
 */
inline fun <E> MultiSet<E>.allConsistent(predicate: (E) -> Boolean): Boolean {
    distinctValues.forEach {
        if (!predicate(it)) {
            return false
        }
    }

    return true
}

/**
 * Returns `true` if no element matches the given [predicate].
 * Returns `true` if MultiSet is empty.
 *
 * @param predicate (E) -> [Boolean]
 * @return [Boolean]: `true` if the MultiSet is empty or no elements match the predicate, `false` otherwise
 */
inline fun <E> MultiSet<E>.noneConsistent(predicate: (E) -> Boolean): Boolean {
    distinctValues.forEach {
        if (predicate(it)) {
            return false
        }
    }

    return true
}

/**
 * Returns an element yielding the smallest value of the given function or `null` if there are no elements.
 * May not always return the same element if there are multiple elements which yield the smallest value.
 *
 * @param selector (E) -> R: function to compare elements
 * @return [E]?: a value that yields the smallest value from [selector], or `null` if the MultiSet is empty
 */
inline fun <E, R : Comparable<R>> MultiSet<E>.minByOrNullConsistent(selector: (E) -> R): E? {
    if (isEmpty()) {
        return null
    }

    val result = distinctValues.associateWith(selector).minByOrNull { it.value }
    return result?.key
}

/**
 * Returns an element yielding the largest value of the given function or `null` if there are no elements.
 * May not always return the same element if there are multiple elements which yield the largest value.
 *
 * @param selector (E) -> R: function to compare elements
 * @return [E]?: a value that yields the largest value from [selector], or `null` if the MultiSet is empty
 */
inline fun <E, R : Comparable<R>> MultiSet<E>.maxByOrNullConsistent(selector: (E) -> R): E? {
    if (isEmpty()) {
        return null
    }

    val result = distinctValues.associateWith(selector).maxByOrNull { it.value }
    return result?.key
}
