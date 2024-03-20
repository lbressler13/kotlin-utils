package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Test
    fun testCreateCountsMap() {
        var intValues: Collection<Int> = emptyList()
        assertEquals(emptyMap(), createCountsMap(intValues))

        intValues = setOf(1)
        assertEquals(mapOf(1 to 1), createCountsMap(intValues))

        intValues = listOf(1, 1, 1)
        assertEquals(mapOf(1 to 3), createCountsMap(intValues))

        intValues = multiSetOf(1, 1, 4, 1, 2, 4)
        assertEquals(mapOf(1 to 3, 2 to 1, 4 to 2), createCountsMap(intValues))

        val stringValues = listOf("hello", "", "world", "", "hello world", "", "world", "")
        val stringExpected = mapOf("hello" to 1, "world" to 2, "hello world" to 1, "" to 4)
        assertEquals(stringExpected, createCountsMap(stringValues))

        val listValues = listOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc"), listOf("a", "b", "c"))
        val listExpected = mapOf(listOf(1, 2, 3) to 2, listOf("abc") to 1, listOf("a", "b", "c") to 1)
        assertEquals(listExpected, createCountsMap(listValues))
    }

    @Test fun testCombineCounts() = runCombineCountsTests()
}
