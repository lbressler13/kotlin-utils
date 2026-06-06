package xyz.lbres.kotlinutils.utils.either

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Check that all fields in [either] match the expected state when the left value is set to [value]
 */
fun <S, T> checkLeft(either: Either<S, T>, value: S) {
    assertEquals(value, either.left)
    assertNull(either.right)
    assertTrue(either.isLeft)
    assertFalse(either.isRight)
}

/**
 * Check that all fields in [either] match the expected state when the right value is set to [value]
 */
fun <S, T> checkRight(either: Either<S, T>, value: T) {
    assertNull(either.left)
    assertEquals(value, either.right)
    assertFalse(either.isLeft)
    assertTrue(either.isRight)
}

/**
 * Check both directions of equality for [first] and [second]
 */
fun <S, T, U, V> bothEquals(first: Either<S, T>, second: Either<U, V>) {
    assertTrue(first == second)
    assertTrue(second == first)
}

/**
 * Check both directions of non-equality for [first] and [second]
 */
fun <S, T, U, V> bothNotEquals(first: Either<S, T>, second: Either<U, V>) {
    assertFalse(first == second)
    assertFalse(second == first)
}
