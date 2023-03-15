package xyz.lbres.kotlinutils.array.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals

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
}
