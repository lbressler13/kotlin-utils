package xyz.lbres.kotlinutils.pair.comparable.ext

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class ComparablePairExtTest {
    @Test
    fun testMax() {
        // equal
        var intPair = Pair(0, 0)
        assertEquals(0, intPair.max())

        intPair = Pair(123456, 123456)
        assertEquals(123456, intPair.max())

        var bigDecimalPair = Pair(BigDecimal("14.56"), BigDecimal("14.56"))
        assertEquals(BigDecimal("14.56"), bigDecimalPair.max())

        var charPair = Pair('C', 'C')
        assertEquals('C', charPair.max())

        // not equal
        intPair = Pair(0, 5)
        assertEquals(5, intPair.max())
        intPair = Pair(5, 0)
        assertEquals(5, intPair.max())

        intPair = Pair(-2, -1)
        assertEquals(-1, intPair.max())
        intPair = Pair(-1, -2)
        assertEquals(-1, intPair.max())

        bigDecimalPair = Pair(BigDecimal("14.56"), BigDecimal("14.57"))
        assertEquals(BigDecimal("14.57"), bigDecimalPair.max())
        bigDecimalPair = Pair(BigDecimal("14.57"), BigDecimal("14.56"))
        assertEquals(BigDecimal("14.57"), bigDecimalPair.max())

        charPair = Pair('C', 'c')
        assertEquals('c', charPair.max())
        charPair = Pair('c', 'C')
        assertEquals('c', charPair.max())
    }

    @Test
    fun testMin() {
        // equal
        var intPair = Pair(0, 0)
        assertEquals(0, intPair.min())

        intPair = Pair(123456, 123456)
        assertEquals(123456, intPair.min())

        var bigDecimalPair = Pair(BigDecimal("14.56"), BigDecimal("14.56"))
        assertEquals(BigDecimal("14.56"), bigDecimalPair.min())

        var charPair = Pair('C', 'C')
        assertEquals('C', charPair.min())

        // not equal
        intPair = Pair(0, 5)
        assertEquals(0, intPair.min())
        intPair = Pair(5, 0)
        assertEquals(0, intPair.min())

        intPair = Pair(-2, -1)
        assertEquals(-2, intPair.min())
        intPair = Pair(-1, -2)
        assertEquals(-2, intPair.min())

        bigDecimalPair = Pair(BigDecimal("14.56"), BigDecimal("14.57"))
        assertEquals(BigDecimal("14.56"), bigDecimalPair.min())
        bigDecimalPair = Pair(BigDecimal("14.57"), BigDecimal("14.56"))
        assertEquals(BigDecimal("14.56"), bigDecimalPair.min())

        charPair = Pair('C', 'c')
        assertEquals('C', charPair.min())
        charPair = Pair('c', 'C')
        assertEquals('C', charPair.min())
    }
}
