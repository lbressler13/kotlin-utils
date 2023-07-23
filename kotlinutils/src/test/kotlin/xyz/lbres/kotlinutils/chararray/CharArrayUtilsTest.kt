package xyz.lbres.kotlinutils.chararray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class CharArrayUtilsTest {
    @Test
    fun testCharArrayOfValue() {
        var expected = charArrayOf()
        assertContentEquals(expected, charArrayOfValue(0, 'A'))

        expected = charArrayOf('A')
        assertContentEquals(expected, charArrayOfValue(1, 'A'))

        expected = charArrayOf(';', ';', ';')
        assertContentEquals(expected, charArrayOfValue(3, ';'))

        expected = charArrayOf('\\', '\\', '\\', '\\', '\\')
        assertContentEquals(expected, charArrayOfValue(5, '\\'))
    }
}
