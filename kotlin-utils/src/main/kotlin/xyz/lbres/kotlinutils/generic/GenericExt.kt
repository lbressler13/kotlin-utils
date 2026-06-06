package xyz.lbres.kotlinutils.generic

/**
 * Returns this value if not null, or the result of calling [getDefaultValue] if null
 *
 * @param getDefaultValue () -> T
 * @return T the current value, or the default
 */
fun <T> T?.ifNull(getDefaultValue: () -> T): T = this ?: getDefaultValue()

/**
 * Returns true if a value is null, or false otherwise
 *
 * @return [Boolean]
 */
fun <T> T?.isNull(): Boolean = this == null

/**
 * Returns true if a value is not null, or false otherwise
 *
 * @return [Boolean]
 */
fun <T> T?.isNotNull(): Boolean = this != null

/**
 * Execute a block of code to generate a return value if this value is not null
 *
 * @param block: block of code to execute
 * @return `null` if this value is `null`, or result of executing [block] on the non-null value
 */
fun <S, T> T?.ifNotNull(block: (T) -> S): S? = if (this == null) null else block(this)
