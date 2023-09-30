package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.const.constmutable.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ConstMutableMultiSetTest {
    @Test fun testConstructor() = runMutableConstConstructorTests()
    @Test fun testEquals() = runMutableConstEqualsTests()

    @Test fun testContains() = runMultiSetMutableContainsTests({ ConstMutableMultiSet(it) }, { ConstMutableMultiSet(it) }, { ConstMutableMultiSet(it) })
    @Test fun testContainsAll() = runMultiSetMutableContainsAllTests { ConstMutableMultiSet(it) }

    @Test fun testClear() =  runMultiSetClearTests { ConstMutableMultiSet(it) }
    @Test fun testAdd() = runMultiSetAddTests({ ConstMutableMultiSet(it) }, { ConstMutableMultiSet(it) })
    @Test fun testAddAll() = runMultiSetAddAllTests { ConstMutableMultiSet(it) }
    @Test fun testRemove() = runMultiSetRemoveTests { ConstMutableMultiSet(it) }
    @Test fun testRemoveAll() = runMultiSetRemoveAllTests { ConstMutableMultiSet(it) }
    @Test fun testRetainAll() = runMultiSetClearTests { ConstMutableMultiSet(it) }

    @Test fun testMinus() = runMutableConstMinusTests()
    @Test fun testPlus() = runMutableConstPlusTests()
    @Test fun testIntersect() = runMutableConstIntersectTests()

    @Test fun testIsEmpty() = runMultiSetMutableIsEmptyTests({ MutableMultiSetImpl(it) }, { MutableMultiSetImpl(it) })
    @Test fun testGetCountOf() = runMultiSetMutableGetCountOfTests({ MutableMultiSetImpl(it) }, { MutableMultiSetImpl(it) })

    @Test fun testIterator() = runMultiSetMutableIteratorTests({ ConstMutableMultiSet(it) }, { ConstMutableMultiSet(it) })

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
