package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import kotlin.test.Test
import kotlin.test.assertEquals

class LongCollectionExtTest {
    @Test
    fun testFilterNotZero() = testFilterNotZeroGeneric<Long, Collection<Long>>({ it }) { it.filterNotZero() }

    @Test
    fun testSum() {
        var list: List<Long> = emptyList()
        var expected = 0L
        assertEquals(expected, list.sum())

        list = listOf(33)
        expected = 33
        assertEquals(expected, list.sum())

        list = listOf(-33)
        expected = -33
        assertEquals(expected, list.sum())

        list = listOf(5, -5)
        expected = 0
        assertEquals(expected, list.sum())

        list = listOf(100, 45, -10, 67, 99)
        expected = 301
        assertEquals(expected, list.sum())

        list = listOf(-100, 45, -10, -67, 99)
        expected = -33
        assertEquals(expected, list.sum())
    }

    @Test
    fun testProduct() {
        var list: List<Long> = emptyList()
        var expected = 0L
        assertEquals(expected, list.product())

        list = listOf(0)
        expected = 0
        assertEquals(expected, list.product())

        list = listOf(1)
        expected = 1
        assertEquals(expected, list.product())

        list = listOf(-1)
        expected = -1
        assertEquals(expected, list.product())

        list = listOf(5, 5, -2, 0)
        expected = 0
        assertEquals(expected, list.product())

        list = listOf(-15, 23, 17, 4, 4, -2, 3)
        expected = 563040
        assertEquals(expected, list.product())

        list = listOf(-15, 23, 17, 4, 4, -2, -3)
        expected = -563040
        assertEquals(expected, list.product())
    }
}
