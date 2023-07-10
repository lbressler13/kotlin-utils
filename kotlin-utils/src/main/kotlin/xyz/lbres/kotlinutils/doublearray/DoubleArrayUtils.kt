package xyz.lbres.kotlinutils.doublearray

/**
 * Create double array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value [Double]: initial value of all elements
 * @return [DoubleArray]: double array of given size, where every element has the given value
 */
fun doubleArrayOfValue(size: Int, value: Double): DoubleArray = DoubleArray(size) { value }
