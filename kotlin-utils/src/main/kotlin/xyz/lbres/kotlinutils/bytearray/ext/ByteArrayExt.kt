package xyz.lbres.kotlinutils.bytearray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Byte]: value to assign
 */
fun ByteArray.setAllValues(value: Byte) {
    indices.forEach { set(it, value) }
}
