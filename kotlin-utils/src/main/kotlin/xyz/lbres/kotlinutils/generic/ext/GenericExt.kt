package xyz.lbres.kotlinutils.generic.ext

/**
 * Returns this value if not null, or the result of calling [getDefaultValue] if null
 *
 * @param getDefaultValue [() -> [T]]
 * @return [T] the current value, or the default
 */
fun <T> T?.ifNull(getDefaultValue: () -> T): T = this ?: getDefaultValue()
