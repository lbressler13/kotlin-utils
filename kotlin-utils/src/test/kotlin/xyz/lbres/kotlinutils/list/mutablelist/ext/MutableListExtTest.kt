package xyz.lbres.kotlinutils.list.mutablelist.ext

import kotlin.test.Test
import kotlin.test.assertEquals

class MutableListExtTest {
    @Test
    fun testSetAllValues() {
        // empty list
        var intList: MutableList<Int> = mutableListOf()
        var intExpected: MutableList<Int> = mutableListOf()
        intList.setAllValues(1)
        assertEquals(intExpected, intList)

        // no change
        intList = mutableListOf(1, 1, 1, 1)
        intExpected = mutableListOf(1, 1, 1, 1)
        intList.setAllValues(1)
        assertEquals(intExpected, intList)

        // some change
        intList = mutableListOf(1, -1, -1, 2, 4)
        intExpected = mutableListOf(-1, -1, -1, -1, -1)
        intList.setAllValues(-1)
        assertEquals(intExpected, intList)

        var listList = mutableListOf(listOf("hello", "world"), listOf("goodbye", "planet", "moon"), listOf("random string"))
        var listExpected = mutableListOf(listOf("hello", "world"), listOf("hello", "world"), listOf("hello", "world"))
        listList.setAllValues(listOf("hello", "world"))
        assertEquals(listExpected, listList)

        var nullList: MutableList<Int?> = mutableListOf(1, 2, null, 3)
        var nullExpected: List<Int?> = mutableListOf(1, 1, 1, 1)
        nullList.setAllValues(1)
        assertEquals(nullExpected, nullList)

        nullList = mutableListOf(1, 2, null, 3)
        nullExpected = mutableListOf(null, null, null, null)
        nullList.setAllValues(null)
        assertEquals<List<Int?>>(nullExpected, nullList)

        // all change
        intList = mutableListOf(100, 200, 500, 355)
        intExpected = mutableListOf(99, 99, 99, 99)
        intList.setAllValues(99)
        assertEquals(intExpected, intList)

        listList = mutableListOf(listOf("hello", "world"), listOf("goodbye", "planet", "moon"), listOf("random string"))
        listExpected = mutableListOf(emptyList(), emptyList(), emptyList())
        listList.setAllValues(emptyList())
        assertEquals(listExpected, listList)

        nullList = mutableListOf(1, 2, 3, 4)
        nullExpected = mutableListOf(null, null, null, null)
        nullList.setAllValues(null)
        assertEquals<List<Int?>>(nullExpected, nullList)

        nullList = mutableListOf(null, null, null, null)
        nullExpected = mutableListOf(53, 53, 53, 53)
        nullList.setAllValues(53)
        assertEquals<List<Int?>>(nullExpected, nullList)
    }
}
