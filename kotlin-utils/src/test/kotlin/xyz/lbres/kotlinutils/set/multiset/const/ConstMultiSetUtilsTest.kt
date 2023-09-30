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
        // TODO
    }

    @Test
    fun testConstMutableMultiSet() {
        // TODO
    }
}
