package xyz.lbres.kotlinutils.array

/**
 * Create array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value E: value of all elements
 * @return [Array]: array of given size, where every element has the given value
 */
inline fun <reified E> arrayOfValue(size: Int, value: E): Array<E> {
    return Array(size) { value }
}

/**
 * Create array where every element is initialized as `null`
 *
 * @param size [Int]: size of array to create
 * @return [Array]: array of given size, where every element is `null`
 */
inline fun <reified E> arrayOfNulls(size: Int): Array<E?> = arrayOfValue(size, null)
