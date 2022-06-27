package kotlinutils.biginteger

import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.Test

class BigIntegerUtilsTest {
    @Test
    internal fun testMax() {
        // zero
        var bi1 = BigInteger("0")

        var bi2 = BigInteger("0")
        var expected = BigInteger("0")
        assertEquals(expected, max(bi1, bi2))

        bi2 = BigInteger("1")
        expected = BigInteger("1")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        bi2 = BigInteger("-1")
        expected = BigInteger("0")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        // non-zero
        bi1 = BigInteger("18")
        bi2 = BigInteger("18")
        expected = BigInteger("18")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        bi1 = BigInteger("-18")
        bi2 = BigInteger("-18")
        expected = BigInteger("-18")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        bi1 = BigInteger("-5")
        bi2 = BigInteger("4")
        expected = BigInteger("4")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        bi1 = BigInteger("-4")
        bi2 = BigInteger("5")
        expected = BigInteger("5")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        bi1 = BigInteger("-4")
        bi2 = BigInteger("-5")
        expected = BigInteger("-4")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))

        bi1 = BigInteger("4")
        bi2 = BigInteger("5")
        expected = BigInteger("5")
        assertEquals(expected, max(bi1, bi2))
        assertEquals(expected, max(bi2, bi1))
    }

    @Test
    internal fun testMin() {
        // zero
        var bi1 = BigInteger("0")

        var bi2 = BigInteger("0")
        var expected = BigInteger("0")
        assertEquals(expected, min(bi1, bi2))

        bi2 = BigInteger("1")
        expected = BigInteger("0")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        bi2 = BigInteger("-1")
        expected = BigInteger("-1")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        // non-zero
        bi1 = BigInteger("18")
        bi2 = BigInteger("18")
        expected = BigInteger("18")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        bi1 = BigInteger("-18")
        bi2 = BigInteger("-18")
        expected = BigInteger("-18")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        bi1 = BigInteger("-5")
        bi2 = BigInteger("4")
        expected = BigInteger("-5")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        bi1 = BigInteger("-4")
        bi2 = BigInteger("5")
        expected = BigInteger("-4")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        bi1 = BigInteger("-4")
        bi2 = BigInteger("-5")
        expected = BigInteger("-5")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))

        bi1 = BigInteger("4")
        bi2 = BigInteger("5")
        expected = BigInteger("4")
        assertEquals(expected, min(bi1, bi2))
        assertEquals(expected, min(bi2, bi1))
    }
}
