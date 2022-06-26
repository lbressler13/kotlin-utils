package kotlinutil

import kotlin.test.assertTrue

private const val iterations = 20

/**
 * Perform a random action repeatedly, checking to ensure that the result was randomized at least once.
 * Any other assertions about result should be contained in the random action.
 *
 * @param randomAction [() -> T]: randomized action to perform, which generates result
 * @param randomCheck [(T) -> Boolean]: check that result was randomized
 */
internal fun <T> runRandomTest(randomAction: () -> T, randomCheck: (T) -> Boolean) {
    var checkPassed = false

    repeat {
        val result = randomAction()
        if (randomCheck(result)) {
            checkPassed = true
        }
    }

    assertTrue(checkPassed)
}

/**
 * Run a function several times
 *
 * @param function [() -> Unit]: function to run
 */
internal fun repeat(function: () -> Unit) {
    for (i in 0 until iterations) {
        function()
    }
}
