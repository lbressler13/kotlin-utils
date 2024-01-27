package xyz.lbres.kotlinutils.doublearray.ext

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class DoubleArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = doubleArrayOf()
        var expected = doubleArrayOf()
        array.setAllValues(0.0)
        assertContentEquals(expected, array)

        // no change
        array = doubleArrayOf(2.0, 2.0, 2.0, 2.0)
        expected = doubleArrayOf(2.0, 2.0, 2.0, 2.0)
        array.setAllValues(2.0)
        assertContentEquals(expected, array)

        // some change
        array = doubleArrayOf(0.0, 9.1, 2.123)
        expected = doubleArrayOf(2.123, 2.123, 2.123)
        array.setAllValues(2.123)
        assertContentEquals(expected, array)

        // all change
        array = doubleArrayOf(0.0, 9.1, 2.123)
        expected = doubleArrayOf(-12.2, -12.2, -12.2)
        array.setAllValues(-12.2)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = doubleArrayOf()
        assertEquals(0, array.countElement(0.0))

        array = doubleArrayOf(12.007)
        assertEquals(1, array.countElement(12.007))

        array = doubleArrayOf(400.123, 400.123, 1.2, 0.3, 400.123, -19.0, 15.0)
        assertEquals(3, array.countElement(400.123))
        assertEquals(1, array.countElement(-19.0))

        array[1] = -19.0
        assertEquals(2, array.countElement(400.123))
        assertEquals(2, array.countElement(-19.0))
    }

    @Test
    fun testMapInPlace() {
        // empty
        var array = doubleArrayOf()
        var expected = doubleArrayOf()
        array.mapInPlace { it + 4 }
        assertContentEquals(expected, array)

        // constant value
        array = doubleArrayOf(5.0, 6.8, -100.001)
        expected = doubleArrayOf(-1.0, -1.0, -1.0)
        array.mapInPlace { -1.0 }
        assertContentEquals(expected, array)

        // transform function
        array = doubleArrayOf(5.0, 6.8, -100.001)
        expected = doubleArrayOf(9.0, 10.8, -96.001)
        array.mapInPlace { it + 4 }
        assertContentEquals(expected, array)

        array = doubleArrayOf(-77.6, 2.2, 8.123, -1.0, 17.456)
        expected = doubleArrayOf(77.6, 3.2, 9.123, 1.0, 18.456)
        array.mapInPlace { simpleIf(it < 0.0, -it, it + 1) }
        assertContentEquals(expected, array)

        var count = 0
        array = doubleArrayOf(-77.6, 2.2, 8.123, -1.0, 17.456)
        expected = doubleArrayOf(1.0, 0.0, 1.0, 1.0, -1.0)
        array.mapInPlace {
            val result = when {
                (it.roundToInt() % 2).absoluteValue == count % 2 -> 1.0
                it.roundToInt() % 2 == 0 -> 0.0
                else -> -1.0
            }

            count++
            result
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty
        var array = doubleArrayOf()
        var expected = doubleArrayOf()
        array.mapInPlaceIndexed { index, value -> value + index }
        assertContentEquals(expected, array)

        // constant value
        array = doubleArrayOf(5.0, 6.8, -100.001)
        expected = doubleArrayOf(-1.0, -1.0, -1.0)
        array.mapInPlaceIndexed { _, _ -> -1.0 }
        assertContentEquals(expected, array)

        // transform function
        array = doubleArrayOf(5.0, 6.8, -100.001)
        expected = doubleArrayOf(5.0, 7.8, -98.001)
        array.mapInPlaceIndexed { index, value -> value + index }
        assertContentEquals(expected, array)

        array = doubleArrayOf(-77.6, 2.2, 8.123, -1.0, 17.456)
        expected = doubleArrayOf(77.6, -2.2, 9.123, 0.0, 18.456)
        array.mapInPlaceIndexed { index, value ->
            simpleIf(index < 2, -value, value + 1)
        }
        assertContentEquals(expected, array)

        array = doubleArrayOf(-77.6, 2.2, 8.123, -1.0, 17.456)
        expected = doubleArrayOf(1.0, 0.0, 1.0, 1.0, -1.0)
        array.mapInPlaceIndexed { index, value ->
            when {
                (value.roundToInt() % 2).absoluteValue == index % 2 -> 1.0
                value.roundToInt() % 2 == 0 -> 0.0
                else -> -1.0
            }
        }
        assertContentEquals(expected, array)
    }
}
