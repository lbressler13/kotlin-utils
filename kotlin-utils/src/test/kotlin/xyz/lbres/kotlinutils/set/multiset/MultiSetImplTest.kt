package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.immutable.* // ktlint-disable no-wildcard-imports no-unused-imports
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
    }
}
