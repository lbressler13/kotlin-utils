package kotlinutils.biginteger

import java.math.BigInteger

/**
 * Comparison to find minimum of 2 BigIntegers
 *
 * @param val1 [BigInteger]
 * @param val2 [BigInteger]
 * @return [BigInteger]: the smaller of val1 and val2
 */
fun min(val1: BigInteger, val2: BigInteger): BigInteger = if (val1 < val2) val1 else val2

/**
 * Comparison to find maximum of 2 BigIntegers
 *
 * @param val1 [BigInteger]
 * @param val2 [BigInteger]
 * @return [BigInteger]: the larger of val1 and val2
 */
fun max(val1: BigInteger, val2: BigInteger): BigInteger = if (val2 < val1) val1 else val2
