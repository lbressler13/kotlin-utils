package xyz.lbres.kotlinutils.bytearray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class ByteArrayUtilsTest {
    @Test
    fun testByteArrayOfValue() {
        var expected = byteArrayOf()
        assertContentEquals(expected, byteArrayOfValue(0, 5))

        expected = byteArrayOf(5)
        assertContentEquals(expected, byteArrayOfValue(1, 5))

        expected = byteArrayOf(-4)
        assertContentEquals(expected, byteArrayOfValue(1, -4))

        expected = byteArrayOf(0, 0)
        assertContentEquals(expected, byteArrayOfValue(2, 0))

        expected = byteArrayOf(15, 15, 15)
        assertContentEquals(expected, byteArrayOfValue(3, 15))

        expected = byteArrayOf(12, 12, 12, 12, 12)
        assertContentEquals(expected, byteArrayOfValue(5, 12))
    }
}
