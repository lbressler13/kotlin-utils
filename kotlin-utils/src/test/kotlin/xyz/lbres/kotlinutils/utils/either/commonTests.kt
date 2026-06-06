@file: Suppress(Suppressions.REMOVE_EXPLICIT_TYPES)
package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.utils.simpleIf
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runTestConstructor(mutable: Boolean) {
    var intString = constructL<Int, String>(123, mutable)
    checkLeft(intString, 123)

    intString = constructR("hello", mutable)
    checkRight(intString, "hello")

    val intInt = constructL<Int, Int>(4, mutable)
    checkLeft(intInt, 4)

    // nullable
    var nullable = constructL<Int?, String?>(null, mutable)
    checkLeft(nullable, null)

    nullable = constructL<Int?, String?>(5, mutable)
    checkLeft(nullable, 5)

    nullable = constructR<Int?, String?>("12", mutable)
    checkRight(nullable, "12")

    // nested either
    var nested = constructR<List<Int>, Either<String, Int>>(Either("123"), mutable)
    checkRight(nested, Either<String, Int>("123"))
    checkLeft(nested.right!!, "123")

    nested = constructL(listOf(1, 2, 5), mutable)
    checkLeft(nested, listOf(1, 2, 5))
}

fun runTestWithLeft(mutable: Boolean) {
    fun <S, T> withLeft(value: S): Either<S, T> {
        return simpleIf(mutable, MutableEither.withLeft(value), Either.withLeft(value))
    }

    val ints = withLeft<Int, Int>(5)
    checkLeft(ints, 5, mutable)

    val nullable = withLeft<Int?, String>(null)
    checkLeft(nullable, null, mutable)

    val string = withLeft<String, Int?>("123")
    checkLeft(string, "123", mutable)

    val list = withLeft<List<Int>, List<String>>(emptyList())
    checkLeft(list, emptyList(), mutable)
}

fun runTestWithRight(mutable: Boolean) {
    fun <S, T> withRight(value: T): Either<S, T> {
        return simpleIf(mutable, MutableEither.withRight(value), Either.withRight(value))
    }

    val ints = withRight<Int, Int>(5)
    checkRight(ints, 5, mutable)

    val nullable = withRight<String, Int?>(null)
    checkRight(nullable, null, mutable)

    val string = withRight<Int?, String>("123")
    checkRight(string, "123", mutable)

    val list = withRight<List<String>, List<Int>>(emptyList())
    checkRight(list, emptyList(), mutable)
}

fun runTestIsNull(mutable: Boolean) {
    // not null
    var intString: Either<Int?, String?> = constructR("hello", mutable)
    assertFalse(intString.isNull())
    intString = setLeft(intString, 123, mutable)
    assertFalse(intString.isNull())

    var intInt: Either<Int, Int?> = constructL(4, mutable)
    assertFalse(intInt.isNull())

    intInt = setRight(intInt, 0, mutable)
    assertFalse(intInt.isNull())

    var listString: Either<List<String?>?, String?> = constructR("", mutable)
    assertFalse(listString.isNull())

    listString = setLeft(listString, listOf(null, null, null), mutable)
    assertFalse(listString.isNull())

    // null
    intString = setLeft(intString, null, mutable)
    assertTrue(intString.isNull())

    intInt = setRight(intInt, null, mutable)
    assertTrue(intInt.isNull())

    listString = setLeft(listString, null, mutable)
    assertTrue(listString.isNull())

    listString = setRight(listString, null, mutable)
    assertTrue(listString.isNull())
}

fun runTestToString(mutable: Boolean) {
    var intString = constructL<Int, String>(123, mutable)
    assertEquals("Either(123)", intString.toString())

    intString = setRight(intString, "hello", mutable)
    assertEquals("Either(hello)", intString.toString())

    var nullable = constructL<Int?, Int>(null, mutable)
    assertEquals("Either(null)", nullable.toString())

    nullable = setLeft(nullable, 3, mutable)
    assertEquals("Either(3)", nullable.toString())

    nullable = setRight(nullable, 3, mutable)
    assertEquals("Either(3)", nullable.toString())

    var subnested = constructL<String, Int>("123", mutable)
    var nested = constructR<List<Int>, Either<String, Int>>(subnested, mutable)
    assertEquals("Either(Either(123))", nested.toString())

    subnested = setRight(subnested, 12, mutable)
    nested = setRight(nested, subnested, mutable)
    assertEquals("Either(Either(12))", nested.toString())

    nested = Either(listOf(1, 4))
    assertEquals("Either([1, 4])", nested.toString())
}

fun runTestToEither(mutable: Boolean) {
    fun <S, T> castLeft(either: Either<S, T>, value: S) {
        val result = if (mutable) {
            either as MutableEither<S, T>
            either.toEither()
        } else {
            either.toMutableEither()
        }
        checkLeft(result, value, !mutable)
    }

    fun <S, T> castRight(either: Either<S, T>, value: T) {
        val result = if (mutable) {
            either as MutableEither<S, T>
            either.toEither()
        } else {
            either.toMutableEither()
        }
        checkRight(result, value, !mutable)
    }

    var int = constructR<Int, Int?>(null, mutable)
    castRight(int, null)
    int = setLeft(int, 4, mutable)
    castLeft(int, 4)
    int = setRight(int, 4, mutable)
    castRight(int, 4)

    var string = constructL<List<String>, String>(emptyList(), mutable)
    castLeft(string, emptyList())
    string = setLeft(string, listOf("123"), mutable)
    castLeft(string, listOf("123"))
    string = setRight(string, "hello world", mutable)
    castRight(string, "hello world")
}
