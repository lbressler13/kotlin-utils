package xyz.lbres.kotlinutils.test

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TestUtilsTest {
    @Test
    fun testRunTestWithWeights() {
        // TODO
    }

    @Test
    fun testAssertEqualsAnyOf() {
        // TODO
    }

    @Test
    fun testRunTestWithRetry() {
        var iter = 0

        // first success
        var test: () -> Unit = {
            iter++
            val a = 52
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
}
