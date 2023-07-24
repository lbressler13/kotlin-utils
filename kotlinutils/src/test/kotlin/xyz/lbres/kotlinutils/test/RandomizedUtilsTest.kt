package xyz.lbres.kotlinutils.test

import xyz.lbres.kotlinutils.collection.mutable.ext.popRandom
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.generic.ext.isNull
import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RandomizedUtilsTest {
    @Test
    fun testRunTestWithRetry() {
        var iter = 0

        // first success
        var test: () -> Unit = { iter++ }
        runTestWithRetry(test)
        assertEquals(1, iter)

        iter = 0
        test = {
            iter++
            try {
                assertTrue(false)
            } catch (_: AssertionError) {}
        }
        runTestWithRetry(test)
        assertEquals(1, iter)

        iter = 0
        var string = ""
        test = {
            iter++
            string += "a"
            assertTrue { string.isNotEmpty() }
        }
        runTestWithRetry(test)
        assertEquals(1, iter)

        // second success
        iter = 0
        var hasRun = false
        test = {
            iter++
            if (!hasRun) {
                hasRun = true
                throw Exception()
            }
        }
        runTestWithRetry(test)
        assertEquals(2, iter)

        iter = 0
        string = ""
        test = {
            iter++
            string += "a"
            assertTrue { string.length > 1 }
        }
        runTestWithRetry(test)
        assertEquals(2, iter)

        // fails
        iter = 0
        string = ""
        test = {
            iter++
            string += "a"
            assertTrue { string.length > 2 }
        }
        assertFailsWith<AssertionError> { runTestWithRetry(test) }
        assertEquals(2, iter)

        iter = 0
        test = {
            iter++
            1 / 0
        }
        assertFailsWith<ArithmeticException> { runTestWithRetry(test) }
        assertEquals(2, iter)
    }

    @Test
    fun runRandomTestTest() {
        // specified iterations
        var iter = 0
        var intList = listOf(4, 4, 4, 4, 4, 4, 4, 4, 4, 3)
        val intAction: () -> Int = {
            iter++
            intList.random()
        }
        runRandomTest(100, intAction) { it == 3 }
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
        runRandomTest(30, listAction) { it.contains(6) }
        assertEquals(30, iter)

        iter = 0
        runRandomTest(listAction) { it.contains(6) }
        assertEquals(20, iter)

        intList = listOf(0, 1, 2, 3, 4, 5)
        runRandomValueChangedTest(null) { intList.random() * intList.random() }
    }

    @Test
    fun checkDistributedResultsTest() {
        // true
        val list1 = mutableListOf("hello", "world", "hello world", "goodbye", "farewell")
        val list2 = list1.toMutableList()
        checkDistributedResults(list1, list1.size, 0f) {
            it as MutableList
            list2.popRandom()!!
        }

        var count = 0
        checkDistributedResults(0 until 15, 15, 0f) {
            val oldCount = count
            count++
            oldCount
        }

        checkDistributedResults(5..6, 20, 1f) { 5 }

        val listRandom: (Iterable<Int>) -> Int = { (it as List).random() }
        val intList = listOf(1, 2, 3, 4, 8, 9, 17, 23, -100, 14, 44, -9)
        assertFailsWith<AssertionError> {
            checkDistributedResults(intList, 5, 0.5f, listRandom)
        }

        runTestWithRetry {
            assertFailsWith<AssertionError> {
                checkDistributedResults(intList, intList.size, 0.5f, listRandom)
            }
        }
        checkDistributedResults(intList, intList.size * 80, 0.5f, listRandom)

        assertFailsWith<AssertionError> {
            checkDistributedResults(intList, intList.size * 80, 0f, listRandom)
        }

        count = 0
        var getValueInt: (Iterable<Int>) -> Int = {
            it as List
            val oldCount = count
            count++
            when (oldCount % 10) {
                0, 1, 2 -> it[0]
                3, 4, 5 -> it[1]
                else -> it[2]
            }
        }
        checkDistributedResults(listOf(3, 4, 5), 100, 0.5f, getValueInt)
        assertFailsWith<AssertionError> {
            checkDistributedResults(listOf(3, 4, 5), 100, 0.1f, getValueInt)
        }

        // default error margin
        checkDistributedResults(listOf(3, 4, 5), 100, 0.33f, getValueInt)
        checkDistributedResults(listOf(3, 4, 5), 100, getValueInt)

        count = 0
        getValueInt = {
            it as List
            val oldCount = count
            count++
            when (oldCount % 10) {
                0, 1, 2, 3 -> it[0]
                4, 5 -> it[1]
                else -> it[2]
            }
        }
        checkDistributedResults(listOf(3, 4, 5), 100, 0.75f, getValueInt)
        assertFailsWith<AssertionError> { checkDistributedResults(listOf(3, 4, 5), 100, getValueInt) }
    }

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
            runRandomTest(action, check)
        } else {
            runRandomTest(iterations, action, check)
        }
        assertEquals(expectedIterations, iter)
    }
}
