package xyz.lbres.kotlinutils.booleanarray

/**
 * Create boolean array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value Boolean: value of all elements
 * @return [BooleanArray]: boolean array of given size, where every element has the given value
 */
fun booleanArrayOfValue(size: Int, value: Boolean): BooleanArray {
    return BooleanArray(size) { value }
}
