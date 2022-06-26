package kotlinutils.intrange

/**
 * Create an IntRange consisting of a single num
 *
 * @param i [Int]: the number to create range of
 * @return [IntRange]: IntRange containing i and no other values
 */
fun rangeOfInt(i: Int): IntRange = i..i
