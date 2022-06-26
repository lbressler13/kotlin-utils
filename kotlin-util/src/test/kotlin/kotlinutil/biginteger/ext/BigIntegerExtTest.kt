package kotlinutil.biginteger.ext

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class BigIntegerExtTest {
    @Test
    internal fun testIsNegative() {
        var bi = BigInteger("0")
        assertFalse { bi.isNegative() }

        bi = BigInteger("1")
        assertFalse { bi.isNegative() }

        bi = BigInteger("100")
        assertFalse { bi.isNegative() }

        bi = BigInteger("-1")
        assertTrue { bi.isNegative() }

        bi = BigInteger("-100")
        assertTrue { bi.isNegative() }
    }

    @Test
    internal fun testIsZero() {
        var bi = BigInteger("0")
        assertTrue { bi.isZero() }

        bi = BigInteger("1")
        assertFalse { bi.isZero() }

        bi = BigInteger("-1")
        assertFalse { bi.isZero() }

        bi = BigInteger("100")
        assertFalse { bi.isZero() }

        bi = BigInteger("-100")
        assertFalse { bi.isZero() }
    }
}
