package xyz.lbres.kotlinutils.map.mutablemap.ext

import xyz.lbres.kotlinutils.CompList
import kotlin.test.Test
import kotlin.test.assertEquals

class MutableMapExtTest {
    @Test
    fun testSetAllValues() {
        // empty map
        var intIntMap: MutableMap<Int, Int> = mutableMapOf()
        var intIntExpected: Map<Int, Int> = emptyMap()
        intIntMap.setAllValues(1)
        assertEquals(intIntExpected, intIntMap)

        // no change
        intIntMap = mutableMapOf(5 to 1)
        intIntExpected = mutableMapOf(5 to 1)
        intIntMap.setAllValues(1)
        assertEquals(intIntExpected, intIntMap)

        intIntMap = mutableMapOf(100 to 999, 200 to 999, -13 to 999)
        intIntExpected = mutableMapOf(100 to 999, 200 to 999, -13 to 999)
        intIntMap.setAllValues(999)
        assertEquals(intIntExpected, intIntMap)

        // some change
        intIntMap = mutableMapOf(1 to 1, 2 to 1, 3 to 4)
        intIntExpected = mapOf(1 to 1, 2 to 1, 3 to 1)
        intIntMap.setAllValues(1)
        assertEquals(intIntExpected, intIntMap)

        intIntMap = mutableMapOf(17 to 123, 13 to -425, 5 to 0, 7 to 987)
        intIntExpected = mapOf(17 to -425, 13 to -425, 5 to -425, 7 to -425)
        intIntMap.setAllValues(-425)
        assertEquals(intIntExpected, intIntMap)

        val listListMap: MutableMap<CompList, CompList> = mutableMapOf(listOf(1, 2, 3) to listOf(4, 5), listOf("hello") to listOf("world"), listOf(12, 'a') to emptyList())
        val listListExpected: Map<CompList, CompList> = mapOf(listOf(1, 2, 3) to emptyList(), listOf("hello") to emptyList(), listOf(12, 'a') to emptyList())
        listListMap.setAllValues(emptyList())
        assertEquals(listListExpected, listListMap)

        var nullValuesMap: MutableMap<String, String?> = mutableMapOf("hello" to null, "world" to null, "goodbye" to "planet")
        var nullValuesExpected: Map<String, String?> = mapOf("hello" to "planet", "world" to "planet", "goodbye" to "planet")
        nullValuesMap.setAllValues("planet")
        assertEquals(nullValuesExpected, nullValuesMap)

        nullValuesMap = mutableMapOf("hello" to null, "world" to null, "goodbye" to "planet")
        nullValuesExpected = mapOf("hello" to null, "world" to null, "goodbye" to null)
        nullValuesMap.setAllValues(null)
        assertEquals<Map<String, String?>>(nullValuesExpected, nullValuesMap)

        // all change
        intIntMap = mutableMapOf(17 to 123, 13 to -420, 5 to 0, 7 to 987)
        intIntExpected = mapOf(17 to -425, 13 to -425, 5 to -425, 7 to -425)
        intIntMap.setAllValues(-425)
        assertEquals(intIntExpected, intIntMap)

        val stringIntMap = mutableMapOf("abc" to 123, "def" to 456, "what" to 238120)
        val stringIntExpected = mapOf("abc" to 85121215, "def" to 85121215, "what" to 85121215)
        stringIntMap.setAllValues(85121215)
        assertEquals(stringIntExpected, stringIntMap)

        val nullKeyMap = mutableMapOf(setOf(1, 2, 3) to 3, null to 0, setOf(999) to 2)
        val nullKeyExpected = mapOf(setOf(1, 2, 3) to -1, null to -1, setOf(999) to -1)
        nullKeyMap.setAllValues(-1)
        assertEquals(nullKeyExpected, nullKeyMap)
    }
}
