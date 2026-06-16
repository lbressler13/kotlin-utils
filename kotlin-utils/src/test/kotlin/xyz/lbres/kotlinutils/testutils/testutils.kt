package xyz.lbres.kotlinutils.testutils

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

typealias CompList = List<Comparable<*>>

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
 * Check that lists of values pass or fail a given check, and print a message on failure
 *
 * @param trueValues: values for which the check is expected to succeed
 * @param falseValues: value for which the check is  expected to fail
 * @param description: method to describe the check being performed, should include the value being checked
 * @param check: the check to perform
 */
fun <T> checkTrueFalse(trueValues: List<T>, falseValues: List<T>, description: (T) -> String, check: (T) -> Boolean) {
    trueValues.forEach {
        runWithFailMessage("${description(it)} is false") { assertTrue(check(it)) }
    }
    falseValues.forEach {
        runWithFailMessage("${description(it)} is true") { assertFalse(check(it)) }
    }
}

/**
 * Assert that a collection is empty
 *
 * @param collection [Collection]<T>?: collection to validate
 */
fun <T> assertEmpty(collection: Collection<T>?) = assertTrue(collection?.isEmpty() ?: false)
