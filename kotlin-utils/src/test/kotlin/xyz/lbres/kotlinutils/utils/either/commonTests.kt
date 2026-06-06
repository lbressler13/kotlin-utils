@file: Suppress(Suppressions.REMOVE_EXPLICIT_TYPES)
package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.utils.simpleIf
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertIsNot
import kotlin.test.assertTrue

private fun <T, S> constructL(value: T, mutable: Boolean): Either<T, S> {
    return simpleIf(mutable, MutableEither(value), Either(value))
}

private fun <T, S> constructR(value: S, mutable: Boolean): Either<T, S> {
    return simpleIf(mutable, MutableEither<T, S>(value), Either<T, S>(value))
}

private fun <T, S> checkMutability(either: Either<T, S>, mutable: Boolean) {
    if (mutable) {
        assertIs<MutableEither<T, S>>(either)
    } else {
        assertIsNot<MutableEither<T, S>>(either)
    }
}

fun runTestConstructor(mutable: Boolean) {
    var intString = constructL<Int, String>(123, mutable)
    checkLeft(intString, 123)
    checkMutability(intString, mutable)

    intString = constructR("hello", mutable)
    checkRight(intString, "hello")
    checkMutability(intString, mutable)

    val intInt = constructL<Int, Int>(4, mutable)
    checkLeft(intInt, 4)
    checkMutability(intInt, mutable)

    // nullable
    var nullable = constructL<Int?, String?>(null, mutable)
    checkLeft(nullable, null)
    checkMutability(nullable, mutable)

    nullable = constructL<Int?, String?>(5, mutable)
    checkLeft(nullable, 5)
    checkMutability(nullable, mutable)

    nullable = constructR<Int?, String?>("12", mutable)
    checkRight(nullable, "12")
    checkMutability(nullable, mutable)

    // nested either
    var nested = constructR<List<Int>, Either<String, Int>>(Either("123"), mutable)
    checkRight(nested, Either<String, Int>("123"))
    checkLeft(nested.right!!, "123")
    checkMutability(nested, mutable)

    nested = constructL(listOf(1, 2, 5), mutable)
    checkLeft(nested, listOf(1, 2, 5))
    checkMutability(nested, mutable)
}

fun runTestWithLeft(mutable: Boolean) {
    fun <T, S> withLeft(value: T): Either<T, S> {
        return simpleIf(mutable, MutableEither.withLeft(value), Either.withLeft(value))
    }

    val ints = withLeft<Int, Int>(5)
    checkLeft(ints, 5)
    checkMutability(ints, mutable)

    val nullable = withLeft<Int?, String>(null)
    checkLeft(nullable, null)
    checkMutability(nullable, mutable)

    val string = withLeft<String, Int?>("123")
    checkLeft(string, "123")
    checkMutability(string, mutable)

    val list = withLeft<List<Int>, List<String>>(emptyList())
    checkLeft(list, emptyList())
    checkMutability(list, mutable)
}

fun runTestWithRight(mutable: Boolean) {
    fun <T, S> withRight(value: S): Either<T, S> {
        return simpleIf(mutable, MutableEither.withRight(value), Either.withRight(value))
    }

    val ints = withRight<Int, Int>(5)
    checkRight(ints, 5)
    checkMutability(ints, mutable)

    val nullable = withRight<String, Int?>(null)
    checkRight(nullable, null)
    checkMutability(nullable, mutable)

    val string = withRight<Int?, String>("123")
    checkRight(string, "123")
    checkMutability(string, mutable)

    val list = withRight<List<String>, List<Int>>(emptyList())
    checkRight(list, emptyList())
    checkMutability(list, mutable)
}

fun runTestIsNull(mutable: Boolean) {
    fun <T, S> setLeft(either: Either<T, S>, value: T): Either<T, S> {
        return if (mutable) {
            either as MutableEither<T, S>
            either.left = value
            either
        } else {
            Either(value)
        }
    }

    fun <T, S> setRight(either: Either<T, S>, value: S): Either<T, S> {
        return if (mutable) {
            either as MutableEither<T, S>
            either.right = value
            either
        } else {
            Either.withRight(value)
        }
    }

    // not null
    var intString: Either<Int?, String?> = constructR("hello", mutable)
    assertFalse(intString.isNull())
    intString = setLeft(intString, 123)
    assertFalse(intString.isNull())

    var intInt: Either<Int, Int?> = constructL(4, mutable)
    assertFalse(intInt.isNull())

    intInt = setRight(intInt, 0)
    assertFalse(intInt.isNull())

    var listString: Either<List<String?>?, String?> = constructR("", mutable)
    assertFalse(listString.isNull())

    listString = setLeft(listString, listOf(null, null, null))
    assertFalse(listString.isNull())

    // null
    intString = setLeft(intString, null)
    assertTrue(intString.isNull())

    intInt = setRight(intInt, null)
    assertTrue(intInt.isNull())

    listString = setLeft(listString, null)
    assertTrue(listString.isNull())

    listString = setRight(listString, null)
    assertTrue(listString.isNull())
}
