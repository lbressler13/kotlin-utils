package kotlinutils.utils

/**
 * Run a function several times
 *
 * @param iterations [Int]: number of times to run [action]
 * @param action [() -> Unit]: function to run
 */
fun repeat(iterations: Int, action: () -> Unit) {
    for (i in 0 until iterations) {
        action()
    }
}
