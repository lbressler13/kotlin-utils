package xyz.lbres.kotlinutils.collection.list

/**
 * Assign all indices to have the same value
 *
 * @param value T: value to assign
 */
fun <T> MutableList<T>.setAllValues(value: T) {
    indices.forEach { set(it, value) }
}

/**
 * Replace all values in the list using a provided transform function, without generating a new list
 *
 * @param transform (T) -> T: function to generate new values
 */
fun <T> MutableList<T>.mapInPlace(transform: (T) -> T) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the list using a provided transform function that uses both the value and the index,
 * without generating a new list
 *
 * @param transform (Int, T) -> T: function to generate new values
 */
fun <T> MutableList<T>.mapInPlaceIndexed(transform: (Int, T) -> T) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
