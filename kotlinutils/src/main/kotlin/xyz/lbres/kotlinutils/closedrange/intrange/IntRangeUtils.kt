package xyz.lbres.kotlinutils.closedrange.intrange

/**
 * Create an IntRange consisting of a single num
 *
 * @param value [Int]: the number to create range of
 * @return [IntRange]: IntRange containing value and no other values
 */
fun rangeOfInt(value: Int): IntRange = value..value
