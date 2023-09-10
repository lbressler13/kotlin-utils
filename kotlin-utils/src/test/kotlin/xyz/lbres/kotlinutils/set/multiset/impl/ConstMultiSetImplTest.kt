package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ConstMultiSetImplTest {
    @Test fun testConstructor() = runConstConstructorTests()
    @Test fun testEquals() = runConstEqualsTests()

    @Test fun testContains() = runConstContainsTests()
    @Test fun testContainsAll() = runConstContainsAllTests()

    @Test fun testMinus() = runConstMinusTests()
    @Test fun testPlus() = runConstPlusTests()
    @Test fun testIntersect() = runConstIntersectTests()

    @Test fun testIsEmpty() = runConstIsEmptyTests()
    @Test fun testGetCountOf() = runConstGetCountOfTests()

    @Test
    fun testIterator() {
        var set: ConstMultiSet<Int> = constMultiSetOf()
        var iter = set.iterator()
        assertFalse(iter.hasNext())

        set = constMultiSetOf(1, 2, 3, 4)
        iter = set.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set = constMultiSetOf(1, 2, 3, 4, 1, 4, 5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        val list1 = mutableListOf(1, 2, 3)
        val list2 = mutableListOf(0, 5, 7)
        val listSet: ConstMultiSet<IntList> = constMultiSetOf(list1, list2)

        val listIter = listSet.iterator()
        val listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
        val listValues: MutableList<IntList> = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)
    }

    @Test
    fun testToString() {
        var set: ConstMultiSet<Int> = emptyConstMultiSet()
        var expected = "[]"
        assertEquals(expected, set.toString())

        set = constMultiSetOf(100, -200, 400, 800)
        expected = "[100, -200, 400, 800]"
        assertEquals(expected, set.toString())

        set = constMultiSetOf(1, 1, 1, 1)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, set.toString())

        set = constMultiSetOf(2, 4, 2, 1)
        expected = "[2, 4, 2, 1]"
        assertEquals(expected, set.toString())

        val list1 = listOf(1, 2, 3)
        val list2 = listOf(0, 5, 7)
        val listSet: ConstMultiSet<IntList> = constMultiSetOf(list1, list2)

        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listSet.toString())
    }
}
