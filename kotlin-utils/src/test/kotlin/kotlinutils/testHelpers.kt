package kotlinutils

import kotlinutils.list.WeightedList
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
 * Perform a random action repeatedly and collect the results, and check that the distribution of results matches the given weights for the items.
 *
 * @param weightedItems [WeightedList]: items and their weights
 * @param randomAction [() -> T]: randomized action to perform repeatedly in order to collect results
 */
internal fun <T> runTestWithWeights(weightedItems: WeightedList<T>, randomAction: () -> T) {
    val iterations = 1000
    val errorRange = 0.05f

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

        when (weight)  {
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
 * Run a function several times
 *
 * @param function [() -> Unit]: function to run
 */
internal fun repeat(function: () -> Unit) {
    val iterations = 20

    for (i in 0 until iterations) {
        function()
    }
}
