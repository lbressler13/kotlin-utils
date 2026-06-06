package xyz.lbres.kotlinutils.utils.either

import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runTestEqualsValue(mutable: Boolean) {
    var intString = constructL<Int,String>(123, mutable)
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
