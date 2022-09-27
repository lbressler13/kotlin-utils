package xyz.lbres.kotlinutils.collection.int.ext

import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.assertEquals
import kotlin.test.Test

class IntCollectionExtTest {
    @Test
    internal fun testFilterNotZero() {
        var list: List<Int> = listOf()
        var expected: List<Int> = listOf()
        assertEquals(expected, list.filterNotZero())

        list = listOf(0, 0, 0)
        expected = listOf()
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
    internal fun testSum() {
        var list: IntList = emptyList()
        var expected = 0
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
    internal fun testProduct() {
        var list: IntList = emptyList()
        var expected = 0
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
