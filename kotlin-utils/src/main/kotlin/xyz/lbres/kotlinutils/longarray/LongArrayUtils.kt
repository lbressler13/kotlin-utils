package xyz.lbres.kotlinutils.longarray

/**
 * Create long array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value Long: value of all elements
 * @return [LongArray]: long array of given size, where every element has the given value
 */
fun longArrayOfValue(size: Int, value: Long): LongArray {
    return LongArray(size) { value }
}
