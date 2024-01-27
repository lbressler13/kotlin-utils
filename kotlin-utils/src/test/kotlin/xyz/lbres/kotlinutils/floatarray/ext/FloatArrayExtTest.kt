package xyz.lbres.kotlinutils.floatarray.ext

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class FloatArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = floatArrayOf()
        var expected = floatArrayOf()
        array.setAllValues(0f)
        assertContentEquals(expected, array)

        // no change
        array = floatArrayOf(2f, 2f, 2f, 2f)
        expected = floatArrayOf(2f, 2f, 2f, 2f)
        array.setAllValues(2f)
        assertContentEquals(expected, array)

        // some change
        array = floatArrayOf(0f, 9.1f, 2.123f)
        expected = floatArrayOf(2.123f, 2.123f, 2.123f)
        array.setAllValues(2.123f)
        assertContentEquals(expected, array)

        // all change
        array = floatArrayOf(0f, 9.1f, 2.123f)
        expected = floatArrayOf(-12.2f, -12.2f, -12.2f)
        array.setAllValues(-12.2f)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = floatArrayOf()
        assertEquals(0, array.countElement(0f))

        array = floatArrayOf(12.007f)
        assertEquals(1, array.countElement(12.007f))

        array = floatArrayOf(400.123f, 400.123f, 1.2f, 0.3f, 400.123f, -19f, 15f)
        assertEquals(3, array.countElement(400.123f))
        assertEquals(1, array.countElement(-19f))

        array[1] = -19f
        assertEquals(2, array.countElement(400.123f))
        assertEquals(2, array.countElement(-19f))
    }

    @Test
    fun testMapInPlace() {
        // empty
        var array = floatArrayOf()
        var expected = floatArrayOf()
        array.mapInPlace { it + 4 }
        assertContentEquals(expected, array)

        // constant value
        array = floatArrayOf(5.0f, 6.8f, -100.001f)
        expected = floatArrayOf(-1.0f, -1.0f, -1.0f)
        array.mapInPlace { -1.0f }
        assertContentEquals(expected, array)

        // transform function
        array = floatArrayOf(5.0f, 6.8f, -100.001f)
        expected = floatArrayOf(9.0f, 10.8f, -96.001f)
        array.mapInPlace { it + 4 }
        assertContentEquals(expected, array)

        array = floatArrayOf(-77.6f, 2.2f, 8.123f, -1.0f, 17.456f)
        expected = floatArrayOf(77.6f, 3.2f, 9.123f, 1.0f, 18.456f)
        array.mapInPlace { simpleIf(it < 0.0, -it, it + 1) }
        assertContentEquals(expected, array)

        var count = 0
        array = floatArrayOf(-77.6f, 2.2f, 8.123f, -1.0f, 17.456f)
        expected = floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f, -1.0f)
        array.mapInPlace {
            val result = when {
                (it.roundToInt() % 2).absoluteValue == count % 2 -> 1.0f
                it.roundToInt() % 2 == 0 -> 0.0f
                else -> -1.0f
            }

            count++
            result
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty
        var array = floatArrayOf()
        var expected = floatArrayOf()
        array.mapInPlaceIndexed { index, value -> value + index }
        assertContentEquals(expected, array)

        // constant value
        array = floatArrayOf(5.0f, 6.8f, -100.001f)
        expected = floatArrayOf(-1.0f, -1.0f, -1.0f)
        array.mapInPlaceIndexed { _, _ -> -1.0f }
        assertContentEquals(expected, array)

        // transform function
        array = floatArrayOf(5.0f, 6.8f, -100.001f)
        expected = floatArrayOf(5.0f, 7.8f, -98.001f)
        array.mapInPlaceIndexed { index, value -> value + index }
        assertContentEquals(expected, array)

        array = floatArrayOf(-77.6f, 2.2f, 8.123f, -1.0f, 17.456f)
        expected = floatArrayOf(77.6f, -2.2f, 9.123f, 0.0f, 18.456f)
        array.mapInPlaceIndexed { index, value ->
            simpleIf(index < 2, -value, value + 1)
        }
        assertContentEquals(expected, array)

        array = floatArrayOf(-77.6f, 2.2f, 8.123f, -1.0f, 17.456f)
        expected = floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f, -1.0f)
        array.mapInPlaceIndexed { index, value ->
            when {
                (value.roundToInt() % 2).absoluteValue == index % 2 -> 1.0f
                value.roundToInt() % 2 == 0 -> 0.0f
                else -> -1.0f
            }
        }
        assertContentEquals(expected, array)
    }
}
