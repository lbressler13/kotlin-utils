package xyz.lbres.kotlinutils.testutils

import xyz.lbres.kotlinutils.collection.list.WeightedList
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

typealias CompList = List<Comparable<*>>

/**
 * Perform a random action repeatedly and collect the results, and check that the distribution of results matches the given weights for the items.
 *
 * @param weightedItems [WeightedList]: items and their weights
 * @param randomAction () -> T: randomized action to perform repeatedly in order to collect results
 */
fun <T> runTestWithWeights(weightedItems: WeightedList<T>, randomAction: () -> T) {
    val iterations = 1000
    val errorRange = 0.075f

    // run action and collect results
    val results = List(iterations) { randomAction() }
        .groupBy { it }
        .map { it.key to it.value.size }

    // check that total adds up to total iterations
    val totalResults = results.sumOf { it.second }
    assertEquals(iterations, totalResults)

    // check result distribution for each item
    for (item in weightedItems) {
        val weight = item.second
        val result: Pair<T, Int> =
            results.find { it.first == item.first } ?: Pair(item.first, 0)

        when (weight) {
            0f -> assertEquals(0, result.second)
            1f -> assertEquals(iterations, result.second)
            else -> {
                // ranges of acceptable values, allowing for error
                val minFloat = max(weight - errorRange, 0f)
                val maxFloat = min(weight + errorRange, 1f)
                val minMatch = (minFloat * iterations).toInt()
                val maxMatch = (maxFloat * iterations).toInt()

                assertTrue(result.second in (minMatch..maxMatch))
            }
        }
    }
}

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
 * Assert that a test fails with a specific exception of type [T] being thrown and a specific error message
 *
 * @param message [String]: expected error message
 * @param block: test to run
 * @return An exception of the expected exception type [T] that was successfully caught
 */
inline fun <reified T : Throwable> assertFailsWithMessage(message: String, block: () -> Unit): T {
    val exception = assertFailsWith<T> { block() }
    assertEquals(message, exception.message)
    return exception
}

/**
 * Run a block of code and print a specific message if an [AssertionError] is thrown
 *
 * @param failureMessage [String]: message to print in case of failure
 * @param block: code to run
 */
fun runWithFailMessage(failureMessage: String, block: () -> Unit) {
    try {
        block()
    } catch (e: AssertionError) {
        printErr(failureMessage)
        throw e
    }
}

/**
 * Assert that a collection is empty
 *
 * @param collection [Collection]<T>?: collection to validate
 */
fun <T> assertEmpty(collection: Collection<T>?) = assertTrue(collection?.isEmpty() ?: false)
