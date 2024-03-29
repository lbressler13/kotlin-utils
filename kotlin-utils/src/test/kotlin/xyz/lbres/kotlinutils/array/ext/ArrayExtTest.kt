package xyz.lbres.kotlinutils.array.ext

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.list.IntList
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
        var intArray: Array<Int> = emptyArray()
        assertEquals(0, intArray.countElement(0))
        assertEquals(0, intArray.countElement(175))

        intArray = arrayOf(7)
        assertEquals(1, intArray.countElement(7))
        assertEquals(0, intArray.countElement(8))

        intArray = arrayOf(7, 7, 2, 2, 7, 8, 5, 7)
        assertEquals(4, intArray.countElement(7))
        assertEquals(1, intArray.countElement(8))

        intArray[1] = 8
        assertEquals(3, intArray.countElement(7))
        assertEquals(2, intArray.countElement(8))

        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(1, 2, 3)
        val mutablesArray: Array<IntList> = arrayOf(mutableList1, mutableList2)
        assertEquals(2, mutablesArray.countElement(listOf(1, 2, 3)))
        assertEquals(0, mutablesArray.countElement(emptyList()))

        mutableList1.clear()
        assertEquals(1, mutablesArray.countElement(listOf(1, 2, 3)))
        assertEquals(1, mutablesArray.countElement(emptyList()))

        var nullableArray = arrayOf(1, 2, null, 3, null)
        assertEquals(2, nullableArray.countElement(null))

        nullableArray = arrayOf(null, null, null, null)
        assertEquals(4, nullableArray.countElement(null))
    }

    @Test
    fun testMapInPlace() {
        // empty array
        var intArray: Array<Int> = emptyArray()
        var intExpected: Array<Int> = emptyArray()
        intArray.mapInPlace { it * 3 - 4 }
        assertContentEquals(intExpected, intArray)

        // constant value
        intArray = arrayOf(3, 4, 6)
        intExpected = arrayOf(0, 0, 0)
        intArray.mapInPlace { 0 }
        assertContentEquals(intExpected, intArray)

        var listArray = arrayOf(listOf(4, 5, 7), listOf(9, 8, 'c'))
        var listExpected = arrayOf(listOf(1.5, 'a'), listOf(1.5, 'a'))
        listArray.mapInPlace { listOf(1.5, 'a') }
        assertContentEquals(listExpected, listArray)

        // transform function
        intArray = arrayOf(-1, -1, -1)
        intExpected = arrayOf(-7, -7, -7)
        intArray.mapInPlace { it * 3 - 4 }
        assertContentEquals(intExpected, intArray)

        intArray = arrayOf(3, 4, 2, 0)
        intExpected = arrayOf(5, 8, 2, -4)
        intArray.mapInPlace { it * 3 - 4 }
        assertContentEquals(intExpected, intArray)

        listArray = arrayOf(listOf(1, 2, 3), listOf(4, "hello"), emptyList(), listOf(5.7))
        listExpected = arrayOf(listOf(3), listOf("hello"), listOf(0, "none"), listOf(5.7))
        listArray.mapInPlace {
            simpleIf(it.isEmpty(), { listOf(0, "none") }, { listOf(it.last()) })
        }
        assertContentEquals(listExpected, listArray)

        var count = 0
        val nullArray: Array<String?> = arrayOf("hello", "world", null, null, "goodbye", "planet", "7", null)
        val nullExpected = arrayOf("50", null, "100", "25", "50", null, "50", "25")
        nullArray.mapInPlace {
            val result = when {
                count % 2 == 0 && it == null -> "100"
                count % 2 == 0 -> "50"
                it == null -> "25"
                else -> null
            }

            count++
            result
        }
        assertContentEquals(nullExpected, nullArray)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty array
        var intArray: Array<Int> = emptyArray()
        var intExpected: Array<Int> = emptyArray()
        intArray.mapInPlaceIndexed { index, value -> value * 3 - index }
        assertContentEquals(intExpected, intArray)

        // constant value
        intArray = arrayOf(3, 4, 6)
        intExpected = arrayOf(0, 0, 0)
        intArray.mapInPlaceIndexed { _, _ -> 0 }
        assertContentEquals(intExpected, intArray)

        var listArray = arrayOf(listOf(4, 5, 7), listOf(9, 8, 'c'))
        var listExpected = arrayOf(listOf(1.5, 'a'), listOf(1.5, 'a'))
        listArray.mapInPlaceIndexed { _, _ -> listOf(1.5, 'a') }
        assertContentEquals(listExpected, listArray)

        // transform function
        intArray = arrayOf(-1, -1, -1)
        intExpected = arrayOf(-3, -4, -5)
        intArray.mapInPlaceIndexed { index, value -> value * 3 - index }
        assertContentEquals(intExpected, intArray)

        intArray = arrayOf(3, 4, 2, 0)
        intExpected = arrayOf(9, 11, 4, -3)
        intArray.mapInPlaceIndexed { index, value -> value * 3 - index }
        assertContentEquals(intExpected, intArray)

        intArray = arrayOf(3, 4, 2, 0)
        intExpected = arrayOf(1, 4, 9, 16)
        intArray.mapInPlaceIndexed { index, _ -> (index + 1) * (index + 1) }
        assertContentEquals(intExpected, intArray)

        listArray = arrayOf(listOf(1, 2, 3), listOf(4), emptyList(), listOf(5, 4, 1, 0, 9))
        listExpected = arrayOf(listOf(1), listOf(0, "none"), listOf(0, "none"), listOf(0))
        listArray.mapInPlaceIndexed { index, list ->
            simpleIf(list.size <= index, { listOf(0, "none") }, { listOf(list[index]) })
        }
        assertContentEquals(listExpected, listArray)

        val nullArray: Array<String?> = arrayOf("hello", "world", null, null, "goodbye", "planet", "7", null)
        val nullExpected = arrayOf("50", null, "100", "25", "50", null, "50", "25")
        nullArray.mapInPlaceIndexed { index, value ->
            when {
                index % 2 == 0 && value == null -> "100"
                index % 2 == 0 -> "50"
                value == null -> "25"
                else -> null
            }
        }
        assertContentEquals(nullExpected, nullArray)
    }
}
