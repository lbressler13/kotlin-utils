package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.CompList
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import kotlin.test.Test
import kotlin.test.assertEquals

// very light tests, mostly tested in MultiSetGenericsTest
class MultiSetCompanionTest {
    @Test
    fun testGenericPlus() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        var expectedInt = emptyMultiSet<Int>()
        assertEquals(expectedInt, MultiSet.genericPlus(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericPlus(intSet2, intSet1))

        // non-empty
        intSet1 = multiSetOf(1)
        intSet2 = multiSetOf(1)
        expectedInt = multiSetOf(1, 1)
        assertEquals(expectedInt, MultiSet.genericPlus(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericPlus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
        intSet2 = multiSetOf(1, 2, 0)
        expectedInt = multiSetOf(1, 2, 2, 3, 3, 3, 1, 2, 0)
        assertEquals(expectedInt, MultiSet.genericPlus(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericPlus(intSet2, intSet1))

        val stringSet1 = multiSetOf("", "hello", "world", "goodbye")
        val stringSet2 = ConstMultiSetImpl(listOf("hi", "no", "bye"))
        val expectedString = multiSetOf("", "hello", "world", "goodbye", "hi", "no", "bye")
        assertEquals(expectedString, MultiSet.genericPlus(stringSet1, stringSet2))
        assertEquals(expectedString, MultiSet.genericPlus(stringSet2, stringSet1))

        val listSet1 = ConstMultiSetImpl(listOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3)))
        val listSet2 = ConstMultiSetImpl(multiSetOf(emptyList(), listOf(1, 2, 3)))
        val expectedList = multiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3), emptyList(), listOf(1, 2, 3))
        assertEquals(expectedList, MultiSet.genericPlus(listSet1, listSet2))
        assertEquals(expectedList, MultiSet.genericPlus(listSet2, listSet1))

        val compListSet1: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSet2: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        val expectedCompList: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"), listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        assertEquals(expectedCompList, MultiSet.genericPlus(compListSet1, compListSet2))
        assertEquals(expectedCompList, MultiSet.genericPlus(compListSet2, compListSet1))

        val nullSet1 = multiSetOf(1, 2, 4, null)
        val nullSet2 = multiSetOf(1, 3, null, null)
        val expectedNull = multiSetOf(1, 2, 4, null, 1, 3, null, null)
        assertEquals(expectedNull, MultiSet.genericPlus(nullSet1, nullSet2))
        assertEquals(expectedNull, MultiSet.genericPlus(nullSet2, nullSet1))

        // mutable
        val mutableSet1 = mutableMultiSetOf(1, 2)
        val mutableSet2 = mutableMultiSetOf(2, 3, 4)
        val expectedMutable = multiSetOf(1, 2, 2, 3, 4)
        assertEquals(expectedMutable, MultiSet.genericPlus(mutableSet1, mutableSet2))
        assertEquals(expectedMutable, MultiSet.genericPlus(mutableSet2, mutableSet1))

        val otherMutable = ConstMutableMultiSetImpl(listOf(2, 3, 4))
        assertEquals(expectedMutable, MultiSet.genericPlus(mutableSet1, otherMutable))
        assertEquals(expectedMutable, MultiSet.genericPlus(otherMutable, mutableSet1))
    }

    @Test
    fun testGenericMinus() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        var expectedInt = emptyMultiSet<Int>()
        assertEquals(expectedInt, MultiSet.genericMinus(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 1, 2, 3, 4, 4, 4)
        intSet2 = multiSetOf(1, 2, 2, 3, 4, 4)
        expectedInt = multiSetOf(1, 4)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet1, intSet2))
        expectedInt = multiSetOf(2)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
        intSet2 = multiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
        expectedInt = multiSetOf(2, 2, 6)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet1, intSet2))
        expectedInt = multiSetOf(1, 5, 5, 7)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet2, intSet1))

        intSet1 = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
        intSet2 = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
        expectedInt = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet1, intSet2))
        expectedInt = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet2, intSet1))

        val stringSet1 = multiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
        val stringSet2 = ConstMultiSetImpl(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
        var stringExpected = ConstMultiSetImpl(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
        assertEquals(stringExpected, MultiSet.genericMinus(stringSet1, stringSet2))
        stringExpected = ConstMultiSetImpl(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
        assertEquals(stringExpected, MultiSet.genericMinus(stringSet2, stringSet1))

        intSet1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
        intSet2 = multiSetOf(1, 1, 5, 6, 6, 7)
        expectedInt = multiSetOf(2, 3, 4, 5)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet1, intSet2))
        expectedInt = multiSetOf(6, 6, 7)
        assertEquals(expectedInt, MultiSet.genericMinus(intSet2, intSet1))

        val listSet1 = ConstMultiSetImpl(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)))
        val listSet2: MultiSet<List<Int>> = ConstMultiSetImpl(listOf(emptyList(), listOf(1, 2, 3)))
        var listExpected = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
        assertEquals(listExpected, MultiSet.genericMinus(listSet1, listSet2))
        listExpected = multiSetOf(emptyList())
        assertEquals(listExpected, MultiSet.genericMinus(listSet2, listSet1))

        val compListSet1: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
        val compListSet2: MultiSet<CompList> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
        var compListExpected: MultiSet<CompList> = multiSetOf(listOf("abc", "def"), listOf("abc", "def"))
        assertEquals(compListExpected, MultiSet.genericMinus(compListSet1, compListSet2))
        compListExpected = multiSetOf(listOf(1, 2, 3), emptyList())
        assertEquals(compListExpected, MultiSet.genericMinus(compListSet2, compListSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        var expectedNull: MultiSet<Int?> = multiSetOf(2)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        assertEquals(expectedNull, MultiSet.genericMinus(nullSet1, nullSet2))
        expectedNull = multiSetOf(5, 6, null)
        assertEquals(expectedNull, MultiSet.genericMinus(nullSet2, nullSet1))

        // mutable
        val mutableSet1 = mutableMultiSetOf(1, 2, 3, 3)
        val mutableSet2 = mutableMultiSetOf(2, 3, 4)
        val expectedMutable = multiSetOf(1, 3)
        assertEquals(expectedMutable, MultiSet.genericMinus(mutableSet1, mutableSet2))

        val otherMutable = ConstMutableMultiSetImpl(listOf(2, 3, 4))
        assertEquals(expectedMutable, MultiSet.genericMinus(mutableSet1, otherMutable))
    }

    @Test
    fun testGenericIntersect() {
        // empty
        var intSet1 = emptyMultiSet<Int>()
        var intSet2 = emptyMultiSet<Int>()
        var expectedInt = emptyMultiSet<Int>()
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet2, intSet1))

        intSet1 = emptyMultiSet()
        intSet2 = multiSetOf(1, 2, 3)
        expectedInt = emptyMultiSet()
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 3)
        intSet2 = multiSetOf(4, 5, 6, 7, 8)
        expectedInt = emptyMultiSet()
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet2, intSet1))

        var listSet1 = multiSetOf(listOf(1, 2, 3), listOf(4, 5))
        var listSet2 = multiSetOf(listOf(1, 2), listOf(3, 4, 5))
        var expectedList = emptyMultiSet<IntList>()
        assertEquals(expectedList, MultiSet.genericIntersect(listSet1, listSet2))
        assertEquals(expectedList, MultiSet.genericIntersect(listSet2, listSet1))

        intSet1 = multiSetOf(1, 2, 3)
        intSet2 = multiSetOf(1, 2, 3)
        expectedInt = multiSetOf(1, 2, 3)
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet2, intSet1))

        intSet1 = multiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
        intSet2 = multiSetOf(-1, 14, 3, 9, 9, 6)
        expectedInt = multiSetOf(-1, 6)
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.genericIntersect(intSet2, intSet1))

        listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
        listSet2 = multiSetOf(emptyList(), listOf(1, 2, 3))
        expectedList = multiSetOf(listOf(1, 2, 3))
        assertEquals(expectedList, MultiSet.genericIntersect(listSet1, listSet2))
        assertEquals(expectedList, MultiSet.genericIntersect(listSet2, listSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        val expectedNull = multiSetOf(1, 1, null)
        assertEquals(expectedNull, MultiSet.genericIntersect(nullSet1, nullSet2))
        assertEquals(expectedNull, MultiSet.genericIntersect(nullSet2, nullSet1))

        // mutable
        val mutableSet1 = mutableMultiSetOf(1, 2, 3)
        val mutableSet2 = mutableMultiSetOf(2, 3, 4, 2)
        val expectedMutable = multiSetOf(2, 3)
        assertEquals(expectedMutable, MultiSet.genericIntersect(mutableSet1, mutableSet2))
        assertEquals(expectedMutable, MultiSet.genericIntersect(mutableSet2, mutableSet1))

        val otherMutable = ConstMutableMultiSetImpl(listOf(2, 3, 4, 2))
        assertEquals(expectedMutable, MultiSet.genericIntersect(mutableSet1, otherMutable))
        assertEquals(expectedMutable, MultiSet.genericIntersect(otherMutable, mutableSet1))
    }
}
