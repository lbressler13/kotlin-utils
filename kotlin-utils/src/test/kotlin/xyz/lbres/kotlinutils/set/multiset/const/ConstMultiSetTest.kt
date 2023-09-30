package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.constimmutable.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetContainsAllTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetContainsTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetIteratorTests
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ConstMultiSetTest {
    @Test fun testConstructor() = runConstConstructorTests()
    @Test fun testEquals() = runConstEqualsTests()

    @Test fun testContains() = runMultiSetContainsTests({ ConstMultiSetImpl(it) }, { ConstMultiSetImpl(it) }, { ConstMultiSetImpl(it) })
    @Test fun testContainsAll() = runMultiSetContainsAllTests { ConstMultiSetImpl(it) }

    @Test fun testMinus() = runConstMinusTests()
    @Test fun testPlus() = runConstPlusTests()
    @Test fun testIntersect() = runConstIntersectTests()

    @Test fun testIsEmpty() = runConstIsEmptyTests()
    @Test fun testGetCountOf() = runConstGetCountOfTests()

    @Test
    fun testIterator() {
        runMultiSetIteratorTests(
            { ints -> ConstMultiSetImpl(ints) },
            { intLists -> ConstMultiSetImpl(intLists) }
        )
    }

    @Test
    fun testToString() {
        var set: ConstMultiSet<Int> = emptyConstMultiSet()
        var expected = "[]"
        assertEquals(expected, set.toString())

        set = constMultiSetOf(1, 1, 1, 1)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, set.toString())

        set = constMultiSetOf(2, 4, 2, 1)
        expected = "[2, 2, 4, 1]"
        assertEquals(expected, set.toString())

        val listSet = constMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 7))
        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listSet.toString())
    }
}
