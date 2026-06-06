package xyz.lbres.kotlinutils.utils.either

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun <S, T> checkLeft(either: Either<S, T>, value: S) {
    assertEquals(value, either.left)
    assertNull(either.right)
    assertTrue(either.isLeft)
    assertFalse(either.isRight)
}

fun <S, T> checkRight(either: Either<S, T>, value: T) {
    assertNull(either.left)
    assertEquals(value, either.right)
    assertFalse(either.isLeft)
    assertTrue(either.isRight)
}

fun <S, T, U, V> bothEquals(first: Either<S, T>, second: Either<U, V>) {
    assertTrue(first == second)
    assertTrue(second == first)
}

fun <S, T, U, V> bothNotEquals(first: Either<S, T>, second: Either<U, V>) {
    assertFalse(first == second)
    assertFalse(second == first)
}
