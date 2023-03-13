package xyz.lbres.kotlinutils.set.multiset

import kotlin.test.Test
import kotlin.test.assertEquals

class MultiSetGenericsTest {
    @Test
    fun testGenericPlus() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        assertEquals(intSet1 + intSet2, genericPlus(intSet1, intSet2))
        assertEquals(intSet1 + intSet2, genericPlus(intSet2, intSet1))

        // non-empty
        intSet1 = multiSetOf(1)
        intSet2 = multiSetOf(1)
        assertEquals(intSet1 + intSet2, genericPlus(intSet1, intSet2))
        assertEquals(intSet1 + intSet2, genericPlus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
        intSet2 = multiSetOf(1, 2, 0)
        assertEquals(intSet1 + intSet2, genericPlus(intSet1, intSet2))
        assertEquals(intSet1 + intSet2, genericPlus(intSet2, intSet1))

        val stringSet1 = multiSetOf("", "hello", "world", "goodbye")
        val stringSet2 = multiSetOf("hi", "no", "bye")
        assertEquals(stringSet1 + stringSet2, genericPlus(stringSet1, stringSet2))
        assertEquals(stringSet1 + stringSet2, genericPlus(stringSet2, stringSet1))

        val listSet1 = multiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
        val listSet2 = multiSetOf(listOf(), listOf(1, 2, 3))
        assertEquals(listSet1 + listSet2, genericPlus(listSet1, listSet2))
        assertEquals(listSet1 + listSet2, genericPlus(listSet2, listSet1))

        val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf())
        assertEquals(compListSet1 + compListSet2, genericPlus(compListSet1, compListSet2))
        assertEquals(compListSet1 + compListSet2, genericPlus(compListSet2, compListSet1))

        val nullSet1 = multiSetOf(1, 2, 4, null)
        val nullSet2 = multiSetOf(1, 3, null, null)
        assertEquals(nullSet1 + nullSet2, genericPlus(nullSet1, nullSet2))
        assertEquals(nullSet1 + nullSet2, genericPlus(nullSet2, nullSet1))
    }

    @Test
    fun testGenericMinus() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        assertEquals(intSet1 - intSet2, genericMinus(intSet1, intSet2))
        assertEquals(intSet2 - intSet1, genericMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 1, 2, 3, 4, 4, 4)
        intSet2 = multiSetOf(1, 2, 2, 3, 4, 4)
        assertEquals(intSet1 - intSet2, genericMinus(intSet1, intSet2))
        assertEquals(intSet2 - intSet1, genericMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
        intSet2 = multiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
        assertEquals(intSet1 - intSet2, genericMinus(intSet1, intSet2))
        assertEquals(intSet2 - intSet1, genericMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
        intSet2 = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
        assertEquals(intSet1 - intSet2, genericMinus(intSet1, intSet2))
        assertEquals(intSet2 - intSet1, genericMinus(intSet2, intSet1))

        val stringSet1 = multiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
        val stringSet2 = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
        assertEquals(stringSet1 - stringSet2, genericMinus(stringSet1, stringSet2))
        assertEquals(stringSet2 - stringSet1, genericMinus(stringSet2, stringSet1))

        intSet1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
        intSet2 = multiSetOf(1, 1, 5, 6, 6, 7)
        assertEquals(intSet1 - intSet2, genericMinus(intSet1, intSet2))
        assertEquals(intSet2 - intSet1, genericMinus(intSet2, intSet1))

        val listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
        val listSet2: MultiSet<List<Int>> = multiSetOf(listOf(), listOf(1, 2, 3))
        assertEquals(listSet1 - listSet2, genericMinus(listSet1, listSet2))
        assertEquals(listSet2 - listSet1, genericMinus(listSet2, listSet1))

        val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf())
        assertEquals(compListSet1 - compListSet2, genericMinus(compListSet1, compListSet2))
        assertEquals(compListSet2 - compListSet1, genericMinus(compListSet2, compListSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        assertEquals(nullSet1 - nullSet2, genericMinus(nullSet1, nullSet2))
        assertEquals(nullSet2 - nullSet1, genericMinus(nullSet2, nullSet1))
    }

    @Test
    fun testGenericIntersect() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        assertEquals(intSet1 intersect intSet2, genericIntersect(intSet1, intSet2))
        assertEquals(intSet2 intersect intSet1, genericIntersect(intSet2, intSet1))

        intSet1 = emptyMultiSet()
        intSet2 = multiSetOf(1, 2, 3)
        assertEquals(intSet1 intersect intSet2, genericIntersect(intSet1, intSet2))
        assertEquals(intSet2 intersect intSet1, genericIntersect(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 3)
        intSet2 = multiSetOf(4, 5, 6, 7, 8)
        assertEquals(intSet1 intersect intSet2, genericIntersect(intSet1, intSet2))
        assertEquals(intSet2 intersect intSet1, genericIntersect(intSet2, intSet1))

        var listSet1 = multiSetOf(listOf(1, 2, 3), listOf(4, 5))
        var listSet2 = multiSetOf(listOf(1, 2), listOf(3, 4, 5))
        assertEquals(listSet1 intersect listSet2, genericIntersect(listSet1, listSet2))
        assertEquals(listSet2 intersect listSet1, genericIntersect(listSet2, listSet1))

        intSet1 = multiSetOf(1, 2, 3)
        intSet2 = multiSetOf(1, 2, 3)
        assertEquals(intSet1 intersect intSet2, genericIntersect(intSet1, intSet2))
        assertEquals(intSet2 intersect intSet1, genericIntersect(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
        intSet2 = multiSetOf(-1, 14, 3, 9, 9, 6)
        assertEquals(intSet1 intersect intSet2, genericIntersect(intSet1, intSet2))
        assertEquals(intSet2 intersect intSet1, genericIntersect(intSet2, intSet1))

        listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
        listSet2 = multiSetOf(listOf(), listOf(1, 2, 3))
        assertEquals(listSet1 intersect listSet2, genericIntersect(listSet1, listSet2))
        assertEquals(listSet2 intersect listSet1, genericIntersect(listSet2, listSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        assertEquals(nullSet1 intersect nullSet2, genericIntersect(nullSet1, nullSet2))
        assertEquals(nullSet2 intersect nullSet1, genericIntersect(nullSet2, nullSet1))
    }
}
