package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.utils.simpleIf

fun runTestConstructor(mutable: Boolean) {
    fun <T, S> constructL(value: T): Either<T, S> {
        return simpleIf(mutable, MutableEither(value), Either(value))
    }

    fun <T, S> constructR(value: S): Either<T, S> {
        return simpleIf(mutable, MutableEither<T, S>(value), Either<T, S>(value))
    }

    var intString = constructL<Int, String>(123)
    checkLeft(intString, 123)

    intString = constructR("hello")
    checkRight(intString, "hello")

    // nullable
    var nullable = constructL<Int?, String?>(null)
    checkLeft(nullable, null)

    nullable = constructL<Int?, String?>(5)
    checkLeft(nullable, 5)

    nullable = constructR<Int?, String?>("12")
    checkRight(nullable, "12")

    // nested either
    var nested = constructR<List<Int>, Either<String, Int>>(Either("123"))
    checkRight(nested, Either<String, Int>("123"))
    checkLeft(nested.right!!, "123")

    nested = constructL(listOf(1, 2, 5))
    checkLeft(nested, listOf(1, 2, 5))
}

fun runTestWithLeft(mutable: Boolean) {
    fun <T, S> withLeft(value: T): Either<T, S> {
        return simpleIf(mutable, MutableEither.withLeft(value), Either.withLeft(value))
    }

    val ints = withLeft<Int, Int>(5)
    checkLeft(ints, 5)

    val nullable = withLeft<Int?, String>(null)
    checkLeft(nullable, null)

    val string = withLeft<String, Int?>("123")
    checkLeft(string, "123")

    val list = withLeft<List<Int>, List<String>>(emptyList())
    checkLeft(list, emptyList())
}

fun runTestWithRight(mutable: Boolean) {
    fun <T, S> withRight(value: S): Either<T, S> {
        return simpleIf(mutable, MutableEither.withRight(value), Either.withRight(value))
    }

    val ints = withRight<Int, Int>(5)
    checkRight(ints, 5)

    val nullable = withRight<String, Int?>(null)
    checkRight(nullable, null)

    val string = withRight<Int?, String>("123")
    checkRight(string, "123")

    val list = withRight<List<String>, List<Int>>(emptyList())
    checkRight(list, emptyList())
}
