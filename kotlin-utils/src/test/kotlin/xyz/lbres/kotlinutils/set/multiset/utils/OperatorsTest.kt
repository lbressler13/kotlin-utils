package xyz.lbres.kotlinutils.set.multiset.utils

import kotlin.test.Test

class OperatorsTest {
    @Test
    fun testPerformPlus() {
        // TODO
    }

    @Test
    fun testPerformMinus() {
        // TODO
    }

    @Test
    fun testPerformIntersect() {
        // TODO
    }

//    @Test
//    fun testCombineCounts() {
//        // empty
//        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
//        var intSet: MultiSet<Int> = emptyMultiSet()
//        var intResult = combineCounts(intCounts, intSet, Int::minus, false, false)
//        assertEquals(emptyMultiSet(), intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        intCounts = CountsMap.from(listOf(1, 4, 1, 1))
//        intSet = emptyMultiSet()
//        var intExpected = multiSetOf(1, 1, 1, 4)
//        intResult = combineCounts(intCounts, intSet, Int::minus, false, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        // constant map function
//        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
//        intSet = multiSetOf(1, 2, -4, 5, 5)
//        var map: (Int, Int) -> Int = { _, _ -> 0 }
//        intExpected = emptyMultiSet()
//        intResult = combineCounts(intCounts, intSet, map, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
//        intSet = multiSetOf(1, 2, -4, 5, 5)
//        map = { _, _ -> -1 }
//        intExpected = emptyMultiSet()
//        intResult = combineCounts(intCounts, intSet, map, true, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
//        intSet = multiSetOf(1, 2, -4, 5, 5)
//        map = { _, _ -> 2 }
//        intExpected = multiSetOf(-4, -4, 1, 1, 2, 2, 4, 4, 5, 5)
//        intResult = combineCounts(intCounts, intSet, map, true, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        // dynamic map function
//        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
//        intSet = multiSetOf(1, 2, -4, 5, 5)
//        intExpected = multiSetOf(1, 1, 1, 2, 2, 4, 5, 5, 5)
//        intResult = combineCounts(intCounts, intSet, Int::plus, false, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        intCounts = CountsMap.from(listOf(1, 4, 4, 4, -4, 5))
//        intSet = multiSetOf(1, 1, 5, 4, 4)
//        intExpected = multiSetOf(1, 1, 4, 4, 4, 4, 4, 4, 5)
//        intResult = combineCounts(intCounts, intSet, Int::times, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        // use all values
//        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
//        intSet = multiSetOf(1, 2, -4, 5, 5)
//        intExpected = multiSetOf(-4, 1, 1, 1, 2, 2, 4, 5, 5, 5)
//        intResult = combineCounts(intCounts, intSet, Int::plus, true)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        intCounts = CountsMap(mapOf(7 to 6, 4 to 1, 1 to 1, 8 to 3))
//        intSet = multiSetOf(6, 6, 7, 1, 8, 8, 7, 12, 1)
//        map = { i1, i2 -> simpleIf(i1.isZero(), i2, min(i1, i2)) }
//        intExpected = multiSetOf(7, 7, 6, 6, 8, 8, 1, 12)
//        intResult = combineCounts(intCounts, intSet, map, true, false)
//        assertEquals(intExpected, intResult)
//        assertIsNot<ConstMultiSet<*>>(intResult)
//
//        // const
//        intCounts = CountsMap.from(listOf(1, 4, 4, 4, -4, 5))
//        intSet = multiSetOf(1, 1, 5, 4, 4)
//        intExpected = multiSetOf(1, 1, 4, 4, 4, 4, 4, 4, 5)
//        intResult = combineCounts(intCounts, intSet, Int::times, false, true)
//        assertEquals(intExpected, intResult)
//        assertIs<ConstMultiSet<*>>(intResult)
//
//        intCounts = CountsMap.from(listOf(1, 4, 5, 1, 2))
//        intSet = multiSetOf(1, 2, -4, 5, 5)
//        intExpected = multiSetOf(1, 1, 1, 2, 2, 4, 5, 5, 5)
//        intResult = combineCounts(intCounts, intSet, Int::plus, false, true)
//        assertEquals(intExpected, intResult)
//        assertIs<ConstMultiSet<*>>(intResult)
//
//        // other types
//        val stringCounts = CountsMap.from(listOf("hello", "world", "hello world", "world", "world", "hello"))
//        val stringSet = multiSetOf("world", "world", "planet", "hello world")
//        val stringExpected = multiSetOf("world", "world", "hello world")
//        val stringResult = combineCounts(stringCounts, stringSet, ::min, true, true)
//        assertEquals(stringExpected, stringResult)
//        assertIs<ConstMultiSet<*>>(stringResult)
//
//        val map1 = CountsMap(mapOf(7 to 2, 8 to 3))
//        val map2 = CountsMap(mapOf(3 to 2, 1 to 7))
//        val map3 = CountsMap(mapOf(4 to 15, 1 to 3, 4 to 5, -6 to 1))
//        val countsMapCounts = CountsMap.from(listOf(map2, map3, map2, map1, map2, map1, map1, map2, map1, map3))
//        val countsMapSet = multiSetOf(map3, map2, map3, map3, map1, map1, map1, map2)
//        val countsMapExpected = multiSetOf(map1, map2, map2)
//        val countsMapResult = combineCounts(countsMapCounts, countsMapSet, Int::minus, false, false)
//        assertEquals(countsMapExpected, countsMapResult)
//        assertIsNot<ConstMultiSet<*>>(countsMapResult)
//    }
}
