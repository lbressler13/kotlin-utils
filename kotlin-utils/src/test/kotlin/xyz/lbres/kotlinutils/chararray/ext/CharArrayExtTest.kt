package xyz.lbres.kotlinutils.chararray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class CharArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = charArrayOf()
        var expected = charArrayOf()
        array.setAllValues('a')
        assertContentEquals(expected, array)

        // no change
        array = charArrayOf('a', 'a', 'a', 'a')
        expected = charArrayOf('a', 'a', 'a', 'a')
        array.setAllValues('a')
        assertContentEquals(expected, array)

        // some change
        array = charArrayOf('1', '\\', 'H')
        expected = charArrayOf('1', '1', '1')
        array.setAllValues('1')
        assertContentEquals(expected, array)

        // all change
        array = charArrayOf('h', 'e', 'l', 'l', 'o')
        expected = charArrayOf('-', '-', '-', '-', '-')
        array.setAllValues('-')
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = charArrayOf()
        assertEquals(0, array.countElement('a'))

        array = charArrayOf('~')
        assertEquals(1, array.countElement('~'))

        array = charArrayOf('7', '7', '?', 'v', '7', 'Q', '9', '*')
        assertEquals(3, array.countElement('7'))
        assertEquals(1, array.countElement('Q'))

        array[1] = 'Q'
        assertEquals(2, array.countElement('7'))
        assertEquals(2, array.countElement('Q'))
    }
}
