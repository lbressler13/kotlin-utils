package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.utils.simpleIf
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertIsNot
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Check that all fields in [either] match the expected state when the left value is set to [value]
 */
fun <S, T> checkLeft(either: Either<S, T>, value: S, mutable: Boolean? = null) {
    assertEquals(value, either.left)
    assertNull(either.right)
    assertTrue(either.isLeft)
    assertFalse(either.isRight)

    if (mutable == true) {
        assertIs<MutableEither<S, T>>(either)
    } else if (mutable == false) {
        assertIsNot<MutableEither<S, T>>(either)
    }
}

/**
 * Check that all fields in [either] match the expected state when the right value is set to [value]
 */
fun <S, T> checkRight(either: Either<S, T>, value: T, mutable: Boolean? = null) {
    assertNull(either.left)
    assertEquals(value, either.right)
    assertFalse(either.isLeft)
    assertTrue(either.isRight)

    if (mutable == true) {
        assertIs<MutableEither<S, T>>(either)
    } else if (mutable == false) {
        assertIsNot<MutableEither<S, T>>(either)
    }
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

// construct using left value, taking mutability into account
fun <S, T> constructL(value: S, mutable: Boolean): Either<S, T> {
    return simpleIf(mutable, MutableEither(value), Either(value))
}

// construct using right value, taking mutability into account
fun <S, T> constructR(value: T, mutable: Boolean): Either<S, T> {
    return simpleIf(mutable, MutableEither<S, T>(value), Either<S, T>(value))
}

// set the left value, either by modifying a mutable either or by generating a new immutable instance
fun <S, T> setLeft(either: Either<S, T>, value: S, mutable: Boolean): Either<S, T> {
    return if (mutable) {
        either as MutableEither<S, T>
        either.left = value
        either
    } else {
        Either(value)
    }
}

// set the right value, either by modifying a mutable either or by generating a new immutable instance
fun <S, T> setRight(either: Either<S, T>, value: T, mutable: Boolean): Either<S, T> {
    return if (mutable) {
        either as MutableEither<S, T>
        either.right = value
        either
    } else {
        Either.withRight(value)
    }
}
