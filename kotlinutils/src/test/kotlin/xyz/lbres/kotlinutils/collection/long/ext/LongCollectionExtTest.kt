package xyz.lbres.kotlinutils.collection.long.ext

import kotlin.test.Test
import kotlin.test.assertEquals

class LongCollectionExtTest {
    @Test
    fun testFilterNotZero() {
        var list: List<Long> = emptyList()
        var expected: List<Long> = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(0, 0, 0)
        expected = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(1, 2, 0, 4, 0, 0, 5)
        expected = listOf(1, 2, 4, 5)
        assertEquals(expected, list.filterNotZero())

        list = listOf(-1, 1, 0)
        expected = listOf(-1, 1)
        assertEquals(expected, list.filterNotZero())

        list = listOf(1, 4, 1000, 19, 5)
        expected = listOf(1, 4, 1000, 19, 5)
        assertEquals(expected, list.filterNotZero())
    }

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
