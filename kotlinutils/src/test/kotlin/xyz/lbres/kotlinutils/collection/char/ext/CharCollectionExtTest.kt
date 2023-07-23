package xyz.lbres.kotlinutils.collection.char.ext

import kotlin.test.Test
import kotlin.test.assertEquals

class CharCollectionExtTest {
    private val zero = Char(0)
    private val one = Char(1)
    private val four = Char(4)
    private val five = Char(5)

    @Test
    fun testFilterNotZero() {
        var list: List<Char> = emptyList()
        var expected: List<Char> = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(zero, zero, zero)
        expected = emptyList()
        assertEquals(expected, list.filterNotZero())

        list = listOf(one, Char(2), zero, four, zero, zero, five)
        expected = listOf(one, Char(2), four, five)
        assertEquals(expected, list.filterNotZero())

        list = listOf(one, four, Char(1000), Char(19), five)
        expected = listOf(one, four, Char(1000), Char(19), five)
        assertEquals(expected, list.filterNotZero())
    }

    @Test
    fun testSum() {
        var list: List<Char> = emptyList()
        var expected = zero
        assertEquals(expected, list.sum())

        list = listOf(Char(33))
        expected = Char(33)
        assertEquals(expected, list.sum())

        list = listOf(Char(100), Char(45), Char(10), Char(67), Char(99))
        expected = Char(321)
        assertEquals(expected, list.sum())
    }

    @Test
    fun testProduct() {
        var list: List<Char> = emptyList()
        var expected = zero
        assertEquals(expected, list.product())

        list = listOf(zero)
        expected = zero
        assertEquals(expected, list.product())

        list = listOf(one)
        expected = one
        assertEquals(expected, list.product())

        list = listOf(four, four, zero)
        expected = zero
        assertEquals(expected, list.product())

        list = listOf(Char(15), Char(23), Char(4), Char(4))
        expected = Char(5520)
        assertEquals(expected, list.product())
    }
}
