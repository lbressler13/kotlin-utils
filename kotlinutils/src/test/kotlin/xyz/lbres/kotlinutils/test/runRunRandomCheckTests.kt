package xyz.lbres.kotlinutils.test

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.generic.ext.isNull
import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.assertEquals

fun runRunRandomCheckTests() {

    // specified iterations
    var iter = 0
    var intList = listOf(4, 4, 4, 4, 4, 4, 4, 4, 4, 3)
    var intAction: () -> Int = {
        iter++
        intList.random()
    }
    runRandomCheck(100, intAction) { it == 3 }
    assertEquals(100, iter)

    intList = listOf(1, 2)
    runRandomValueChangedTest(10) { intList.random() }

    intList = listOf(0, 1, 2, 3, 4, 5)
    runRandomValueChangedTest(40) { intList.random() * intList.random() }

    iter = 0
    val listSet = setOf(listOf(1, 2, 3), listOf(4, 5, 6, 7), listOf(5, 6, 6), listOf(14, 5, 7, 8, 9), listOf(13), listOf(14))
    val listAction: () -> IntList = {
        iter++
        listSet.random()
    }
    runRandomCheck(30, listAction) { it.contains(6) }
    assertEquals(30, iter)

    iter = 0
    runRandomCheck(listAction) { it.contains(6) }
    assertEquals(20, iter)

    intList = listOf(0, 1, 2, 3, 4, 5)
    runRandomValueChangedTest(null) { intList.random() * intList.random() }

    iter = 0
    val previousValues: MutableSet<Int> = mutableSetOf()
    intAction = {
        iter++
        val value = intList.random()
        previousValues.add(value)
        value
    }
    runRandomCheck(40, intAction) { previousValues == intList.toSet() }
}

/**
 * Run single test where check is that different values are returned.
 * Validates number of iterations and runs test.
 *
 * @param iterations [Int]?: number of iterations to use. Uses default if `null`
 * @param randomAction () -> Unit: action to execute
 */
private fun <T> runRandomValueChangedTest(iterations: Int?, randomAction: () -> T) {
    val expectedIterations = simpleIf(iterations.isNull(), { 20 }, { iterations!! })
    var iter = 0
    val action: () -> T = {
        iter++
        randomAction()
    }

    var result: T? = null
    val check: (T) -> Boolean = {
        if (result == null) {
            result = it
            false
        } else {
            result != it
        }
    }

    if (iterations == null) {
        runRandomCheck(action, check)
    } else {
        runRandomCheck(iterations, action, check)
    }
    assertEquals(expectedIterations, iter)
}
