package xyz.lbres.kotlinutils.test

import xyz.lbres.kotlinutils.set.multiset.multiSetOf
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
        // true
        var intValue = 5
        var intOptions: Collection<Int> = listOf(5)
        assertEqualsAnyOf(intOptions, intValue)

        intOptions = listOf(5, 5, 5, 5, 5)
        assertEqualsAnyOf(intOptions, intValue)

        intOptions = listOf(15, 20, 23, -8, 2, 5, 4)
        assertEqualsAnyOf(intOptions, intValue)

        intValue = -8
        assertEqualsAnyOf(intOptions, intValue)

        var stringValue = "hello world"
        var stringOptions = setOf("hello", "world", "hello world", "farewell planet")
        assertEqualsAnyOf(stringOptions, stringValue)

        var setValue = setOf(5, "hello", 'c', 1.5)
        var setOptions = multiSetOf(setOf(5, "hello", 'c', 1.5), setOf(5, "hello", 'c', 1.5), setOf(5, 1.5))
        assertEqualsAnyOf(setOptions, setValue)

        // false
        intValue = 1

        intOptions = setOf(-1)
        assertFailsWith<AssertionError> { assertEqualsAnyOf(intOptions, intValue) }

        intOptions = setOf(0, 0, 4, 5, 2)
        assertFailsWith<AssertionError> { assertEqualsAnyOf(intOptions, intValue) }

        stringValue = "hello world"
        stringOptions = setOf("hello", "world", "Hello World", "farewell planet")
        assertFailsWith<AssertionError> { assertEqualsAnyOf(stringOptions, stringValue) }

        setValue = setOf(5, "hello", 'c', 1.5)
        setOptions = multiSetOf(setOf(5, "hello", 'c'), setOf("hello", 'c', 1.5), setOf('c', 1.5, "hello"))
        assertFailsWith<AssertionError> { assertEqualsAnyOf(setOptions, setValue) }
    }

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
}
