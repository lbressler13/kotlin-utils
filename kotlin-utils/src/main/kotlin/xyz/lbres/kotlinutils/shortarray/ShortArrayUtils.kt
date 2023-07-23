package xyz.lbres.kotlinutils.shortarray

/**
 * Create short array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value [Short]: initial value of all elements
 * @return [ShortArray]: short array of given size, where every element has the given value
 */
fun shortArrayOfValue(size: Int, value: Short): ShortArray = ShortArray(size) { value }
