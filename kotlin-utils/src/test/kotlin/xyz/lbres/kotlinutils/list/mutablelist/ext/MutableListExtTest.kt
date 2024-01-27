package xyz.lbres.kotlinutils.list.mutablelist.ext

import xyz.lbres.kotlinutils.general.simpleIf
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

    @Test
    fun testMapInPlace() {
        // empty list
        var intList: MutableList<Int> = mutableListOf()
        var intExpected: MutableList<Int> = mutableListOf()
        intList.mapInPlace { it * 3 - 4 }
        assertEquals(intExpected, intList)

        // constant value
        intList = mutableListOf(3, 4, 6)
        intExpected = mutableListOf(0, 0, 0)
        intList.mapInPlace { 0 }
        assertEquals(intExpected, intList)

        var listList = mutableListOf(listOf(4, 5, 7), listOf(9, 8, 'c'))
        var listExpected = mutableListOf(listOf(1.5, 'a'), listOf(1.5, 'a'))
        listList.mapInPlace { listOf(1.5, 'a') }
        assertEquals(listExpected, listList)

        // transform function
        intList = mutableListOf(-1, -1, -1)
        intExpected = mutableListOf(-7, -7, -7)
        intList.mapInPlace { it * 3 - 4 }
        assertEquals(intExpected, intList)

        intList = mutableListOf(3, 4, 2, 0)
        intExpected = mutableListOf(5, 8, 2, -4)
        intList.mapInPlace { it * 3 - 4 }
        assertEquals(intExpected, intList)

        listList = mutableListOf(listOf(1, 2, 3), listOf(4, "hello"), emptyList(), listOf(5.7))
        listExpected = mutableListOf(listOf(3), listOf("hello"), listOf(0, "none"), listOf(5.7))
        listList.mapInPlace {
            simpleIf(it.isEmpty(), { listOf(0, "none") }, { listOf(it.last()) })
        }
        assertEquals(listExpected, listList)

        var count = 0
        val nullList: MutableList<String?> = mutableListOf("hello", "world", null, null, "goodbye", "planet", "7", null)
        val nullExpected = mutableListOf("50", null, "100", "25", "50", null, "50", "25")
        nullList.mapInPlace {
            val result = when {
                count % 2 == 0 && it == null -> "100"
                count % 2 == 0 -> "50"
                it == null -> "25"
                else -> null
            }

            count++
            result
        }
        assertEquals(nullExpected, nullList)
    }

    @Test
    fun testMapInPlaceIndexed() {
        // empty list
        var intList: MutableList<Int> = mutableListOf()
        var intExpected: MutableList<Int> = mutableListOf()
        intList.mapInPlaceIndexed { index, value -> value * 3 - index }
        assertEquals(intExpected, intList)

        // constant value
        intList = mutableListOf(3, 4, 6)
        intExpected = mutableListOf(0, 0, 0)
        intList.mapInPlaceIndexed { _, _ -> 0 }
        assertEquals(intExpected, intList)

        var listList = mutableListOf(listOf(4, 5, 7), listOf(9, 8, 'c'))
        var listExpected = mutableListOf(listOf(1.5, 'a'), listOf(1.5, 'a'))
        listList.mapInPlaceIndexed { _, _ -> listOf(1.5, 'a') }
        assertEquals(listExpected, listList)

        // transform function
        intList = mutableListOf(-1, -1, -1)
        intExpected = mutableListOf(-3, -4, -5)
        intList.mapInPlaceIndexed { index, value -> value * 3 - index }
        assertEquals(intExpected, intList)

        intList = mutableListOf(3, 4, 2, 0)
        intExpected = mutableListOf(9, 11, 4, -3)
        intList.mapInPlaceIndexed { index, value -> value * 3 - index }
        assertEquals(intExpected, intList)

        intList = mutableListOf(3, 4, 2, 0)
        intExpected = mutableListOf(1, 4, 9, 16)
        intList.mapInPlaceIndexed { index, _ -> (index + 1) * (index + 1) }
        assertEquals(intExpected, intList)

        listList = mutableListOf(listOf(1, 2, 3), listOf(4), emptyList(), listOf(5, 4, 1, 0, 9))
        listExpected = mutableListOf(listOf(1), listOf(0, "none"), listOf(0, "none"), listOf(0))
        listList.mapInPlaceIndexed { index, list ->
            if (list.isEmpty() || list.size <= index) {
                listOf(0, "none")
            } else {
                listOf(list[index])
            }
        }
        assertEquals(listExpected, listList)

        val nullList: MutableList<String?> = mutableListOf("hello", "world", null, null, "goodbye", "planet", "7", null)
        val nullExpected = mutableListOf("50", null, "100", "25", "50", null, "50", "25")
        nullList.mapInPlaceIndexed { index, value ->
            when {
                index % 2 == 0 && value == null -> "100"
                index % 2 == 0 -> "50"
                value == null -> "25"
                else -> null
            }
        }
        assertEquals(nullExpected, nullList)
    }
}
