package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.impl.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.StandardMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MutableConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MutableStandardMultiSet
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiSetUtilsTest {
    @Test
    fun testMultiSetOf() {
        var set: MultiSet<Int> = multiSetOf()
        var expected: MultiSet<Int> = StandardMultiSet(emptyList())
        assertEquals(expected, set)

        set = multiSetOf(1)
        expected = StandardMultiSet(listOf(1))
        assertEquals(expected, set)

        set = multiSetOf(1, 2, 3, 4)
        expected = StandardMultiSet(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = multiSetOf(1, 1, 1)
        expected = StandardMultiSet(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = multiSetOf("", "hello", "world")
        val stringExpected = StandardMultiSet(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = multiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = StandardMultiSet(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)

        val compListSet = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSetExpected = StandardMultiSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
        assertEquals(compListSetExpected, compListSet)
    }

    @Test
    fun testMutableMultiSetOf() {
        var set: MutableMultiSet<Int> = mutableMultiSetOf()
        var expected: MutableMultiSet<Int> = MutableStandardMultiSet(emptyList())
        assertEquals(expected, set)

        set = mutableMultiSetOf(1)
        expected = MutableStandardMultiSet(listOf(1))
        assertEquals(expected, set)

        set = mutableMultiSetOf(1, 2, 3, 4)
        expected = MutableStandardMultiSet(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = mutableMultiSetOf(1, 1, 1)
        expected = MutableStandardMultiSet(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = mutableMultiSetOf("", "hello", "world")
        val stringExpected = MutableStandardMultiSet(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = mutableMultiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = MutableStandardMultiSet(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)

        val compListSet = mutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSetExpected = MutableStandardMultiSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
        assertEquals(compListSetExpected, compListSet)
    }

    @Test
    fun testConstMultiSetOf() {
        var set: MultiSet<Int> = constMultiSetOf()
        var expected: MultiSet<Int> = ConstMultiSetImpl(emptyList())
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
        var set: MutableMultiSet<Int> = constMutableMultiSetOf()
        var expected: MutableMultiSet<Int> = MutableConstMultiSetImpl(emptyList())
        assertEquals(expected, set)

        set = constMutableMultiSetOf(1)
        expected = MutableConstMultiSetImpl(listOf(1))
        assertEquals(expected, set)

        set = constMutableMultiSetOf(1, 2, 3, 4)
        expected = MutableConstMultiSetImpl(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = constMutableMultiSetOf(1, 1, 1)
        expected = MutableConstMultiSetImpl(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = constMutableMultiSetOf("", "hello", "world")
        val stringExpected = MutableConstMultiSetImpl(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = constMutableMultiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = MutableConstMultiSetImpl(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)

        val compListSet = constMutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSetExpected = MutableConstMultiSetImpl(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
        assertEquals(compListSetExpected, compListSet)
    }

    @Test
    fun testEmptyMultiSet() {
        val set = emptyMultiSet<Int>()
        val expected = multiSetOf<Int>()
        assertEquals(expected, set)
    }

    @Test
    fun testMultiSet() {
        var set = MultiSet(0) { 1 }
        var expectedSize = 0
        var expected: MultiSet<Int> = emptyMultiSet()
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MultiSet(5) { 2 }
        expectedSize = 5
        expected = StandardMultiSet(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MultiSet(4) { 3 * it }
        expectedSize = 4
        expected = StandardMultiSet(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = MultiSet(8) { values[it] }
        expectedSize = 8
        expected = StandardMultiSet(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }

    @Test
    fun testMutableMultiSet() {
        var set = MutableMultiSet(0) { 5 }
        var expectedSize = 0
        var expected: MutableMultiSet<Int> = mutableMultiSetOf()
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MutableMultiSet(5) { 2 }
        expectedSize = 5
        expected = MutableStandardMultiSet(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MutableMultiSet(4) { 3 * it }
        expectedSize = 4
        expected = MutableStandardMultiSet(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = MutableMultiSet(8) { values[it] }
        expectedSize = 8
        expected = MutableStandardMultiSet(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }
}
