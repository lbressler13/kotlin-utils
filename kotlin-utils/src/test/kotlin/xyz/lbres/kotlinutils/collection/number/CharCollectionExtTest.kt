package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import kotlin.test.Test
import kotlin.test.assertEquals

typealias CharColl = Collection<Char>

class CharCollectionExtTest {
    private val zero = Char(0)
    private val one = Char(1)
    private val four = Char(4)
    private val five = Char(5)

    @Test
    fun testFilterNotZero() = testFilterNotZeroGeneric<Char, CharColl>({ it }) { it.filterNotZero() }

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
