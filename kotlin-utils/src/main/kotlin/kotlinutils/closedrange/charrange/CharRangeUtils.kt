package kotlinutils.closedrange.charrange

/**
 * Create an CharRange consisting of a single num
 *
 * @param value [Char]: the number to create range of
 * @return [CharRange]: CharRange containing value and no other values
 */
fun rangeOfChar(value: Char): CharRange = value..value
