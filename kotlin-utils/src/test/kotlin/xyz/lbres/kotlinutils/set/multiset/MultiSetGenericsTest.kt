package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.testimpl.TestMultiSet
import xyz.lbres.kotlinutils.set.multiset.testimpl.TestMutableMultiSet
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiSetGenericsTest {
    @Test
    fun testGenericMultiSetPlus() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        var expectedInt = emptyMultiSet<Int>()
        assertEquals(expectedInt, genericMultiSetPlus(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetPlus(intSet2, intSet1))

        // non-empty
        intSet1 = multiSetOf(1)
        intSet2 = multiSetOf(1)
        expectedInt = multiSetOf(1, 1)
        assertEquals(expectedInt, genericMultiSetPlus(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetPlus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
        intSet2 = multiSetOf(1, 2, 0)
        expectedInt = multiSetOf(1, 2, 2, 3, 3, 3, 1, 2, 0)
        assertEquals(expectedInt, genericMultiSetPlus(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetPlus(intSet2, intSet1))

        val stringSet1 = multiSetOf("", "hello", "world", "goodbye")
        val stringSet2 = TestMultiSet(listOf("hi", "no", "bye"))
        val expectedString = multiSetOf("", "hello", "world", "goodbye", "hi", "no", "bye")
        assertEquals(expectedString, genericMultiSetPlus(stringSet1, stringSet2))
        assertEquals(expectedString, genericMultiSetPlus(stringSet2, stringSet1))

        val listSet1 = TestMultiSet(listOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3)))
        val listSet2 = TestMultiSet(multiSetOf(emptyList(), listOf(1, 2, 3)))
        val expectedList = multiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3), emptyList(), listOf(1, 2, 3))
        assertEquals(expectedList, genericMultiSetPlus(listSet1, listSet2))
        assertEquals(expectedList, genericMultiSetPlus(listSet2, listSet1))

        val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        val expectedCompList: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"), listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        assertEquals(expectedCompList, genericMultiSetPlus(compListSet1, compListSet2))
        assertEquals(expectedCompList, genericMultiSetPlus(compListSet2, compListSet1))

        val nullSet1 = multiSetOf(1, 2, 4, null)
        val nullSet2 = multiSetOf(1, 3, null, null)
        val expectedNull = multiSetOf(1, 2, 4, null, 1, 3, null, null)
        assertEquals(expectedNull, genericMultiSetPlus(nullSet1, nullSet2))
        assertEquals(expectedNull, genericMultiSetPlus(nullSet2, nullSet1))

        // mutable
        val mutableSet1 = mutableMultiSetOf(1, 2)
        val mutableSet2 = mutableMultiSetOf(2, 3, 4)
        val expectedMutable = multiSetOf(1, 2, 2, 3, 4)
        assertEquals(expectedMutable, genericMultiSetPlus(mutableSet1, mutableSet2))
        assertEquals(expectedMutable, genericMultiSetPlus(mutableSet2, mutableSet1))

        val otherMutable = TestMutableMultiSet(listOf(2, 3, 4))
        assertEquals(expectedMutable, genericMultiSetPlus(mutableSet1, otherMutable))
        assertEquals(expectedMutable, genericMultiSetPlus(otherMutable, mutableSet1))
    }

    @Test
    fun testGenericMultiSetMinus() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        var expectedInt = emptyMultiSet<Int>()
        assertEquals(expectedInt, genericMultiSetMinus(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 1, 2, 3, 4, 4, 4)
        intSet2 = multiSetOf(1, 2, 2, 3, 4, 4)
        expectedInt = multiSetOf(1, 4)
        assertEquals(expectedInt, genericMultiSetMinus(intSet1, intSet2))
        expectedInt = multiSetOf(2)
        assertEquals(expectedInt, genericMultiSetMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
        intSet2 = multiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
        expectedInt = multiSetOf(2, 2, 6)
        assertEquals(expectedInt, genericMultiSetMinus(intSet1, intSet2))
        expectedInt = multiSetOf(1, 5, 5, 7)
        assertEquals(expectedInt, genericMultiSetMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
        intSet2 = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
        expectedInt = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
        assertEquals(expectedInt, genericMultiSetMinus(intSet1, intSet2))
        expectedInt = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
        assertEquals(expectedInt, genericMultiSetMinus(intSet2, intSet1))

        val stringSet1 = multiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
        val stringSet2 = TestMultiSet(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
        var stringExpected = TestMultiSet(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
        assertEquals(stringExpected, genericMultiSetMinus(stringSet1, stringSet2))
        stringExpected = TestMultiSet(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
        assertEquals(stringExpected, genericMultiSetMinus(stringSet2, stringSet1))

        intSet1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
        intSet2 = multiSetOf(1, 1, 5, 6, 6, 7)
        expectedInt = multiSetOf(2, 3, 4, 5)
        assertEquals(expectedInt, genericMultiSetMinus(intSet1, intSet2))
        expectedInt = multiSetOf(6, 6, 7)
        assertEquals(expectedInt, genericMultiSetMinus(intSet2, intSet1))

        val listSet1 = TestMultiSet(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)))
        val listSet2: MultiSet<List<Int>> = TestMultiSet(listOf(emptyList(), listOf(1, 2, 3)))
        var listExpected = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
        assertEquals(listExpected, genericMultiSetMinus(listSet1, listSet2))
        listExpected = multiSetOf(emptyList())
        assertEquals(listExpected, genericMultiSetMinus(listSet2, listSet1))

        val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        var compListExpected: MultiSet<List<Comparable<*>>> = multiSetOf(listOf("abc", "def"), listOf("abc", "def"))
        assertEquals(compListExpected, genericMultiSetMinus(compListSet1, compListSet2))
        compListExpected = multiSetOf(listOf(1, 2, 3), emptyList())
        assertEquals(compListExpected, genericMultiSetMinus(compListSet2, compListSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        var expectedNull: MultiSet<Int?> = multiSetOf(2)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        assertEquals(expectedNull, genericMultiSetMinus(nullSet1, nullSet2))
        expectedNull = multiSetOf(5, 6, null)
        assertEquals(expectedNull, genericMultiSetMinus(nullSet2, nullSet1))

        // mutable
        val mutableSet1 = mutableMultiSetOf(1, 2, 3, 3)
        val mutableSet2 = mutableMultiSetOf(2, 3, 4)
        val expectedMutable = multiSetOf(1, 3)
        assertEquals(expectedMutable, genericMultiSetMinus(mutableSet1, mutableSet2))

        val otherMutable = TestMutableMultiSet(listOf(2, 3, 4))
        assertEquals(expectedMutable, genericMultiSetMinus(mutableSet1, otherMutable))
    }

    @Test
    fun testGenericMultiSetIntersect() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        var expectedInt = emptyMultiSet<Int>()
        assertEquals(expectedInt, genericMultiSetIntersect(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetIntersect(intSet2, intSet1))

        intSet1 = emptyMultiSet()
        intSet2 = multiSetOf(1, 2, 3)
        expectedInt = emptyMultiSet()
        assertEquals(expectedInt, genericMultiSetIntersect(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetIntersect(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 3)
        intSet2 = multiSetOf(4, 5, 6, 7, 8)
        expectedInt = emptyMultiSet()
        assertEquals(expectedInt, genericMultiSetIntersect(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetIntersect(intSet2, intSet1))

        var listSet1 = multiSetOf(listOf(1, 2, 3), listOf(4, 5))
        var listSet2 = multiSetOf(listOf(1, 2), listOf(3, 4, 5))
        var expectedList = emptyMultiSet<IntList>()
        assertEquals(expectedList, genericMultiSetIntersect(listSet1, listSet2))
        assertEquals(expectedList, genericMultiSetIntersect(listSet2, listSet1))

        intSet1 = multiSetOf(1, 2, 3)
        intSet2 = multiSetOf(1, 2, 3)
        expectedInt = multiSetOf(1, 2, 3)
        assertEquals(expectedInt, genericMultiSetIntersect(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetIntersect(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
        intSet2 = multiSetOf(-1, 14, 3, 9, 9, 6)
        expectedInt = multiSetOf(-1, 6)
        assertEquals(expectedInt, genericMultiSetIntersect(intSet1, intSet2))
        assertEquals(expectedInt, genericMultiSetIntersect(intSet2, intSet1))

        listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
        listSet2 = multiSetOf(listOf(), listOf(1, 2, 3))
        expectedList = multiSetOf(listOf(1, 2, 3))
        assertEquals(expectedList, genericMultiSetIntersect(listSet1, listSet2))
        assertEquals(expectedList, genericMultiSetIntersect(listSet2, listSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        val expectedNull = multiSetOf(1, 1, null)
        assertEquals(expectedNull, genericMultiSetIntersect(nullSet1, nullSet2))
        assertEquals(expectedNull, genericMultiSetIntersect(nullSet2, nullSet1))

        // mutable
        val mutableSet1 = mutableMultiSetOf(1, 2, 3)
        val mutableSet2 = mutableMultiSetOf(2, 3, 4, 2)
        val expectedMutable = multiSetOf(2, 3)
        assertEquals(expectedMutable, genericMultiSetIntersect(mutableSet1, mutableSet2))
        assertEquals(expectedMutable, genericMultiSetIntersect(mutableSet2, mutableSet1))

        val otherMutable = TestMutableMultiSet(listOf(2, 3, 4, 2))
        assertEquals(expectedMutable, genericMultiSetIntersect(mutableSet1, otherMutable))
        assertEquals(expectedMutable, genericMultiSetIntersect(otherMutable, mutableSet1))
    }
}
