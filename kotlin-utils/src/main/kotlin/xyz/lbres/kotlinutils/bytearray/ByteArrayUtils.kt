package xyz.lbres.kotlinutils.bytearray

/**
 * Create byte array where every element is initialized with the same value
 *
 * @param size [Int]: size of array to create
 * @param value [Byte]: initial value of all elements
 * @return [ByteArray]: byte array of given size, where every element has the given value
 */
fun byteArrayOfValue(size: Int, value: Byte): ByteArray = ByteArray(size) { value }
