package xyz.lbres.kotlinutils.intarray.ext

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class IntArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = intArrayOf()
        var expected = intArrayOf()
        array.setAllValues(0)
        assertContentEquals(expected, array)

        // no change
        array = intArrayOf(2, 2, 2, 2)
        expected = intArrayOf(2, 2, 2, 2)
        array.setAllValues(2)
        assertContentEquals(expected, array)

        // some change
        array = intArrayOf(0, -99, 1000)
        expected = intArrayOf(-99, -99, -99)
        array.setAllValues(-99)
        assertContentEquals(expected, array)

        // all change
        array = intArrayOf(100, -100, 200, 3)
        expected = intArrayOf(14, 14, 14, 14)
        array.setAllValues(14)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = intArrayOf()
        assertEquals(0, array.countElement(0))

        array = intArrayOf(12)
        assertEquals(1, array.countElement(12))

        array = intArrayOf(400123, 400123, 12, 3, 400123, -19, 15)
        assertEquals(3, array.countElement(400123))
        assertEquals(1, array.countElement(-19))

        array[1] = -19
        assertEquals(2, array.countElement(400123))
        assertEquals(2, array.countElement(-19))
    }

    @Test
    fun testMapInPlace() {
        // empty
        var array = intArrayOf()
        var expected = intArrayOf()
        array.mapInPlace { it + 4 }
        assertContentEquals(expected, array)

        // constant value
        array = intArrayOf(5, 6, -100)
        expected = intArrayOf(-1, -1, -1)
        array.mapInPlace { -1 }
        assertContentEquals(expected, array)

        // transform function
        array = intArrayOf(5, 6, -100)
        expected = intArrayOf(9, 10, -96)
        array.mapInPlace { it + 4 }
        assertContentEquals(expected, array)

        array = intArrayOf(-77, 2, 8, -1, 17)
        expected = intArrayOf(77, 3, 9, 1, 18)
        array.mapInPlace { simpleIf(it < 0, -it, it + 1) }
        assertContentEquals(expected, array)

        var count = 0
        array = intArrayOf(-77, 2, 8, -1, 17)
        expected = intArrayOf(-1, 0, 1, 1, -1)
        array.mapInPlace {
            val result = when {
                (it % 2).absoluteValue == count % 2 -> 1
                it % 2 == 0 -> 0
                else -> -1
            }

            count++
            result
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty
        var array = intArrayOf()
        var expected = intArrayOf()
        array.mapInPlaceIndexed { index, value -> value + index }
        assertContentEquals(expected, array)

        // constant value
        array = intArrayOf(5, 6, -100)
        expected = intArrayOf(-1, -1, -1)
        array.mapInPlaceIndexed { _, _ -> -1 }
        assertContentEquals(expected, array)

        // transform function
        array = intArrayOf(5, 6, -100)
        expected = intArrayOf(5, 7, -98)
        array.mapInPlaceIndexed { index, value -> value + index }
        assertContentEquals(expected, array)

        array = intArrayOf(-77, 2, 8, -1, 17)
        expected = intArrayOf(77, -2, 9, 0, 18)
        array.mapInPlaceIndexed { index, value ->
            simpleIf(index < 2, -value, value + 1)
        }
        assertContentEquals(expected, array)

        array = intArrayOf(-77, 2, 8, -1, 17)
        expected = intArrayOf(-1, 0, 1, 1, -1)
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
