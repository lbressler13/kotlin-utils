package xyz.lbres.kotlinutils.floatarray

/**
 * Create float array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value [Float]: initial value of all elements
 * @return [FloatArray]: float array of given size, where every element has the given value
 */
fun floatArrayOfValue(size: Int, value: Float): FloatArray = FloatArray(size) { value }
