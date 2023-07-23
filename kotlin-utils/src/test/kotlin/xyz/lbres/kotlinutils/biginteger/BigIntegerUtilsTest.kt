package xyz.lbres.kotlinutils.biginteger

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class BigIntegerUtilsTest {
    @Test
    fun testGetGcd() {
        val zero = BigInteger.ZERO
        val one = BigInteger.ONE

        // zero
        var num1 = zero

        var num2 = zero
        var expected = one
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num2 = one
        expected = one
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num2 = BigInteger("4")
        expected = BigInteger("4")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num2 = BigInteger("-4")
        expected = BigInteger("4")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        // one
        num1 = one
        expected = one

        num2 = one
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num2 = -one
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num2 = BigInteger("103")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num2 = BigInteger("-103")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = -one
        num2 = -one
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        // equal
        num1 = BigInteger("17")
        num2 = BigInteger("17")
        expected = BigInteger("17")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("17")
        num2 = BigInteger("-17")
        expected = BigInteger("17")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("-81")
        num2 = BigInteger("-81")
        expected = BigInteger("81")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        // co-prime
        expected = one

        num1 = BigInteger("17")
        num2 = BigInteger("19")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("-17")
        num2 = BigInteger("19")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("-2")
        num2 = BigInteger("-3")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("14")
        num2 = BigInteger("81")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("-15")
        num2 = BigInteger("1024")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        // exact divisor
        num1 = BigInteger.TWO
        num2 = BigInteger("4")
        expected = BigInteger.TWO
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("-1002")
        num2 = BigInteger("3")
        expected = BigInteger("3")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("1002")
        num2 = BigInteger("-3")
        expected = BigInteger("3")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("1002")
        num2 = BigInteger("334")
        expected = BigInteger("334")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        // common factor
        num1 = BigInteger("6")
        num2 = BigInteger("4")
        expected = BigInteger.TWO
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("15")
        num2 = BigInteger("-25")
        expected = BigInteger("5")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("120")
        num2 = BigInteger("100")
        expected = BigInteger("20")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("-120")
        num2 = BigInteger("-100")
        expected = BigInteger("20")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))

        num1 = BigInteger("34")
        num2 = BigInteger("51")
        expected = BigInteger("17")
        assertEquals(expected, getGCD(num1, num2))
        assertEquals(expected, getGCD(num2, num1))
    }

    @Test
    fun testListGCD() {
        val one = BigInteger.ONE

        // empty list
        var l: List<BigInteger> = emptyList()
        var expected = one
        assertEquals(expected, getListGCD(l))

        // single value
        l = listOf(BigInteger.ZERO)
        expected = one
        assertEquals(expected, getListGCD(l))

        l = listOf(one)
        expected = one
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("17"))
        expected = BigInteger("17")
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("-17"))
        expected = BigInteger("17")
        assertEquals(expected, getListGCD(l))

        // 2 values
        l = listOf(BigInteger.TWO, BigInteger("-2"))
        expected = BigInteger.TWO
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("11"), BigInteger("23"))
        expected = one
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("22"), BigInteger("121"))
        expected = BigInteger("11")
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("96"), BigInteger("180"))
        expected = BigInteger("12")
        assertEquals(expected, getListGCD(l))

        // multiple values
        l = listOf(one, one, BigInteger.TEN)
        expected = one
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("21"), BigInteger("3"), BigInteger("7"), BigInteger("14"))
        expected = one
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("63"), BigInteger("27"), BigInteger("102"))
        expected = BigInteger("3")
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("96"), BigInteger("180"), BigInteger("372"))
        expected = BigInteger("12")
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("-96"), BigInteger("180"), BigInteger("-372"))
        expected = BigInteger("12")
        assertEquals(expected, getListGCD(l))

        l = listOf(BigInteger("-96"), BigInteger("-180"), BigInteger("-372"))
        expected = BigInteger("12")
        assertEquals(expected, getListGCD(l))
        assertEquals(expected, getListGCD(l))

        l = listOf(
            BigInteger("12"),
            BigInteger("-18"),
            BigInteger("11111000"),
            BigInteger("4444444"),
            BigInteger("100"),
            BigInteger("12345678")
        )
        expected = BigInteger.TWO
        assertEquals(expected, getListGCD(l))

        l = listOf(
            BigInteger("12"),
            BigInteger("-18"),
            BigInteger("11111000"),
            BigInteger("4444444"),
            BigInteger("81"),
            BigInteger("12345678")
        )
        expected = one
        assertEquals(expected, getListGCD(l))

        l = listOf(
            BigInteger("1000"),
            BigInteger("-180"),
            BigInteger("11111000"),
            BigInteger("44444440"),
            BigInteger("80"),
            BigInteger("123456785"),
            BigInteger("-275"),
            BigInteger("45454545"),
            BigInteger.ZERO,
            BigInteger("98765")
        )
        expected = BigInteger("5")
        assertEquals(expected, getListGCD(l))
    }
}
