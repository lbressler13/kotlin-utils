package xyz.lbres.kotlinutils.iterable.ext

/**
 * forEach method which iterates through all combinations of elements in this iterable and another iterable,
 * and applies an action to each pair of values
 *
 * @param other [Iterable]<S>: other value to iterate through
 * @param action (S, T) -> Unit: action to take on each pair of values
 */
fun <S, T> Iterable<S>.forEachWith(other: Iterable<T>, action: (S, T) -> Unit) {
    forEach { value ->
        other.forEach { action(value, it) }
    }
}

/**
 * forEachIndexed method which iterates through all combinations of elements in this iterable and another iterable, with indices,
 * and applies an action to each pair of indexed values
 *
 * @param other [Iterable]<S>: other value to iterate through
 * @param action (IndexedValue<S>, IndexedValue<R>) -> Unit: action to take on each pair of indexed values
 */
fun <S, T> Iterable<S>.forEachWithIndexed(other: Iterable<T>, action: (IndexedValue<S>, IndexedValue<T>) -> Unit) {
    forEachIndexed { index, value ->
        val indexed: IndexedValue<S> = IndexedValue(index, value)

        other.forEachIndexed { otherIndex, otherValue ->
            val otherIndexed: IndexedValue<T> = IndexedValue(otherIndex, otherValue)
            action(indexed, otherIndexed)
        }
    }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element T: value to match
 * @return [Int]: number of elements with the given value
 */
fun <T> Iterable<T>.countElement(element: T) = this.count { it == element }
