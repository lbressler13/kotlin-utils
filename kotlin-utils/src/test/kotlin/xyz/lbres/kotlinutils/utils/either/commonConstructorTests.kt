@file: Suppress(Suppressions.REMOVE_EXPLICIT_TYPES)
package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.utils.simpleIf

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

    nullable = constructR<Int?, String?>(null, mutable)
    checkRight(nullable, null)

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
