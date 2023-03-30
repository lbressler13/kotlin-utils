package xyz.lbres.kotlinutils

import xyz.lbres.kotlinutils.list.WeightedList
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Perform a random action repeatedly and collect the results, and check that the distribution of results matches the given weights for the items.
 *
 * @param weightedItems [WeightedList]: items and their weights
 * @param randomAction [() -> T]: randomized action to perform repeatedly in order to collect results
 */
internal fun <T> runTestWithWeights(weightedItems: WeightedList<T>, randomAction: () -> T) {
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
 * Assert that a value matches any of the provided options
 *
 * @param expectedOptions [List]<T>: list of possible allowed results
 * @param actual [T]: the actual value
 */
internal fun <T> assertEqualsAnyOf(expectedOptions: List<T>, actual: T) {
    assertTrue { expectedOptions.any { it == actual } }
}

/**
 * Run a test, with one retry in the event of failure.
 * Can be used for tests with a small but non-zero possibility of failure due to randomization.
 *
 * @param test () -> [Unit]: test to run
 */
internal fun runTestWithRetry(test: () -> Unit) {
    try {
        test()
    } catch (_: Throwable) {
        test()
    }
}
