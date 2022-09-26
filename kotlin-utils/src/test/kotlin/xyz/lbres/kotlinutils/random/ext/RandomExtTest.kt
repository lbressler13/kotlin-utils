package xyz.lbres.kotlinutils.random.ext

import xyz.lbres.kotlinutils.list.WeightedList
import xyz.lbres.kotlinutils.runTestWithWeights
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class RandomExtTest {
    private val random = Random.Default

    @Test
    internal fun testNextBoolean() {
        runSingleNextBooleanTest(0f)
        runSingleNextBooleanTest(1f)

        runSingleNextBooleanTest(0.1f)
        runSingleNextBooleanTest(0.25f)
        runSingleNextBooleanTest(0.5f)
        runSingleNextBooleanTest(0.9f)

        val expectedMessage = "Probability must be in range 0f..1f"
        assertFailsWith<IllegalArgumentException>(expectedMessage) { random.nextBoolean(-0.5f) }
        assertFailsWith<IllegalArgumentException>(expectedMessage) { random.nextBoolean(1.5f) }
    }

    /**
     * Run nextBoolean test for a single probability
     *
     * @param probability [Float]: the value to test
     */
    private fun runSingleNextBooleanTest(probability: Float) {
        val weightedList = listOf(Pair(true, probability), Pair(false, 1f - probability))
        val randomAction = { random.nextBoolean(probability) }
        runTestWithWeights(weightedList, randomAction)
    }

    @Test
    internal fun testNextFromWeightedList() {
        // errors
        val expectedError = "Weights must total 1"
        assertFailsWith<IllegalArgumentException>(expectedError) { random.nextFromWeightedList(listOf()) }

        var list = listOf(
            Pair(1, 0.1f),
            Pair(2, 0.2f),
            Pair(3, 0.75f)
        )
        assertFailsWith<IllegalArgumentException>(expectedError) { random.nextFromWeightedList(list) }

        list = listOf(
            Pair(1, 0.1f),
            Pair(2, 0.2f),
            Pair(3, 0.6f)
        )
        assertFailsWith<IllegalArgumentException>(expectedError) { random.nextFromWeightedList(list) }

        list = listOf(
            Pair(1, 0f),
            Pair(2, 0f),
        )
        assertFailsWith<IllegalArgumentException>(expectedError) { random.nextFromWeightedList(list) }

        list = listOf(
            Pair(1, -0.1f),
            Pair(2, 0.5f),
            Pair(6, 0.6f),
        )
        assertFailsWith<IllegalArgumentException>(expectedError) { random.nextFromWeightedList(list) }

        // tests with int
        list = listOf(Pair(3, 1f))
        runSingleNextFromWeightedListTest(list)

        list = listOf(Pair(3, 1f), Pair(2, 0f))
        runSingleNextFromWeightedListTest(list)

        list = listOf(Pair(3, 0.5f), Pair(4, 0.5f))
        runSingleNextFromWeightedListTest(list)

        list = listOf(Pair(3, 0.25f), Pair(4, 0.75f))
        runSingleNextFromWeightedListTest(list)

        list = listOf(Pair(-1, 0.15f), Pair(0, 0.3f), Pair(1, 0.55f))
        runSingleNextFromWeightedListTest(list)

        list = listOf(Pair(0, 0.1f), Pair(-1, 0.2f), Pair(5, 0.45f), Pair(2, 0.25f))
        runSingleNextFromWeightedListTest(list)

        // other types
        val stringList = listOf(Pair("hello", 0.3f), Pair("world", 0.65f), Pair("goodbye", 0.05f))
        runSingleNextFromWeightedListTest(stringList)

        val rangeList = listOf(Pair(0..1000, 0.2f), Pair(1000..10000, 0.6f), Pair(10000..1000000, 0.2f))
        runSingleNextFromWeightedListTest(rangeList)
    }

    /**
     * Run nextFromWeightedList test for a single list
     *
     * @param list [List]: the value to test
     */
    private fun <T> runSingleNextFromWeightedListTest(list: WeightedList<T>) {
        val randomAction = { random.nextFromWeightedList(list) }
        runTestWithWeights(list, randomAction)
    }
}
