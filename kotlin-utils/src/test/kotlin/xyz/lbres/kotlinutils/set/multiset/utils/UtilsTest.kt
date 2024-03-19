package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertIsNot

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

    @Test
    fun testCombineCounts() {
        // empty
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        var intSet: MultiSet<Int> = emptyMultiSet()
        var intResult = combineCounts(intCounts, intSet, Int::minus, false, false)
        assertEquals(emptyMultiSet(), intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        intCounts = CountsMap.from(listOf(1, 4, 1, 1))
        intSet = emptyMultiSet()
        var intExpected = multiSetOf(1, 1, 1, 4)
        intResult = combineCounts(intCounts, intSet, Int::minus, false, false)
        assertEquals(intExpected, intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        // nonempty
        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
        intSet = multiSetOf(1, 2, -4, 5, 5)
        intExpected = multiSetOf(1, 1, 1, 2, 2, 4, 5, 5, 5)
        intResult = combineCounts(intCounts, intSet, Int::plus, false, false)
        assertEquals(intExpected, intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        intCounts = CountsMap.from(listOf(1, 4, 4, 4, -4, 5))
        intSet = multiSetOf(1, 1, 5, 4, 4)
        intExpected = multiSetOf(1, 1, 4, 4, 4, 4, 4, 4, 5)
        intResult = combineCounts(intCounts, intSet, Int::times, false, false)
        assertEquals(intExpected, intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        intCounts = CountsMap.from(listOf(1, 4, 5, 2, 1, 2, 2, 2))
        intSet = multiSetOf(1, 1, 1, 2, 4, 7, 18)
        intExpected = multiSetOf(2, 2, 2, 5)
        intResult = combineCounts(intCounts, intSet, Int::minus, false, false)
        assertEquals(intExpected, intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        // use all values
        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
        intSet = multiSetOf(1, 2, -4, 5, 5)
        intExpected = multiSetOf(-4, 1, 1, 1, 2, 2, 4, 5, 5, 5)
        intResult = combineCounts(intCounts, intSet, Int::plus, true, false)
        assertEquals(intExpected, intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        intCounts = CountsMap(mapOf(7 to 6, 4 to 1, 1 to 1, 8 to 3))
        intSet = multiSetOf(6, 6, 7, 1, 8, 8, 7, 12, 1)
        val map: (Int, Int) -> Int = { i1, i2 -> simpleIf(i1.isZero(), i2, min(i1, i2)) }
        intExpected = multiSetOf(7, 7, 6, 6, 8, 8, 1, 12)
        intResult = combineCounts(intCounts, intSet, map, true, false)
        assertEquals(intExpected, intResult)
        assertIsNot<ConstMultiSet<*>>(intResult)

        // const
        intCounts = CountsMap.from(listOf(1, 4, 4, 4, -4, 5))
        intSet = multiSetOf(1, 1, 5, 4, 4)
        intExpected = multiSetOf(1, 1, 4, 4, 4, 4, 4, 4, 5)
        intResult = combineCounts(intCounts, intSet, Int::times, false, true)
        assertEquals(intExpected, intResult)
        assertIs<ConstMultiSet<*>>(intResult)

        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
        intSet = multiSetOf(1, 2, -4, 5, 5)
        intExpected = multiSetOf(1, 1, 1, 2, 2, 4, 5, 5, 5)
        intResult = combineCounts(intCounts, intSet, Int::plus, false, true)
        assertEquals(intExpected, intResult)
        assertIs<ConstMultiSet<*>>(intResult)

        // other types
        val stringCounts = CountsMap.from(listOf("hello", "world", "hello world", "world", "world", "hello"))
        val stringSet = multiSetOf("world", "world", "planet", "hello world")
        val stringExpected = multiSetOf("world", "world", "hello world")
        val stringResult = combineCounts(stringCounts, stringSet, ::min, true, true)
        assertEquals(stringExpected, stringResult)
        assertIs<ConstMultiSet<*>>(stringResult)

        val map1 = CountsMap(mapOf(7 to 2, 8 to 3))
        val map2 = CountsMap(mapOf(3 to 2, 1 to 7))
        val map3 = CountsMap(mapOf(4 to 15, 1 to 3, 4 to 5, -6 to 1))
        val countsMapCounts = CountsMap.from(listOf(map2, map3, map2))
        val countsMapSet = multiSetOf(map3, map2, map3, map3, map1, map1, map1, map2)
        val countsMapExpected = multiSetOf(map2, map3, map2, map3, map2, map3, map3, map2)
        val countsMapResult = combineCounts(countsMapCounts, countsMapSet, Int::plus, false, false)
        assertEquals(countsMapExpected, countsMapResult)
        assertIsNot<ConstMultiSet<*>>(countsMapResult)
    }
}
