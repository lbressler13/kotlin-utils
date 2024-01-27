package xyz.lbres.kotlinutils.chararray.ext

import xyz.lbres.kotlinutils.general.simpleIf
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

    @Test
    fun testMapInPlace() {
        // empty
        var array = charArrayOf()
        var expected = charArrayOf()
        array.mapInPlace { (it.code + 4).toChar() }
        assertContentEquals(expected, array)

        // constant value
        array = charArrayOf('a', 'b', '3')
        expected = charArrayOf('7', '7', '7')
        array.mapInPlace { '7' }
        assertContentEquals(expected, array)

        // transform function
        array = charArrayOf('a', 'b', '2', 'R')
        expected = charArrayOf('e', 'f', '6', 'V')
        array.mapInPlace { (it.code + 4).toChar() }
        assertContentEquals(expected, array)

        array = charArrayOf('1', '7', '%', 'K', '?')
        expected = charArrayOf('1', '-', '%', '-', '-')
        array.mapInPlace { simpleIf(it < '3', it, '-') }
        assertContentEquals(expected, array)

        var count = 0
        array = charArrayOf('1', '4', '%', 'K', '$') // 49, 52, 37, 75, 36
        expected = charArrayOf('0', '+', '0', '*', '*')
        array.mapInPlace {
            val result = when {
                it.code % 2 == count % 2 -> '*'
                it.code % 2 == 0 -> '+'
                else -> '0'
            }

            count++
            result
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty
        var array = charArrayOf()
        var expected = charArrayOf()
        array.mapInPlaceIndexed { index, value -> (value.code + index).toChar() }
        assertContentEquals(expected, array)

        // constant value
        array = charArrayOf('a', 'b', '3')
        expected = charArrayOf('7', '7', '7')
        array.mapInPlaceIndexed { _, _ -> '7' }
        assertContentEquals(expected, array)

        // transform function
        array = charArrayOf('a', 'b', '2', 'R')
        expected = charArrayOf('a', 'c', '4', 'U')
        array.mapInPlaceIndexed { index, value -> (value.code + index).toChar() }
        assertContentEquals(expected, array)

        array = charArrayOf('1', '7', '%', 'K', '?')
        expected = charArrayOf('1', '7', '%', '-', '-')
        array.mapInPlaceIndexed { index, value -> simpleIf(index < 3, value, '-') }
        assertContentEquals(expected, array)

        array = charArrayOf('1', '4', '%', 'K', '$') // 49, 52, 37, 75, 36
        expected = charArrayOf('0', '+', '0', '*', '*')
        array.mapInPlaceIndexed { index, value ->
            when {
                value.code % 2 == index % 2 -> '*'
                value.code % 2 == 0 -> '+'
                else -> '0'
            }
        }
        assertContentEquals(expected, array)
    }
}
