package kotlinutils.classes.multiset

import kotlin.test.Test
import kotlin.test.assertEquals

internal class MultiSetUtilsTest {
    @Test
    internal fun testMultiSetOf() {
        var set: MultiSet<Int> = multiSetOf()
        var expected: MultiSet<Int> = MultiSet(listOf())
        assertEquals(expected, set)

        set = multiSetOf(1)
        expected = MultiSet(listOf(1))
        assertEquals(expected, set)

        set = multiSetOf(1, 2, 3, 4)
        expected = MultiSet(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = multiSetOf(1, 1, 1)
        expected = MultiSet(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = multiSetOf("", "hello", "world")
        val stringExpected = MultiSet(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = multiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = MultiSet(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)
    }

    @Test
    internal fun testMutableMultiSetOf() {
        var set: MutableMultiSet<Int> = mutableMultiSetOf()
        var expected: MutableMultiSet<Int> = MutableMultiSet(listOf())
        assertEquals(expected, set)

        set = mutableMultiSetOf(1)
        expected = MutableMultiSet(listOf(1))
        assertEquals(expected, set)

        set = mutableMultiSetOf(1, 2, 3, 4)
        expected = MutableMultiSet(listOf(1, 2, 3, 4))
        assertEquals(expected, set)

        set = mutableMultiSetOf(1, 1, 1)
        expected = MutableMultiSet(listOf(1, 1, 1))
        assertEquals(expected, set)

        val stringSet = mutableMultiSetOf("", "hello", "world")
        val stringExpected = MutableMultiSet(listOf("", "hello", "world"))
        assertEquals(stringExpected, stringSet)

        val listSet = mutableMultiSetOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97))
        val listExpected = MutableMultiSet(listOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
        assertEquals(listExpected, listSet)
    }

    @Test
    fun testEmptyMultiSet() {
        val set = emptyMultiSet<Int>()
        val expected = multiSetOf<Int>()
        assertEquals(expected, set)
    }
}
