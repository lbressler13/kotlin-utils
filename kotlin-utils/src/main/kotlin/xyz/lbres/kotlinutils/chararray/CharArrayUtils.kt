package xyz.lbres.kotlinutils.chararray

/**
 * Create char array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value [Char]: initial value of all elements
 * @return [CharArray]: char array of given size, where every element has the given value
 */
fun charArrayOfValue(size: Int, value: Char): CharArray = CharArray(size) { value }
