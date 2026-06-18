package xyz.lbres.kotlinutils.collection.number

import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleCollectionExtTest {
    @Test
    fun testFilterNotZero() {
        var list: List<Double> = emptyList()
        var expected: List<Double> = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(0.0, -0.0, 0.0)
        expected = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(1.0, 0.2, 0.0, 4.0, 0.0, 0.0, 5.63)
        expected = listOf(1.0, 0.2, 4.0, 5.63)
        assertEquals(expected, list.filterNotZero())

        list = listOf(-1.0, 1.0, 0.0, -0.0)
        expected = listOf(-1.0, 1.0)
        assertEquals(expected, list.filterNotZero())

        list = listOf(1.0, 4.0, 1000.0, 19.0, 5.0)
        expected = listOf(1.0, 4.0, 1000.0, 19.0, 5.0)
        assertEquals(expected, list.filterNotZero())
    }

    @Test
    fun testSum() {
        var list: List<Double> = emptyList()
        var expected = 0.0
        assertEquals(expected, list.sum())

        list = listOf(33.0)
        expected = 33.0
        assertEquals(expected, list.sum())

        list = listOf(-33.3)
        expected = -33.3
        assertEquals(expected, list.sum())

        list = listOf(5.0, -5.0)
        expected = 0.0
        assertEquals(expected, list.sum())

        list = listOf(100.2, 0.45, -10.0, 67.0, 9.983)
        expected = 167.633
        assertEquals(expected, list.sum())

        list = listOf(-100.2, -0.45, 10.0, -67.0, -9.983)
        expected = -167.633
        assertEquals(expected, list.sum())
        assertEquals(expected, list.sum())
    }

    @Test
    fun testProduct() {
        var list: List<Double> = emptyList()
        var expected = 0.0
        assertEquals(expected, list.product())

        list = listOf(0.0)
        expected = 0.0
        assertEquals(expected, list.product())

        list = listOf(-0.0)
        expected = -0.0
        assertEquals(expected, list.product())

        list = listOf(1.0)
        expected = 1.0
        assertEquals(expected, list.product())

        list = listOf(-1.0)
        expected = -1.0
        assertEquals(expected, list.product())

        list = listOf(5.3, 5.3, -0.6, 0.0)
        expected = -0.0
        assertEquals(expected, list.product())

        list = listOf(-5.3, 5.3, -0.6, 0.0)
        expected = 0.0
        assertEquals(expected, list.product())

        list = listOf(-10.5, 23.0, 17.8, 2.4, 2.4, -13.5, 3.0)
        expected = 1002800.736
        assertEquals(expected, list.product())

        list = listOf(10.5, 23.0, 17.8, 2.4, 2.4, -13.5, 3.0)
        expected = -1002800.736
        assertEquals(expected, list.product())
    }
}
