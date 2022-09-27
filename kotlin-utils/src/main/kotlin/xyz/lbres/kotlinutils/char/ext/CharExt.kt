package xyz.lbres.kotlinutils.char.ext

import xyz.lbres.kotlinutils.general.ternaryIf
import xyz.lbres.kotlinutils.int.ext.isZero

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Char]
 * @return [Char] the current value, or the default
 */
fun Char.ifZero(getDefaultValue: () -> Char): Char = ternaryIf(isZero(), getDefaultValue(), this)

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Char.isZero(): Boolean = code.isZero()
