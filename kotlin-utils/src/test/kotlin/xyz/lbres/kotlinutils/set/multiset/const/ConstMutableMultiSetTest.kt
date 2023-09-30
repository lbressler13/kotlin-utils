package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.constmutable.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ConstMutableMultiSetTest {
    @Test fun testConstructor() = runMutableConstConstructorTests()
    @Test fun testEquals() = runMutableConstEqualsTests()

    @Test fun testContains() = runMutableConstContainsTests()
    @Test fun testContainsAll() = runMutableConstContainsAllTests()

    @Test fun testClear() =  runMultiSetClearTests { ints -> ConstMutableMultiSet(ints) }
    @Test fun testAdd() = runMultiSetAddTests(
        { ints -> ConstMutableMultiSet(ints) },
        { stringLists -> ConstMutableMultiSet(stringLists) },
    )
    @Test fun testAddAll() = runMultiSetAddAllTests { ints -> ConstMutableMultiSet(ints) }
    @Test fun testRemove() = runMultiSetRemoveTests { ints -> ConstMutableMultiSet(ints) }
    @Test fun testRemoveAll() = runMultiSetRemoveAllTests { ints -> ConstMutableMultiSet(ints) }
    @Test fun testRetainAll() = runMultiSetClearTests { ints -> ConstMutableMultiSet(ints) }

    @Test fun testMinus() = runMutableConstMinusTests()
    @Test fun testPlus() = runMutableConstPlusTests()
    @Test fun testIntersect() = runMutableConstIntersectTests()

    @Test fun testIsEmpty() = runMutableConstIsEmptyTests()
    @Test fun testGetCountOf() = runMutableConstGetCountOfTests()

    @Test
    fun testIterator() {
        runMultiSetMutableIteratorTests(
            { ints -> ConstMutableMultiSet(ints) },
            { stringLists -> ConstMutableMultiSet(stringLists) },
        )
    }

    @Test
    fun testToString() {
        var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
        var expected = "[]"
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

        val listSet = constMutableMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 7))
        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listSet.toString())
    }
}
