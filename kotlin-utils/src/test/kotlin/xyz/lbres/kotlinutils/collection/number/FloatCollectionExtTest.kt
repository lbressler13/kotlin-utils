package xyz.lbres.kotlinutils.collection.number

import kotlin.test.Test
import kotlin.test.assertEquals

// TODO duplicate to double after decimals are added
class FloatCollectionExtTest {
    @Test
    fun testFilterNotZero() {
        var list: List<Float> = emptyList()
        var expected: List<Float> = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(0f, -0f, 0f)
        expected = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(1f, 0.2f, 0f, 4f, 0f, 0f, 5.63f)
        expected = listOf(1f, 0.2f, 4f, 5.63f)
        assertEquals(expected, list.filterNotZero())

        list = listOf(-1f, 1f, 0f, -0f)
        expected = listOf(-1f, 1f)
        assertEquals(expected, list.filterNotZero())

        list = listOf(1f, 4f, 1000f, 19f, 5f)
        expected = listOf(1f, 4f, 1000f, 19f, 5f)
        assertEquals(expected, list.filterNotZero())
    }

    @Test
    fun testSum() {
        var list: List<Float> = emptyList()
        var expected = 0f
        assertEquals(expected, list.sum())

        list = listOf(33f)
        expected = 33f
        assertEquals(expected, list.sum())

        list = listOf(-33.3f)
        expected = -33.3f
        assertEquals(expected, list.sum())

        list = listOf(5f, -5f)
        expected = 0f
        assertEquals(expected, list.sum())

        list = listOf(100.2f, 0.45f, -10f, 67f, 9.983f)
        expected = 167.633f
        assertEquals(expected, list.sum())

        list = listOf(-100.2f, -0.45f, 10f, -67f, -9.983f)
        expected = -167.633f
        assertEquals(expected, list.sum())
        assertEquals(expected, list.sum())
    }

    @Test
    fun testProduct() {
        var list: List<Float> = emptyList()
        var expected = 0f
        assertEquals(expected, list.product())

        list = listOf(0f)
        expected = 0f
        assertEquals(expected, list.product())

        list = listOf(-0f)
        expected = -0f
        assertEquals(expected, list.product())

        list = listOf(1f)
        expected = 1f
        assertEquals(expected, list.product())

        list = listOf(-1f)
        expected = -1f
        assertEquals(expected, list.product())

        list = listOf(5.3f, 5.3f, -0.6f, 0f)
        expected = -0f
        assertEquals(expected, list.product())

        list = listOf(-5.3f, 5.3f, -0.6f, 0f)
        expected = 0f
        assertEquals(expected, list.product())

        list = listOf(-10.5f, 23f, 17.8f, 2.4f, 2.4f, -13.5f, 3f)
        expected = 1002800.8f
        assertEquals(expected, list.product())

        list = listOf(10.5f, 23f, 17.8f, 2.4f, 2.4f, -13.5f, 3f)
        expected = -1002800.8f
        assertEquals(expected, list.product())
    }
}
