package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.set.multiset.utils.countsmap.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.math.max
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CountsMapTest {
    @Test fun testFrom() = runFromTests()
    @Test fun testDistinct() = runDistinctTests()

    @Test fun testGetCountOf() = runGetCountOfTests()
    @Test fun testIsEmpty() = runIsEmptyTests()

    @Test fun testContains() = runContainsTests()
    @Test fun testContainsAll() = runContainsAllTests()

    @Test
    fun testForEach() {
        var total = 0
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        intCounts.forEach { element, count -> total += element * count }
        assertEquals(0, total)

        intCounts = CountsMap.from(listOf(1, 1, 2, 2, -7))
        intCounts.forEach { element, count -> total += element * count }
        assertEquals(-1, total)

        val intSet: MutableSet<Int> = mutableSetOf()
        intCounts = CountsMap.from(listOf(3, 7, 3, 1, 1, 1, 0, 0))
        intCounts.forEach { element, count -> intSet.add(max(element, count)) }
        assertEquals(setOf(3, 7, 2), intSet)

        var string = ""
        var stringCounts = CountsMap.from(listOf("hello", "world", "goodbye", "farewell", "hello", "farewell", "hello"))
        stringCounts.forEach { element, _ -> if (string.length < element.length) string = element }
        assertEquals("farewell", string)

        string = ""
        stringCounts = CountsMap.from(listOf("abcd", "abcd", "abcd", "123", "hello", "hello"))
        stringCounts.forEach { element, count -> repeat(count) { string += element[count] } }
        val stringOptions = setOf("ddd2ll", "dddll2", "2dddll", "2llddd", "llddd2", "ll2ddd")
        assertContains(stringOptions, string)
    }

    @Test
    fun testGetString() {
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        assertEquals("[]", intCounts.getString())

        intCounts = CountsMap(mapOf(4 to 1))
        assertEquals("[4]", intCounts.getString())

        intCounts = CountsMap(mapOf(4 to 3))
        assertEquals("[4, 4, 4]", intCounts.getString())

        intCounts = CountsMap(mapOf(3 to 1, 1 to 2, 2 to 1))
        var options = setOf(
            "[1, 1, 2, 3]", "[1, 1, 3, 2]", "[2, 1, 1, 3]", "[2, 3, 1, 1]", "[3, 1, 1, 2]", "[3, 2, 1, 1]"
        )
        assertContains(options, intCounts.getString())

        var stringCounts = CountsMap(mapOf("abc" to 4, "hello" to 2, "world" to 3))
        options = setOf(
            "[abc, abc, abc, abc, hello, hello, world, world, world]",
            "[abc, abc, abc, abc, world, world, world, hello, hello]",
            "[hello, hello, abc, abc, abc, abc, world, world, world]",
            "[hello, hello, world, world, world, abc, abc, abc, abc]",
            "[world, world, world, abc, abc, abc, abc, hello, hello]",
            "[world, world, world, hello, hello, abc, abc, abc, abc]"
        )
        assertContains(options, stringCounts.getString())

        stringCounts = CountsMap(mapOf("" to 6))
        assertEquals("[, , , , , ]", stringCounts.getString())

        val listCounts = CountsMap(mapOf(listOf(1, 2, 3) to 2, listOf("hello") to 1))
        options = setOf("[[1, 2, 3], [1, 2, 3], [hello]]", "[[hello], [1, 2, 3], [1, 2, 3]]")
        assertContains(options, listCounts.getString())
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
