package xyz.lbres.kotlinutils.booleanarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun testAll() {
        // true
        var array = booleanArrayOf()
        assertTrue(array.all())

        array = booleanArrayOf(true)
        assertTrue(array.all())

        array = booleanArrayOf(true, true, true, true)
        assertTrue(array.all())

        // false
        array = booleanArrayOf(false)
        assertFalse(array.all())

        array = booleanArrayOf(false, false, false, false)
        assertFalse(array.all())

        // mixed
        array = booleanArrayOf(true, false)
        assertFalse(array.all())

        array = booleanArrayOf(false, true)
        assertFalse(array.all())

        array = booleanArrayOf(true, true, true, false, true)
        assertFalse(array.all())

        array = booleanArrayOf(true, false, true, false, true, false, false)
        assertFalse(array.all())

        // changing
        array = booleanArrayOf(true)
        assertTrue(array.all())

        array[0] = false
        assertFalse(array.all())

        array = booleanArrayOf(false, true, false)
        assertFalse(array.all())

        array[1] = false
        assertFalse(array.all())

        array.setAllValues(true)
        assertTrue(array.all())
    }

    @Test
    fun testNone() {
        // true
        var array = booleanArrayOf()
        assertTrue(array.none())

        array = booleanArrayOf(false)
        assertTrue(array.none())

        array = booleanArrayOf(false, false, false, false)
        assertTrue(array.none())

        // false
        array = booleanArrayOf(true)
        assertFalse(array.none())

        array = booleanArrayOf(true, true, true, true)
        assertFalse(array.none())

        // mixed
        array = booleanArrayOf(true, false)
        assertFalse(array.none())

        array = booleanArrayOf(false, true)
        assertFalse(array.none())

        array = booleanArrayOf(true, true, true, false, true)
        assertFalse(array.none())

        array = booleanArrayOf(true, false, true, false, true, false, false)
        assertFalse(array.none())

        // changing
        array = booleanArrayOf(true)
        assertFalse(array.none())

        array[0] = false
        assertTrue(array.none())

        array = booleanArrayOf(false, true, false)
        assertFalse(array.none())

        array[1] = false
        assertTrue(array.none())

        array.setAllValues(true)
        assertFalse(array.none())
    }

    @Test
    fun testAny() {
        // empty
        var array = booleanArrayOf()
        assertFalse(array.any())

        // true
        array = booleanArrayOf(true)
        assertTrue(array.any())

        array = booleanArrayOf(true, true, true, true)
        assertTrue(array.any())

        // false
        array = booleanArrayOf(false)
        assertFalse(array.any())

        array = booleanArrayOf(false, false, false, false)
        assertFalse(array.any())

        // mixed
        array = booleanArrayOf(true, false)
        assertTrue(array.any())

        array = booleanArrayOf(false, true)
        assertTrue(array.any())

        array = booleanArrayOf(true, true, true, false, true)
        assertTrue(array.any())

        array = booleanArrayOf(true, false, true, false, true, false, false)
        assertTrue(array.any())

        // changing
        array = booleanArrayOf(true)
        assertTrue(array.any())

        array[0] = false
        assertFalse(array.any())

        array = booleanArrayOf(false, true, false)
        assertTrue(array.any())

        array[1] = false
        assertFalse(array.any())

        array.setAllValues(true)
        assertTrue(array.any())
    }

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
}
