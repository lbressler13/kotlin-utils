package xyz.lbres.kotlinutils.shortarray.ext

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ShortArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = shortArrayOf()
        var expected = shortArrayOf()
        array.setAllValues(0)
        assertContentEquals(expected, array)

        // no change
        array = shortArrayOf(2, 2, 2, 2)
        expected = shortArrayOf(2, 2, 2, 2)
        array.setAllValues(2)
        assertContentEquals(expected, array)

        // some change
        array = shortArrayOf(0, -99, 1000)
        expected = shortArrayOf(-99, -99, -99)
        array.setAllValues(-99)
        assertContentEquals(expected, array)

        // all change
        array = shortArrayOf(100, -100, 200, 3)
        expected = shortArrayOf(14, 14, 14, 14)
        array.setAllValues(14)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = shortArrayOf()
        assertEquals(0, array.countElement(0))

        array = shortArrayOf(12)
        assertEquals(1, array.countElement(12))

        array = shortArrayOf(4013, 4013, 12, 3, 4013, -19, 15)
        assertEquals(3, array.countElement(4013))
        assertEquals(1, array.countElement(-19))

        array[1] = -19
        assertEquals(2, array.countElement(4013))
        assertEquals(2, array.countElement(-19))
    }

    @Test
    fun testMapInPlace() {
        // empty
        var array = shortArrayOf()
        var expected = shortArrayOf()
        array.mapInPlace { (it + 4).toShort() }
        assertContentEquals(expected, array)

        // constant value
        array = shortArrayOf(5, 6, -100)
        expected = shortArrayOf(-1, -1, -1)
        array.mapInPlace { -1 }
        assertContentEquals(expected, array)

        // transform function
        array = shortArrayOf(5, 6, -100)
        expected = shortArrayOf(9, 10, -96)
        array.mapInPlace { (it + 4).toShort() }
        assertContentEquals(expected, array)

        array = shortArrayOf(-77, 2, 8, -1, 17)
        expected = shortArrayOf(77, 3, 9, 1, 18)
        array.mapInPlace {
            simpleIf(it < 0, -it, it + 1).toShort()
        }
        assertContentEquals(expected, array)

        var count = 0
        array = shortArrayOf(-77, 2, 8, -1, 17)
        expected = shortArrayOf(-1, 0, 1, 1, -1)
        array.mapInPlace {
            val result = when {
                (it % 2).absoluteValue == count % 2 -> 1
                it % 2 == 0 -> 0
                else -> -1
            }

            count++
            result.toShort()
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty
        var array = shortArrayOf()
        var expected = shortArrayOf()
        array.mapInPlaceIndexed { index, value -> (value + index).toShort() }
        assertContentEquals(expected, array)

        // constant value
        array = shortArrayOf(5, 6, -100)
        expected = shortArrayOf(-1, -1, -1)
        array.mapInPlaceIndexed { _, _ -> -1 }
        assertContentEquals(expected, array)

        // transform function
        array = shortArrayOf(5, 6, -100)
        expected = shortArrayOf(5, 7, -98)
        array.mapInPlaceIndexed { index, value -> (value + index).toShort() }
        assertContentEquals(expected, array)
        assertContentEquals(expected, array)

        array = shortArrayOf(-77, 2, 8, -1, 17)
        expected = shortArrayOf(77, -2, 9, 0, 18)
        array.mapInPlaceIndexed { index, value ->
            simpleIf(index < 2, -value, value + 1).toShort()
        }
        assertContentEquals(expected, array)

        array = shortArrayOf(-77, 2, 8, -1, 17)
        expected = shortArrayOf(-1, 0, 1, 1, -1)
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
