package xyz.lbres.kotlinutils.generic.ext

/**
 * Returns this value if not null, or the result of calling [getDefaultValue] if null
 *
 * @param getDefaultValue [() -> [T]]
 * @return [T] the current value, or the default
 */
fun <T> T?.ifNull(getDefaultValue: () -> T): T = this ?: getDefaultValue()

/**
 * Returns true if a value is null, or false otherwise
 *
 * @return [Boolean]
 */
fun <T> T?.isNull(): Boolean = this == null

/**
 * Returns true if a value is null, or false otherwise
 *
 * @return [Boolean]
 */
fun <T> T?.isNotNull(): Boolean = this != null
