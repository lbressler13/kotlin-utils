package xyz.lbres.kotlinutils.intarray

/**
 * Create int array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value Int: value of all elements
 * @return [IntArray]: int array of given size, where every element has the given value
 */
fun intArrayOfValue(size: Int, value: Int): IntArray {
    return IntArray(size) { value }
}
