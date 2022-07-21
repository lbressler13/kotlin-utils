package kotlinutils.int.ext

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue [() -> Int]
 * @return [Int] the current value, or the default
 */
fun Int.ifZero(getDefaultValue: () -> Int): Int {
    return if (equals(0)) {
        getDefaultValue()
    } else {
        this
    }
}
