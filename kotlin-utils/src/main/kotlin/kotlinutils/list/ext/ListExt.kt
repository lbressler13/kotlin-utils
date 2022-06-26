package kotlinutils.list.ext

/**
 * Create a copy of a list, with one value changed
 *
 * @param index [Int]: index of value to change
 * @param value [T]: value to substitute at index
 * @return list identical to this, with the exception of the value at index i
 * @throws IndexOutOfBoundsException if index is less than zero or greater than lastIndex
 */
fun <T> List<T>.copyWithReplacement(index: Int, value: T): List<T> {
    val before: List<T> = subList(0, index)
    val after: List<T> = if (index == lastIndex) {
        listOf()
    } else {
        subList(index + 1, size)
    }

    return before + value + after
}

/**
 * Create a copy of a list, with the last value changed
 *
 * @param value [T]: new value for last index
 * @return list identical to this, with the exception of the value at the last index
 */
fun <T> List<T>.copyWithLastReplaced(value: T): List<T> = copyWithReplacement(lastIndex, value)

/**
 * Create a copy of a list, with the first value changed
 *
 * @param value [T]: new value for first index
 * @return list identical to this, with the exception of the value at the first index
 */
fun <T> List<T>.copyWithFirstReplaced(value: T): List<T> = copyWithReplacement(0, value)

/**
 * Create a copy of a list, without the last value
 *
 * @return list identical to this, with the last value removed
 */
fun <T> List<T>.copyWithoutLast(): List<T> = subList(0, lastIndex)
