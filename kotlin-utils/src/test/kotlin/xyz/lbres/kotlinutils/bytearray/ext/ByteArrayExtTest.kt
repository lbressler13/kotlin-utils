package xyz.lbres.kotlinutils.bytearray.ext

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = byteArrayOf()
        var expected = byteArrayOf()
        array.setAllValues(8)
        assertContentEquals(expected, array)

        // no change
        array = byteArrayOf(2, 2, 2, 2)
        expected = byteArrayOf(2, 2, 2, 2)
        array.setAllValues(2)
        assertContentEquals(expected, array)

        // some change
        array = byteArrayOf(2, 0, -2, 9)
        expected = byteArrayOf(0, 0, 0, 0)
        array.setAllValues(0)
        assertContentEquals(expected, array)

        // all change
        array = byteArrayOf(5, 6, 7, 8)
        expected = byteArrayOf(-10, -10, -10, -10)
        array.setAllValues(-10)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = byteArrayOf()
        assertEquals(0, array.countElement(0))

        array = byteArrayOf(127)
        assertEquals(1, array.countElement(127))

        array = byteArrayOf(5, 5, 1, 3, 5, -19, 52)
        assertEquals(3, array.countElement(5))
        assertEquals(1, array.countElement(-19))

        array[1] = -19
        assertEquals(2, array.countElement(5))
        assertEquals(2, array.countElement(-19))
    }

    @Test
    fun testMapInPlace() {
        // empty
        var array = byteArrayOf()
        var expected = byteArrayOf()
        array.mapInPlace { (it + 4).toByte() }
        assertContentEquals(expected, array)

        // constant value
        array = byteArrayOf(5, 6, -100)
        expected = byteArrayOf(-1, -1, -1)
        array.mapInPlace { -1 }
        assertContentEquals(expected, array)

        // transform function
        array = byteArrayOf(5, 6, -100)
        expected = byteArrayOf(9, 10, -96)
        array.mapInPlace { (it + 4).toByte() }
        assertContentEquals(expected, array)

        array = byteArrayOf(-77, 2, 8, -1, 17)
        expected = byteArrayOf(77, 3, 9, 1, 18)
        array.mapInPlace {
            simpleIf(it < 0, -it, it + 1).toByte()
        }
        assertContentEquals(expected, array)

        var count = 0
        array = byteArrayOf(-77, 2, 8, -1, 17)
        expected = byteArrayOf(-1, 0, 1, 1, -1)
        array.mapInPlace {
            val result = when {
                (it % 2).absoluteValue == count % 2 -> 1
                it % 2 == 0 -> 0
                else -> -1
            }

            count++
            result.toByte()
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty
        var array = byteArrayOf()
        var expected = byteArrayOf()
        array.mapInPlaceIndexed { index, value -> (value + index).toByte() }
        assertContentEquals(expected, array)

        // constant value
        array = byteArrayOf(5, 6, -100)
        expected = byteArrayOf(-1, -1, -1)
        array.mapInPlaceIndexed { _, _ -> -1 }
        assertContentEquals(expected, array)

        // transform function
        array = byteArrayOf(5, 6, -100)
        expected = byteArrayOf(5, 7, -98)
        array.mapInPlaceIndexed { index, value -> (value + index).toByte() }
        assertContentEquals(expected, array)

        array = byteArrayOf(-77, 2, 8, -1, 17)
        expected = byteArrayOf(77, -2, 9, 0, 18)
        array.mapInPlaceIndexed { index, value ->
            simpleIf(index < 2, -value, value + 1).toByte()
        }
        assertContentEquals(expected, array)

        array = byteArrayOf(-77, 2, 8, -1, 17)
        expected = byteArrayOf(-1, 0, 1, 1, -1)
        array.mapInPlaceIndexed { index, value ->
            when {
                (value % 2).absoluteValue == index % 2 -> 1
                value % 2 == 0 -> 0
                else -> -1
            }
        }
        assertContentEquals(expected, array)
    }
}
