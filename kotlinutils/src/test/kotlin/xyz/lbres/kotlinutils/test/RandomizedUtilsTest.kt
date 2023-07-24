package xyz.lbres.kotlinutils.test

import xyz.lbres.kotlinutils.collection.mutable.ext.popRandom
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

    @Test fun runRandomCheckTest() = runRunRandomCheckTests()
}
