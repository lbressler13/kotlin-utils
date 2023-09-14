package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.mutableconst.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MutableConstMultiSetImplImplTest {
    @Test fun testConstructor() = runMutableConstConstructorTests()
    @Test fun testEquals() = runMutableConstEqualsTests()

    @Test fun testContains() = runMutableConstContainsTests()
    @Test fun testContainsAll() = runMutableConstContainsAllTests()

    @Test fun testClear() = runClearTests()
    @Test fun testAdd() = runAddTests()
    @Test fun testAddAll() = runAddAllTests()
    @Test fun testRemove() = runRemoveTests()
    @Test fun testRemoveAll() = runRemoveAllTests()
    @Test fun testRetainAll() = runRetainAllTests()

    @Test fun testMinus() = runMutableConstMinusTests()
    @Test fun testPlus() = runMutableConstPlusTests()
    @Test fun testIntersect() = runMutableConstIntersectTests()

    @Test fun testIsEmpty() = runMutableConstIsEmptyTests()
    @Test fun testGetCountOf() = runMutableConstGetCountOfTests()

    @Test
    fun testIterator() {
        var set: MutableMultiSet<Int> = constMutableMultiSetOf()
        var iter = set.iterator()
        assertFalse(iter.hasNext())

        set = constMutableMultiSetOf(1, 2, 3, 4)
        iter = set.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set = constMutableMultiSetOf(1, 2, 3, 4, 1, 4, 5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set.add(5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set.remove(2)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 3, 4, 4, 5, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(0, 5, 7)
        val listSet: MutableMultiSet<IntList> = constMutableMultiSetOf(mutableList1, mutableList2)

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
        var set: MutableMultiSet<Int> = constMutableMultiSetOf()
        var expected = "[]"
        assertEquals(expected, set.toString())

        set = constMutableMultiSetOf(100, -200, 400, 800)
        expected = "[100, -200, 400, 800]"
        assertEquals(expected, set.toString())

        set = constMutableMultiSetOf(1, 1, 1, 1)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, set.toString())

        set = constMutableMultiSetOf(2, 4, 2, 1)
        expected = "[2, 2, 4, 1]"
        assertEquals(expected, set.toString())

        set.add(5)
        expected = "[2, 2, 4, 1, 5]"
        assertEquals(expected, set.toString())

        set.remove(2)
        expected = "[2, 4, 1, 5]"
        assertEquals(expected, set.toString())

        val list1 = listOf(1, 2, 3)
        val list2 = listOf(0, 5, 7)
        val listSet: MutableMultiSet<IntList> = constMutableMultiSetOf(list1, list2)

        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listSet.toString())
    }
}
