package xyz.lbres.kotlinutils.set.multiset.const

import kotlin.test.Test
import kotlin.test.assertEquals

class ConstMultiSetUtilsTest {
    @Test
    fun testConstMultiSetOf() {
        var set: ConstMultiSet<Int> = constMultiSetOf()
        var expected: ConstMultiSet<Int> = ConstMultiSetImpl(emptyList())
        assertEquals(expected, set)

        set = constMultiSetOf(1)
        expected = ConstMultiSetImpl(listOf(1))
        assertEquals(expected, set)

        set = constMultiSetOf(1, 2, 3, 4)
        expected = ConstMultiSetImpl(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = constMultiSetOf(1, 1, 1)
        expected = ConstMultiSetImpl(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = constMultiSetOf("", "hello", "world")
        val stringExpected = ConstMultiSetImpl(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = constMultiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = ConstMultiSetImpl(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)

        val compListSet = constMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSetExpected = ConstMultiSetImpl(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
        assertEquals(compListSetExpected, compListSet)
    }

    @Test
    fun testMutableConstMultiSetOf() {
        var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
        var expected: ConstMutableMultiSet<Int> = ConstMutableMultiSet(emptyList())
        assertEquals(expected, set)

        set = constMutableMultiSetOf(1)
        expected = ConstMutableMultiSet(listOf(1))
        assertEquals(expected, set)

        set = constMutableMultiSetOf(1, 2, 3, 4)
        expected = ConstMutableMultiSet(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = constMutableMultiSetOf(1, 1, 1)
        expected = ConstMutableMultiSet(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = constMutableMultiSetOf("", "hello", "world")
        val stringExpected = ConstMutableMultiSet(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = constMutableMultiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = ConstMutableMultiSet(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)

        val compListSet = constMutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSetExpected = ConstMutableMultiSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
        assertEquals(compListSetExpected, compListSet)
    }

    @Test
    fun testEmptyConstMultiSet() {
        val set = emptyConstMultiSet<Int>()
        val expected = constMultiSetOf<Int>()
        assertEquals(expected, set)
    }

    @Test
    fun testConstMultiSet() {
        var set = ConstMultiSet(0) { 1 }
        var expectedSize = 0
        var expected: ConstMultiSet<Int> = emptyConstMultiSet()
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = ConstMultiSet(5) { 2 }
        expectedSize = 5
        expected = ConstMultiSetImpl(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = ConstMultiSet(4) { 3 * it }
        expectedSize = 4
        expected = ConstMultiSetImpl(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = ConstMultiSet(8) { values[it] }
        expectedSize = 8
        expected = ConstMultiSetImpl(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }

    @Test
    fun testConstMutableMultiSet() {
        var set = ConstMutableMultiSet(0) { 5 }
        var expectedSize = 0
        var expected: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = ConstMutableMultiSet(5) { 2 }
        expectedSize = 5
        expected = ConstMutableMultiSet(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = ConstMutableMultiSet(4) { 3 * it }
        expectedSize = 4
        expected = ConstMutableMultiSet(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = ConstMutableMultiSet(8) { values[it] }
        expectedSize = 8
        expected = ConstMutableMultiSet(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }
}
