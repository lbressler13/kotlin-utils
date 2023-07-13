package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.immutable.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MultiSetImplTest {
    @Test fun testConstructor() = runImmutableConstructorTests()
    @Test fun testEquals() = runImmutableEqualsTests()

    @Test fun testContains() = runImmutableContainsTests()
    @Test fun testContainsAll() = runImmutableContainsAllTests()

    @Test fun testMinus() = runImmutableMinusTests()
    @Test fun testPlus() = runImmutablePlusTests()
    @Test fun testIntersect() = runImmutableIntersectTests()

    @Test fun testIsEmpty() = runImmutableIsEmptyTests()
    @Test fun testGetCountOf() = runImmutableGetCountOfTests()

    @Test
    fun testIterator() {
        var set: MultiSet<Int> = multiSetOf()
        var iter = set.iterator()
        assertFalse(iter.hasNext())

        set = multiSetOf(1, 2, 3, 4)
        iter = set.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set = multiSetOf(1, 2, 3, 4, 1, 4, 5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(0, 5, 7)
        val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)

        var listIter = listSet.iterator()
        var listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
        var listValues: MutableList<IntList> = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)

        mutableList2.clear()
        listIter = listSet.iterator()
        listExpected = listOf(listOf(1, 2, 3), emptyList())
        listValues = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)
    }

    @Test
    fun testToString() {
        var set: MultiSet<Int> = emptyMultiSet()
        var expected = "[]"
        assertEquals(expected, set.toString())

        set = multiSetOf(100, -200, 400, 800)
        expected = "[100, -200, 400, 800]"
        assertEquals(expected, set.toString())

        set = multiSetOf(1, 1, 1, 1)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, set.toString())

        set = multiSetOf(2, 4, 2, 1)
        expected = "[2, 4, 2, 1]"
        assertEquals(expected, set.toString())

        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(0, 5, 7)
        val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)

        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listSet.toString())

        mutableList2.clear()
        expected = "[[1, 2, 3], []]"
        assertEquals(expected, listSet.toString())
    }
}
