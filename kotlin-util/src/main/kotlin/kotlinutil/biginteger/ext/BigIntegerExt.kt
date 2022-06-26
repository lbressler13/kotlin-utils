package kotlinutil.biginteger.ext

import java.math.BigInteger

// unary checks
fun BigInteger.isNegative(): Boolean = this < BigInteger.ZERO
fun BigInteger.isZero(): Boolean = equals(BigInteger.ZERO)

// comparisons
fun min(val1: BigInteger, val2: BigInteger): BigInteger = if (val1 < val2) val1 else val2
fun max(val1: BigInteger, val2: BigInteger): BigInteger = if (val2 < val1) val1 else val2
