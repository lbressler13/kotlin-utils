package xyz.lbres.kotlinutils.closedrange.longrange

/**
 * Create an LongRange consisting of a single num
 *
 * @param value [Long]: the number to create range of
 * @return [LongRange]: LongRange containing value and no other values
 */
fun rangeOfLong(value: Long): LongRange = value..value
