package xyz.lbres.kotlinutils.test

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import kotlin.test.assertTrue

/**
 * Run a test, with one retry in the event of failure.
 * Can be used for tests with a small but non-zero possibility of failure due to randomization.
 *
 * @param test () -> [Unit]: test to run
 */
fun runTestWithRetry(test: () -> Unit) {
    try {
        test()
    } catch (_: Throwable) {
        test()
    }
}

/**
 * Perform a random action twenty times, checking to ensure that the result that was randomized.
 * The action should also contain any assertions about a single result.
 *
 * @param randomAction [() -> T]: generate a single result, and perform any assertions about that result
 * @param randomCheck [(T) -> Boolean]: check if a result meets criteria for randomization
 */
fun <T> runRandomCheck(randomAction: () -> T, randomCheck: (T) -> Boolean) {
    runRandomCheck(20, randomAction, randomCheck)
}

/**
 * Perform a random action repeatedly, checking to ensure that the result that was randomized.
 * The action should also contain any assertions about a single result.
 *
 * @param iterations [Int]: number of times to repeat action
 * @param randomAction [() -> T]: generate a single result, and perform any assertions about that result
 * @param randomCheck [(T) -> Boolean]: check if a result meets criteria for randomization
 */
fun <T> runRandomCheck(iterations: Int, randomAction: () -> T, randomCheck: (T) -> Boolean) {
    var checkPassed = false

    repeat(iterations) {
        val result = randomAction()
        if (randomCheck(result)) {
            checkPassed = true
        }
    }

    assertTrue(checkPassed)
}

/**
 * Repeatedly generate a random value, and check that the results are evenly distributed.
 * Allows 33% variation from exactly equal distribution for each value.
 *
 * @param values [Iterable]<T>: all possible values
 * @param iterations [Int]: number of times to generate value
 * @param getValue ([Iterable]<T>) -> T: function that takes [values] as parameter, and uses it to randomly generate a value
 */
fun <T> checkDistributedResults(values: Iterable<T>, iterations: Int, getValue: (Iterable<T>) -> T) {
    checkDistributedResults(values, iterations, 0.33f, getValue)
}

/**
 * Repeatedly generate a random value, and check that the results are evenly distributed
 *
 * @param values [Iterable]<T>: all possible values
 * @param iterations [Int]: number of times to generate value
 * @param errorMargin [Float]: allowed variation from exact equal distribution, expected to be between 0 and 1
 * @param getValue ([Iterable]<T>) -> T: function that takes [values] as parameter, and uses it to randomly generate a value
 */
fun <T> checkDistributedResults(values: Iterable<T>, iterations: Int, errorMargin: Float, getValue: (Iterable<T>) -> T) {
    val results: MutableMultiSet<T> = mutableMultiSetOf()
    repeat(iterations) {
        val result = getValue(values)
        results.add(result)
        assertContains(values, result)
    }

    val average = iterations / values.count()
    val allowedError = (average * errorMargin).toInt()
    val allowedRange = (average - allowedError)..(average + allowedError)
    values.forEach {
        assertContains(allowedRange, results.getCountOf(it))
    }
}
