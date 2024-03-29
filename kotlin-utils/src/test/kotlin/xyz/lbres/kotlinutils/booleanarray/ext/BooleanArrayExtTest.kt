package xyz.lbres.kotlinutils.booleanarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class BooleanArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = booleanArrayOf()
        var expected = booleanArrayOf()
        array.setAllValues(true)
        assertContentEquals(expected, array)

        // no change
        array = booleanArrayOf(true, true, true)
        expected = booleanArrayOf(true, true, true)
        array.setAllValues(true)
        assertContentEquals(expected, array)

        array = booleanArrayOf(false, false, false)
        expected = booleanArrayOf(false, false, false)
        array.setAllValues(false)
        assertContentEquals(expected, array)

        // some change
        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(true, true, true)
        array.setAllValues(true)
        assertContentEquals(expected, array)

        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(false, false, false)
        array.setAllValues(false)
        assertContentEquals(expected, array)

        // all change
        array = booleanArrayOf(true, true, true)
        expected = booleanArrayOf(false, false, false)
        array.setAllValues(false)
        assertContentEquals(expected, array)

        array = booleanArrayOf(false, false, false)
        expected = booleanArrayOf(true, true, true)
        array.setAllValues(true)
        assertContentEquals(expected, array)
    }

    @Test fun testAll() = runAllTests()
    @Test fun testNone() = runNoneTests()
    @Test fun testAny() = runAnyTests()

    @Test
    fun testCountElement() {
        var array = booleanArrayOf()
        assertEquals(0, array.countElement(true))
        assertEquals(0, array.countElement(false))

        array = booleanArrayOf(true)
        assertEquals(1, array.countElement(true))
        assertEquals(0, array.countElement(false))

        array = booleanArrayOf(false, false, false, true, true)
        assertEquals(2, array.countElement(true))
        assertEquals(3, array.countElement(false))

        array[4] = false
        assertEquals(1, array.countElement(true))
        assertEquals(4, array.countElement(false))
    }

    @Test
    fun testMapInPlace() {
        // empty array
        var array = booleanArrayOf()
        var expected = booleanArrayOf()
        array.mapInPlace { !it }
        assertContentEquals(expected, array)

        // constant value
        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(true, true, true)
        array.mapInPlace { true }
        assertContentEquals(expected, array)

        // transform function
        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(false, true, true)
        array.mapInPlace { !it }
        assertContentEquals(expected, array)

        var count = 0
        array = booleanArrayOf(true, true, false, false, true)
        expected = booleanArrayOf(true, false, false, true, true)
        array.mapInPlace {
            val result = (count % 2 == 0) == it
            count++
            result
        }
        assertContentEquals(expected, array)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty array
        var array = booleanArrayOf()
        var expected = booleanArrayOf()
        array.mapInPlaceIndexed { _, value -> !value }
        assertContentEquals(expected, array)

        // constant value
        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(true, true, true)
        array.mapInPlaceIndexed { _, _ -> true }
        assertContentEquals(expected, array)

        // transform function
        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(false, true, true)
        array.mapInPlaceIndexed { _, value -> !value }
        assertContentEquals(expected, array)

        array = booleanArrayOf(true, false, false, true, true)
        expected = booleanArrayOf(false, true, false, false, true)
        array.mapInPlaceIndexed { index, _ -> index % 3 == 1 }
        assertContentEquals(expected, array)

        array = booleanArrayOf(true, true, false, false, true)
        expected = booleanArrayOf(true, false, false, true, true)
        array.mapInPlaceIndexed { index, value ->
            (index % 2 == 0) == value
        }
        assertContentEquals(expected, array)
    }
}
