package xyz.lbres.kotlinutils.closedrange.charrange

/**
 * Create an CharRange consisting of a single char
 *
 * @param value [Char]: the char to create range of
 * @return [CharRange]: CharRange containing value and no other values
 */
fun rangeOfChar(value: Char): CharRange = value..value
