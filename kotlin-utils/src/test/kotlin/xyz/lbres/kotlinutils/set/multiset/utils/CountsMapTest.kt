package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.set.multiset.utils.countsmap.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CountsMapTest {
    @Test fun testFrom() = runFromTests()
    @Test fun testDistinct() = runDistinctTests() // TODO

    @Test fun testGetCountOf() = runGetCountOfTests()
    @Test fun testIsEmpty() = runIsEmptyTests()

    @Test fun testContains() = runContainsTests()
    @Test fun testContainsAll() = runContainsAllTests()

    @Test
    fun testForEach() {
        // TODO
    }

    @Test
    fun testToString() {
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        assertEquals("[]", intCounts.toString())

        intCounts = CountsMap(mapOf(4 to 1))
        assertEquals("[4]", intCounts.toString())

        intCounts = CountsMap(mapOf(4 to 3))
        assertEquals("[4, 4, 4]", intCounts.toString())

        intCounts = CountsMap(mapOf(3 to 1, 1 to 2, 2 to 1))
        var options = setOf(
            "[1, 1, 2, 3]", "[1, 1, 3, 2]", "[2, 1, 1, 3]", "[2, 3, 1, 1]", "[3, 1, 1, 2]", "[3, 2, 1, 1]"
        )
        assertContains(options, intCounts.toString())

        var stringCounts = CountsMap(mapOf("abc" to 4, "hello" to 2, "world" to 3))
        options = setOf(
            "[abc, abc, abc, abc, hello, hello, world, world, world]",
            "[abc, abc, abc, abc, world, world, world, hello, hello]",
            "[hello, hello, abc, abc, abc, abc, world, world, world]",
            "[hello, hello, world, world, world, abc, abc, abc, abc]",
            "[world, world, world, abc, abc, abc, abc, hello, hello]",
            "[world, world, world, hello, hello, abc, abc, abc, abc]"
        )
        assertContains(options, stringCounts.toString())

        stringCounts = CountsMap(mapOf("" to 6))
        assertEquals("[, , , , , ]", stringCounts.toString())

        val listCounts = CountsMap(mapOf(listOf(1, 2, 3) to 2, listOf("hello") to 1))
        options = setOf("[[1, 2, 3], [1, 2, 3], [hello]]", "[[hello], [1, 2, 3], [1, 2, 3]]")
        assertContains(options, listCounts.toString())
    }

    @Test
    fun testToList() {
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        assertEquals(emptyList(), intCounts.toList())

        intCounts = CountsMap(mapOf(2 to 3))
        assertEquals(listOf(2, 2, 2), intCounts.toList())

        intCounts = CountsMap(mapOf(0 to 2))
        assertEquals(listOf(0, 0), intCounts.toList())

        intCounts = CountsMap(mapOf(1 to 2, 8 to 1, -4 to 3))
        val intOptions = setOf(
            listOf(1, 1, 8, -4, -4, -4),
            listOf(1, 1, -4, -4, -4, 8),
            listOf(8, 1, 1, -4, -4, -4),
            listOf(8, -4, -4, -4, 1, 1),
            listOf(-4, -4, -4, 1, 1, 8),
            listOf(-4, -4, -4, 8, 1, 1)
        )
        assertContains(intOptions, intCounts.toList())

        val stringCounts = CountsMap(mapOf("hello" to 1, "world" to 2))
        val stringOptions = setOf(listOf("hello", "world", "world"), listOf("world", "world", "hello"))
        assertContains(stringOptions, stringCounts.toList())
    }
}
