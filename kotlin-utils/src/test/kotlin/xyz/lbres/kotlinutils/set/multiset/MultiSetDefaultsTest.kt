package xyz.lbres.kotlinutils.set.multiset

import kotlin.test.Test
import kotlin.test.assertEquals

// very light tests, mostly tested in MultiSetGenericsTest
class MultiSetDefaultsTest {
    @Test
    fun testDefaultPlus() {
        val intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
        val intSet2 = multiSetOf(1, 2, 0)
        assertEquals(intSet1 + intSet2, MultiSet.defaultPlus(intSet1, intSet2))
        assertEquals(intSet1 + intSet2, MultiSet.defaultPlus(intSet2, intSet1))

        val nullSet1 = multiSetOf(1, 2, 4, null)
        val nullSet2 = multiSetOf(1, 3, null, null)
        assertEquals(nullSet1 + nullSet2, MultiSet.defaultPlus(nullSet1, nullSet2))
        assertEquals(nullSet1 + nullSet2, MultiSet.defaultPlus(nullSet2, nullSet1))
    }

    @Test
    fun testDefaultMinus() {
        val intSet1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
        val intSet2 = multiSetOf(1, 1, 5, 6, 6, 7)
        assertEquals(intSet1 - intSet2, MultiSet.defaultMinus(intSet1, intSet2))
        assertEquals(intSet2 - intSet1, MultiSet.defaultMinus(intSet2, intSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        assertEquals(nullSet1 - nullSet2, MultiSet.defaultMinus(nullSet1, nullSet2))
        assertEquals(nullSet2 - nullSet1, MultiSet.defaultMinus(nullSet2, nullSet1))
    }

    @Test
    fun testDefaultIntersect() {
        val intSet1 = multiSetOf(1, 2, 3, 5, 5, 5)
        val intSet2 = multiSetOf(4, 5, 5, 6, 7, 8)
        assertEquals(intSet1 intersect intSet2, MultiSet.defaultIntersect(intSet1, intSet2))
        assertEquals(intSet2 intersect intSet1, MultiSet.defaultIntersect(intSet2, intSet1))

        val nullSet1 = multiSetOf(1, 1, 2, null)
        val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
        assertEquals(nullSet1 intersect nullSet2, MultiSet.defaultIntersect(nullSet1, nullSet2))
        assertEquals(nullSet2 intersect nullSet1, MultiSet.defaultIntersect(nullSet2, nullSet1))
    }
}
