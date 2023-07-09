package xyz.lbres.kotlinutils.array.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var intArray: Array<Int> = emptyArray()
        var intExpected: Array<Int> = emptyArray()
        intArray.setAllValues(1)
        assertContentEquals(intExpected, intArray)

        // no change
        intArray = arrayOf(1, 1, 1, 1)
        intExpected = arrayOf(1, 1, 1, 1)
        intArray.setAllValues(1)
        assertContentEquals(intExpected, intArray)

        // some change
        intArray = arrayOf(1, -1, -1, 2, 4)
        intExpected = arrayOf(-1, -1, -1, -1, -1)
        intArray.setAllValues(-1)
        assertContentEquals(intExpected, intArray)

        var listArray = arrayOf(listOf("hello", "world"), listOf("goodbye", "planet", "moon"), listOf("random string"))
        var listExpected = arrayOf(listOf("hello", "world"), listOf("hello", "world"), listOf("hello", "world"))
        listArray.setAllValues(listOf("hello", "world"))
        assertContentEquals(listExpected, listArray)

        var nullArray: Array<Int?> = arrayOf(1, 2, null, 3)
        var nullExpected: Array<Int?> = arrayOf(1, 1, 1, 1)
        nullArray.setAllValues(1)
        assertContentEquals(nullExpected, nullArray)

        nullArray = arrayOf(1, 2, null, 3)
        nullExpected = arrayOf(null, null, null, null)
        nullArray.setAllValues(null)
        assertContentEquals(nullExpected, nullArray)

        // all change
        intArray = arrayOf(100, 200, 500, 355)
        intExpected = arrayOf(99, 99, 99, 99)
        intArray.setAllValues(99)
        assertContentEquals(intExpected, intArray)

        listArray = arrayOf(listOf("hello", "world"), listOf("goodbye", "planet", "moon"), listOf("random string"))
        listExpected = arrayOf(emptyList(), emptyList(), emptyList())
        listArray.setAllValues(emptyList())
        assertContentEquals(listExpected, listArray)

        nullArray = arrayOf(1, 2, 3, 4)
        nullExpected = arrayOf(null, null, null, null)
        nullArray.setAllValues(null)
        assertContentEquals(nullExpected, nullArray)

        nullArray = arrayOf(null, null, null, null)
        nullExpected = arrayOf(53, 53, 53, 53)
        nullArray.setAllValues(53)
        assertContentEquals(nullExpected, nullArray)
    }

    @Test
    fun testCountElement() {
        var intArray1: Array<Int> = arrayOf()
        assertEquals(0, intArray1.countElement(0))
        assertEquals(0, intArray1.countElement(175))

        intArray1 = arrayOf(7)
        assertEquals(1, intArray1.countElement(7))
        assertEquals(0, intArray1.countElement(8))

        intArray1 = arrayOf(7, 7, 2, 2, 7, 8, 5, 7)
        assertEquals(4, intArray1.countElement(7))
        assertEquals(1, intArray1.countElement(8))

        intArray1[1] = 8
        assertEquals(3, intArray1.countElement(7))
        assertEquals(2, intArray1.countElement(8))

        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(1, 2, 3)
        val mutablesArray = arrayOf(mutableList1, mutableList2)
        assertEquals(2, mutablesArray.countElement(mutableListOf(1, 2, 3)))
        assertEquals(0, mutablesArray.countElement(mutableListOf()))

        mutableList1.clear()
        assertEquals(1, mutablesArray.countElement(mutableListOf(1, 2, 3)))
        assertEquals(1, mutablesArray.countElement(mutableListOf()))

        var nullableArray = arrayOf(1, 2, null, 3, null)
        assertEquals(2, nullableArray.countElement(null))

        nullableArray = arrayOf(null, null, null, null)
        assertEquals(4, nullableArray.countElement(null))
    }
}
