package xyz.lbres.kotlinutils.utils.either

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runTestEqualsValue(mutable: Boolean) {
    var intString = constructL<Int, String>(123, mutable)
    assertTrue(intString.equalsValue(123))
    assertFalse(intString.equalsValue(12))
    assertFalse(intString.equalsValue("123"))

    intString = setRight(intString, "hello", mutable)
    assertTrue(intString eqV "hello")
    assertFalse(intString eqV 123)

    // nested
    var subnested = constructL<String, Int>("123", mutable)
    var nested = constructR<List<Int>, Either<String, Int>>(subnested, mutable)
    assertTrue(nested eqV "123")

    var nestedNested = constructR<String, Either<List<Int>, Either<String, Int>>>(nested, mutable)
    assertTrue(nestedNested eqV "123")

    subnested = setRight(subnested, 5, mutable)
    nested = setRight(nested, subnested, mutable)
    nestedNested = setRight(nestedNested, nested, mutable)
    assertTrue(nestedNested eqV 5)

    // nullable
    var nullable = constructL<Int?, Int>(null, mutable)
    assertTrue(nullable eqV null)
    nullable = setLeft(nullable, 2, mutable)
    assertTrue(nullable eqV 2)
    assertFalse(nullable eqV null)

    // other type
    intString = constructL(123, mutable)
    var other = constructL<Int, String>(123, !mutable)
    assertTrue(intString eqV other)

    other = setRight(other, "", !mutable)
    assertFalse(intString eqV other)
}

fun runTestEquals(mutable: Boolean) {
    var intString = constructL<Int, String>(123, mutable)
    assertEquals(intString, intString)

    var arrInt = constructR<IntArray, Int>(123, mutable)
    bothEquals(intString, arrInt)

    arrInt = setRight(arrInt, 12, mutable)
    bothNotEquals(intString, arrInt)

    arrInt = setRight(arrInt, 123, mutable)
    bothEquals(intString, arrInt)

    arrInt = setLeft(arrInt, intArrayOf(123), mutable)
    bothNotEquals(intString, arrInt)

    var intList = constructR<Int, List<Int>>(emptyList(), mutable)
    var stringList = constructR<Int, List<String>>(emptyList(), mutable)
    bothEquals(intList, stringList)

    intList = setRight(intList, listOf(1), mutable)
    bothNotEquals(intList, stringList)

    intList = setLeft(intList, 1, mutable)
    stringList = setLeft(stringList, 1, mutable)
    bothEquals(intList, stringList)

    // nullable
    val nullable1 = MutableEither<Int?, String>(null)
    val nullable2 = MutableEither<List<Int>, List<Boolean>?>(null)
    bothEquals(nullable1, nullable2)

    nullable1.left = 5
    bothNotEquals(nullable1, nullable2)

    // nested
    intString = setRight(intString, "123", mutable)
    var subnested = constructL<String, Int>("123", mutable)
    var nested = constructR<List<Int>, Either<String, Int>>(subnested, mutable)
    bothEquals(nested, intString)

    subnested = setRight(subnested, 12, mutable)
    nested = setRight(nested, subnested, mutable)
    bothNotEquals(nested, intString)

    intString = setLeft(intString, 12, mutable)
    bothEquals(nested, intString)
}
