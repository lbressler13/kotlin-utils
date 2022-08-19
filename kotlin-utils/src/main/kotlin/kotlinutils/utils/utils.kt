package kotlinutils.utils

/**
 * Run a function several times. Shorter version of a for-loop where the index isn't used.
 *
 * @param iterations [Int]: number of times to run [action]
 * @param action [() -> Unit]: function to run
 */
fun repeat(iterations: Int, action: () -> Unit) {
    for (i in 0 until iterations) {
        action()
    }
}
