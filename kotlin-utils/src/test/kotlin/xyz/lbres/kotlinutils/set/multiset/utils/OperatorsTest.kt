package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.CompList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertIsNot

// light testing, heavier tests are in the MultiSet classes
class OperatorsTest {
    @Test
    fun testPerformPlus() {
        // empty
        runSingleTest<Int>(CountsMap(emptyMap()), emptyMultiSet(), emptyMultiSet(), ::performPlus)
        runSingleTest(CountsMap(emptyMap()), multiSetOf(1, 2, 3), multiSetOf(1, 2, 3), ::performPlus)
        runSingleTest(CountsMap(mapOf(3 to 2)), emptyMultiSet(), multiSetOf(3, 3), ::performPlus)

        // non-empty
        runSingleTest(CountsMap.from(listOf(1)), multiSetOf(1), multiSetOf(1, 1), ::performPlus)

        var intCounts = CountsMap.from(listOf(1, 2, 2, 3, 3, 3))
        var intSet = multiSetOf(1, 2, 0)
        var intExpected = multiSetOf(1, 2, 2, 3, 3, 3, 1, 2, 0)
        runSingleTest(intCounts, intSet, intExpected, ::performPlus)

        intCounts = CountsMap.from(listOf(0, 4, 5, -1))
        intSet = multiSetOf(1, 2, 3)
        intExpected = multiSetOf(-1, 0, 1, 2, 3, 4, 5)
        runSingleTest(intCounts, intSet, intExpected, ::performPlus)

        val listCounts: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
        val listSet: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        val listExpected: MultiSet<CompList> =
            multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"), listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        runSingleTest(listCounts, listSet, listExpected, ::performPlus)

        // mutable
        intCounts = CountsMap.from(listOf(1, 2))
        val mutableSet = mutableMultiSetOf(2, 3, 4)
        intExpected = multiSetOf(1, 2, 2, 3, 4)
        runSingleTest(intCounts, mutableSet, intExpected, ::performPlus)

        mutableSet.remove(3)
        intExpected = multiSetOf(1, 2, 2, 4)
        runSingleTest(intCounts, mutableSet, intExpected, ::performPlus)
    }

    @Test
    fun testPerformMinus() {
        // empty
        runSingleTest<Int>(CountsMap(emptyMap()), emptyMultiSet(), emptyMultiSet(), ::performMinus)
        runSingleTest(CountsMap(emptyMap()), multiSetOf(1, 2, 3), emptyMultiSet(), ::performMinus)
        runSingleTest(CountsMap(mapOf(3 to 2)), emptyMultiSet(), multiSetOf(3, 3), ::performMinus)

        // non-empty
        var intCounts = CountsMap.from(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
        var intSet = multiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
        var intExpected = multiSetOf(2, 2, 6)
        runSingleTest(intCounts, intSet, intExpected, ::performMinus)

        intCounts = CountsMap.from(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
        intSet = multiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
        intExpected = multiSetOf(1, 5, 5, 7)
        runSingleTest(intCounts, intSet, intExpected, ::performMinus)

        intCounts = CountsMap.from(listOf(1, 2, 3, 4))
        intSet = multiSetOf(5, 6, 7, 8)
        intExpected = multiSetOf(1, 2, 3, 4)
        runSingleTest(intCounts, intSet, intExpected, ::performMinus)

        val listCounts: CountsMap<CompList> = CountsMap.from(multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
        val listSet: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val listExpected: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), emptyList())
        runSingleTest(listCounts, listSet, listExpected, ::performMinus)

        // mutable
        intCounts = CountsMap.from(listOf(1, 2, 3, 4))
        val mutableSet = mutableMultiSetOf(2, 3, 4)
        intExpected = multiSetOf(1)
        runSingleTest(intCounts, mutableSet, intExpected, ::performMinus)

        mutableSet.remove(3)
        intExpected = multiSetOf(1, 3)
        runSingleTest(intCounts, mutableSet, intExpected, ::performMinus)
    }

    @Test
    fun testPerformIntersect() {
        // empty
        runSingleTest<Int>(CountsMap(emptyMap()), emptyMultiSet(), emptyMultiSet(), ::performIntersect)
        runSingleTest(CountsMap(emptyMap()), multiSetOf(1, 2, 3), emptyMultiSet(), ::performIntersect)
        runSingleTest(CountsMap(mapOf(3 to 2)), emptyMultiSet(), emptyMultiSet(), ::performIntersect)

        // non-empty
        var intCounts = CountsMap.from(multiSetOf(1, 2, 3))
        var intSet = multiSetOf(4, 5, 6, 7, 8)
        var intExpected: MultiSet<Int> = emptyMultiSet()
        runSingleTest(intCounts, intSet, intExpected, ::performIntersect)

        intCounts = CountsMap.from(multiSetOf(4, 5, 6, 7, 8))
        intSet = multiSetOf(1, 2, 3)
        runSingleTest(intCounts, intSet, intExpected, ::performIntersect)

        intCounts = CountsMap.from(listOf(1, 2, 3))
        intSet = multiSetOf(1, 2, 3)
        intExpected = multiSetOf(1, 2, 3)
        runSingleTest(intCounts, intSet, intExpected, ::performIntersect)

        intCounts = CountsMap.from(multiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10))
        intSet = multiSetOf(-1, 14, 3, 9, 9, 6)
        intExpected = multiSetOf(-1, 6)
        runSingleTest(intCounts, intSet, intExpected, ::performIntersect)

        val listCounts: CountsMap<CompList> = CountsMap.from(multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
        val listSet: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val listExpected: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3))
        runSingleTest(listCounts, listSet, listExpected, ::performIntersect)

        // mutable
        intCounts = CountsMap.from(listOf(1, 2, 3))
        val mutableSet = mutableMultiSetOf(2, 3, 4, 2)
        intExpected = multiSetOf(2, 3)
        runSingleTest(intCounts, mutableSet, intExpected, ::performIntersect)

        mutableSet.remove(3)
        intExpected = multiSetOf(2)
        runSingleTest(intCounts, mutableSet, intExpected, ::performIntersect)
    }

    // run single test for a given map, set, and operator
    private fun <T> runSingleTest(
        counts: CountsMap<T>,
        set: MultiSet<T>,
        expected: MultiSet<T>,
        operator: (CountsMap<T>, MultiSet<T>, Boolean) -> MultiSet<T>
    ) {
        // non-const
        var result = operator(counts, set, false)
        assertEquals(expected, result)
        assertIsNot<ConstMultiSet<T>>(result)
        // const
        result = operator(counts, set, true)
        assertEquals(expected, result)
        assertIs<ConstMultiSet<T>>(result)
    }
}
