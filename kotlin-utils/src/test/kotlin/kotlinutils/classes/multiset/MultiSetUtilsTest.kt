package kotlinutils.classes.multiset

import kotlin.test.Test
import kotlin.test.assertEquals

class MultiSetUtilsTest {
    @Test
    fun testMultiSetOf() {
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
}
