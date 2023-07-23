package xyz.lbres.kotlinutils.array

/**
 * Create array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value [T]: initial value of all elements
 * @return [Array]: array of given size, where every element has the given value
 */
inline fun <reified T> arrayOfValue(size: Int, value: T): Array<T> = Array(size) { value }

/**
 * Create array where every element is initialized as `null`
 *
 * @param size [Int]: size of array to create
 * @return [Array]: array of given size, where every element is `null`
 */
inline fun <reified T> arrayOfNulls(size: Int): Array<T?> = arrayOfValue(size, null)
