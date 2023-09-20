package xyz.lbres.kotlinutils.set.multiset.manager

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.constMutableMultiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ConstMultiSetManagerTest {
//    @Test fun testConstructor() = runConstructorTests()
//    @Test fun testEquals() = runEqualsTests()
//
//    @Test fun testContains() = runContainsTests()
//    @Test fun testContainsAll() = runContainsAllTests()
//
//    @Test fun testMinus() = runMinusTests()
//    @Test fun testPlus() = runPlusTests()
//    @Test fun testIntersect() = runIntersectTests()
//
    @Test fun testIsEmpty() = runIsEmptyTests()
    @Test fun testGetCountOf() = runGetCountOfTests()

    @Test
    fun testIterator() {
        var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
        var iter = manager.iterator()
        assertFalse(iter.hasNext())

        manager = ConstMultiSetManager(emptyList(), true)
        iter = manager.iterator()
        assertFalse(iter.hasNext())


        manager = ConstMultiSetManager(listOf(1, 2, 3, 4), false)
        iter = manager.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        manager = ConstMultiSetManager(listOf(1, 2, 3, 4, 1, 4, 5), true)
        iter = manager.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        manager.add(5)
        iter = manager.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        val list1 = mutableListOf(1, 2, 3)
        val list2 = mutableListOf(0, 5, 7)
        val listManager: ConstMultiSetManager<IntList> = ConstMultiSetManager(setOf(list1, list2), false)

        val listIter = listManager.iterator()
        val listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
        val listValues: MutableList<IntList> = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)
    }

    @Test
    fun testToString() {
        var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
        var expected = "[]"
        assertEquals(expected, manager.toString())

        manager = ConstMultiSetManager(emptyList(), true)
        expected = "[]"
        assertEquals(expected, manager.toString())

        manager = ConstMultiSetManager(listOf(100, -200, 400, 800), false)
        expected = "[100, -200, 400, 800]"
        assertEquals(expected, manager.toString())

        manager = ConstMultiSetManager(listOf(1, 1, 1, 1), false)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, manager.toString())

        manager = ConstMultiSetManager(listOf(2, 4, 2, 1), true)
        expected = "[2, 2, 4, 1]"
        assertEquals(expected, manager.toString())

        manager.add(5)
        expected = "[2, 2, 4, 1, 5]"
        assertEquals(expected, manager.toString())

        manager.remove(2)
        expected = "[2, 4, 1, 5]"
        assertEquals(expected, manager.toString())

        val list1 = listOf(1, 2, 3)
        val list2 = listOf(0, 5, 7)
        val listManager = ConstMultiSetManager(setOf(list1, list2), true)

        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listManager.toString())
    }

//    @Test
//    fun testToString() {
//        var set: ConstMultiSet<Int> = constMultiSetOf()
//        var expected = "[]"
//        assertEquals(expected, set.toString())
//
//        set = constMultiSetOf(100, -200, 400, 800)
//        expected = "[100, -200, 400, 800]"
//        assertEquals(expected, set.toString())
//
//        set = constMultiSetOf(1, 1, 1, 1)
//        expected = "[1, 1, 1, 1]"
//        assertEquals(expected, set.toString())
//
//        set = constMultiSetOf(2, 4, 2, 1)
//        expected = "[2, 2, 4, 1]"
//        assertEquals(expected, set.toString())
//
//        val list1 = listOf(1, 2, 3)
//        val list2 = listOf(0, 5, 7)
//        val listSet: ConstMultiSet<IntList> = constMultiSetOf(list1, list2)
//
//        expected = "[[1, 2, 3], [0, 5, 7]]"
//        assertEquals(expected, listSet.toString())
//    }
}
