package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test
import kotlin.test.assertEquals

class ConstMultiSetTest {
    private fun <T> createSet(): (Collection<T>) -> ConstMultiSet<T> = { ConstMultiSetImpl(it) }

    @Test fun testConstructor() = runConstConstructorTests()
    @Test fun testEquals() = runConstEqualsTests()

    @Test fun testContains() = runMultiSetContainsTests(createSet(), createSet(), createSet())
    @Test fun testContainsAll() = runMultiSetContainsAllTests(createSet())

    @Test
    fun testMinus() {
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }
    }

    @Test fun testIsEmpty() = runMultiSetIsEmptyTests(createSet(), createSet())
    @Test fun testGetCountOf() = runMultiSetGetCountOfTests(createSet(), createSet())

    @Test fun testIterator() = runMultiSetIteratorTests(createSet(), createSet())

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
