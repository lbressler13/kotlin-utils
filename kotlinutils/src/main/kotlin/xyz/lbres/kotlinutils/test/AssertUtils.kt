package xyz.lbres.kotlinutils.test

import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Assert that an iterable contains a given value
 *
 * @param iterable [Iterable]<T>
 * @param value T
 */
fun <T> assertContains(iterable: Iterable<T>, value: T) {
    assertTrue(value in iterable)
}

/**
 * Assert that an iterable does not contain a given value
 *
 * @param iterable [Iterable]<T>
 * @param value T
 */
fun <T> assertNotContains(iterable: Iterable<T>, value: T) {
    assertFalse(value in iterable)
}
