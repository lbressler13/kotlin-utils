package xyz.lbres.kotlinutils.set.multiset

import kotlin.test.Test
import kotlin.test.assertEquals

// very light tests, mostly tested in MultiSetGenericsTest
class MultiSetDefaultsTest {
    @Test
    fun testDefaultPlus() {
        val intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
        val intSet2 = multiSetOf(1, 2, 0)
        val intSet3 = TestMultiSet(listOf(1, 2, 2, 3, 3, 3))
        val intSet4 = TestMultiSet(listOf(1, 2, 0))
        val expectedInt = multiSetOf(1, 2, 2, 3, 3, 3, 1, 2, 0)

        assertEquals(expectedInt, MultiSet.defaultPlus(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.defaultPlus(intSet1, intSet4))
        assertEquals(expectedInt, MultiSet.defaultPlus(intSet3, intSet2))
        assertEquals(expectedInt, MultiSet.defaultPlus(intSet3, intSet4))

        val nullSet1 = mutableMultiSetOf(1, 2, 4, null)
        val nullSet2 = mutableMultiSetOf(1, 3, null, null)
        val expectedNull = multiSetOf(1, 2, 4, null, 1, 3, null, null)
        assertEquals(expectedNull, MultiSet.defaultPlus(nullSet1, nullSet2))
        assertEquals(expectedNull, MultiSet.defaultPlus(nullSet2, nullSet1))
    }

    @Test
    fun testDefaultMinus() {
        val intSet1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
        val intSet2 = multiSetOf(1, 1, 5, 6, 6, 7)
        val intSet3 = TestMultiSet(listOf(1, 1, 2, 3, 4, 5, 5))
        val intSet4 = TestMultiSet(listOf(1, 1, 5, 6, 6, 7))
        val expectedInt = multiSetOf(2, 3, 4, 5)

        assertEquals(expectedInt, MultiSet.defaultMinus(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.defaultMinus(intSet1, intSet4))
        assertEquals(expectedInt, MultiSet.defaultMinus(intSet3, intSet2))
        assertEquals(expectedInt, MultiSet.defaultMinus(intSet3, intSet4))

        val nullSet1 = mutableMultiSetOf(1, 1, 2, null)
        val nullSet2 = mutableMultiSetOf(1, 1, 5, 6, null, null)
        var expectedNull: MultiSet<Int?> = multiSetOf(2)
        assertEquals(expectedNull, MultiSet.defaultMinus(nullSet1, nullSet2))
        expectedNull = multiSetOf(5, 6, null)
        assertEquals(expectedNull, MultiSet.defaultMinus(nullSet2, nullSet1))
    }

    @Test
    fun testDefaultIntersect() {
        val intSet1 = multiSetOf(1, 2, 3, 5, 5, 5)
        val intSet2 = multiSetOf(4, 5, 5, 6, 7, 8)
        val intSet3 = TestMultiSet(listOf(1, 2, 3, 5, 5, 5))
        val intSet4 = TestMultiSet(listOf(4, 5, 5, 6, 7, 8))
        val expectedInt = multiSetOf(5, 5)

        assertEquals(expectedInt, MultiSet.defaultIntersect(intSet1, intSet2))
        assertEquals(expectedInt, MultiSet.defaultIntersect(intSet3, intSet2))
        assertEquals(expectedInt, MultiSet.defaultIntersect(intSet1, intSet4))
        assertEquals(expectedInt, MultiSet.defaultIntersect(intSet4, intSet3))

        val nullSet1 = mutableMultiSetOf(1, 1, 2, null)
        val nullSet2 = mutableMultiSetOf(1, 1, 5, 6, null, null)
        val nullExpected = multiSetOf(1, 1, null)
        assertEquals(nullExpected, MultiSet.defaultIntersect(nullSet1, nullSet2))
        assertEquals(nullExpected, MultiSet.defaultIntersect(nullSet2, nullSet1))
    }
}
