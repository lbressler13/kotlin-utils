package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.Test
import kotlin.test.assertContains
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

    @Test
    fun testCountsToString() {
        var intCounts: Map<Int, Int> = emptyMap()
        assertEquals("[]", countsToString(intCounts))

        intCounts = mapOf(4 to 1)
        assertEquals("[4]", countsToString(intCounts))

        intCounts = mapOf(4 to 3)
        assertEquals("[4, 4, 4]", countsToString(intCounts))

        intCounts = mapOf(3 to 1, 1 to 2, 2 to 1)
        var options = setOf(
            "[1, 1, 2, 3]", "[1, 1, 3, 2]", "[2, 1, 1, 3]", "[2, 3, 1, 1]", "[3, 1, 1, 2]", "[3, 2, 1, 1]"
        )
        assertContains(options, countsToString(intCounts))

        var stringCounts = mapOf("abc" to 4, "hello" to 2, "world" to 3)
        options = setOf(
            "[abc, abc, abc, abc, hello, hello, world, world, world]",
            "[abc, abc, abc, abc, world, world, world, hello, hello]",
            "[hello, hello, abc, abc, abc, abc, world, world, world]",
            "[hello, hello, world, world, world, abc, abc, abc, abc]",
            "[world, world, world, abc, abc, abc, abc, hello, hello]",
            "[world, world, world, hello, hello, abc, abc, abc, abc]"
        )
        assertContains(options, countsToString(stringCounts))

        stringCounts = mapOf("" to 6)
        assertEquals("[, , , , , ]", countsToString(stringCounts))

        val listCounts = mapOf(listOf(1, 2, 3) to 2, listOf("hello") to 1)
        options = setOf("[[1, 2, 3], [1, 2, 3], [hello]]", "[[hello], [1, 2, 3], [1, 2, 3]]")
        assertContains(options, countsToString(listCounts))
    }

    @Test
    fun testCountsToList() {
        var intCounts: Map<Int, Int> = emptyMap()
        assertEquals(emptyList(), countsToList(intCounts))

        intCounts = mapOf(2 to 3)
        assertEquals(listOf(2, 2, 2), countsToList(intCounts))

        intCounts = mapOf(2 to 0, 0 to 2)
        assertEquals(listOf(0, 0), countsToList(intCounts))

        intCounts = mapOf(1 to 2, 8 to 1, -4 to 3)
        val intOptions = setOf(
            listOf(1, 1, 8, -4, -4, -4),
            listOf(1, 1, -4, -4, -4, 8),
            listOf(8, 1, 1, -4, -4, -4),
            listOf(8, -4, -4, -4, 1, 1),
            listOf(-4, -4, -4, 1, 1, 8),
            listOf(-4, -4, -4, 8, 1, 1)
        )
        assertContains(intOptions, countsToList(intCounts))

        val stringCounts = mapOf("hello" to 1, "world" to 2)
        val stringOptions = setOf(listOf("hello", "world", "world"), listOf("world", "world", "hello"))
        assertContains(stringOptions, countsToList(stringCounts))
    }

    @Test fun testCombineCounts() = runCombineCountsTests()
}
