package xyz.lbres.kotlinutils.bytearray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Byte]: value to assign
 */
fun ByteArray.setAllValues(value: Byte) {
    indices.forEach { set(it, value) }
}
