package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.classes.multiset.MultiSet
import xyz.lbres.kotlinutils.classes.multiset.MutableMultiSet
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MultiSetUtilsTest {
    @Test
    internal fun testMultiSetOf() {
        var set: MultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.multiSetOf()
        var expected: MultiSet<Int> = MultiSetImpl(listOf())
        assertEquals(expected, set)

        set = xyz.lbres.kotlinutils.classes.multiset.multiSetOf(1)
        expected = MultiSetImpl(listOf(1))
        assertEquals(expected, set)

        set = xyz.lbres.kotlinutils.classes.multiset.multiSetOf(1, 2, 3, 4)
        expected = MultiSetImpl(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = xyz.lbres.kotlinutils.classes.multiset.multiSetOf(1, 1, 1)
        expected = MultiSetImpl(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = xyz.lbres.kotlinutils.classes.multiset.multiSetOf("", "hello", "world")
        val stringExpected = MultiSetImpl(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet =
            xyz.lbres.kotlinutils.classes.multiset.multiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = MultiSetImpl(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)
    }

    @Test
    internal fun testMutableMultiSetOf() {
        var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        var expected: MutableMultiSet<Int> = MutableMultiSetImpl(listOf())
        assertEquals(expected, set)

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
        expected = MutableMultiSetImpl(listOf(1))
        assertEquals(expected, set)

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)
        expected = MutableMultiSetImpl(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1)
        expected = MutableMultiSetImpl(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf("", "hello", "world")
        val stringExpected = MutableMultiSetImpl(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(
            listOf(123),
            listOf(1, 4, 5, 6),
            listOf(99, 100, 97)
        )
        val listExpected = MutableMultiSetImpl(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)
    }

    @Test
    internal fun testEmptyMultiSet() {
        val set = xyz.lbres.kotlinutils.classes.multiset.emptyMultiSet<Int>()
        val expected = xyz.lbres.kotlinutils.classes.multiset.multiSetOf<Int>()
        assertEquals(expected, set)
    }

    @Test
    internal fun testMultiSet() {
        var set = MultiSet(0) { 1 }
        var expectedSize = 0
        var expected: MultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.emptyMultiSet()
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MultiSet(5) { 2 }
        expectedSize = 5
        expected = MultiSetImpl(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MultiSet(4) { 3 * it }
        expectedSize = 4
        expected = MultiSetImpl(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = MultiSet(8) { values[it] }
        expectedSize = 8
        expected = MultiSetImpl(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }

    @Test
    internal fun testMutableMultiSet() {
        var set = MutableMultiSet(0) { 5 }
        var expectedSize = 0
        var expected: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MutableMultiSet(5) { 2 }
        expectedSize = 5
        expected = MutableMultiSetImpl(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MutableMultiSet(4) { 3 * it }
        expectedSize = 4
        expected = MutableMultiSetImpl(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = MutableMultiSet(8) { values[it] }
        expectedSize = 8
        expected = MutableMultiSetImpl(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }
}
