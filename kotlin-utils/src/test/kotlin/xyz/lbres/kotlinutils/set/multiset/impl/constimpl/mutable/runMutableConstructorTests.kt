package xyz.lbres.kotlinutils.set.multiset.impl.constimpl.mutable

import xyz.lbres.kotlinutils.set.multiset.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.ConstMutableMultiSetImpl
import kotlin.test.assertEquals

fun runMutableConstConstructorTests() {
    var set: ConstMutableMultiSet<Int> = ConstMutableMultiSetImpl(emptyList())
    var expectedSize = 0
    var expectedDistinct = emptySet<Int>()
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    set = ConstMutableMultiSetImpl(setOf(-12))
    expectedSize = 1
    expectedDistinct = setOf(-12)
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    set = ConstMutableMultiSetImpl(listOf(10, 10, 10, 10))
    expectedSize = 4
    expectedDistinct = setOf(10)
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    set = ConstMutableMultiSetImpl(listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25))
    expectedSize = 10
    expectedDistinct = setOf(-12, 18, 4, 10000, 25, -1, 0, 5)
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    val e1 = ArithmeticException()
    val e2 = NullPointerException()
    val e3 = ArithmeticException()
    val errSet = ConstMutableMultiSetImpl(mutableSetOf(e1, e2, e3))
    expectedSize = 3
    val expectedErrDistinct = setOf(e1, e2, e3)
    assertEquals(expectedSize, errSet.size)
    assertEquals(expectedErrDistinct, errSet.distinctValues)

    val listSet = ConstMutableMultiSetImpl(listOf(listOf(1, 3, 4), listOf(55, 66, 77)))
    expectedSize = 2
    val expectedListDistinct = setOf(listOf(1, 3, 4), listOf(55, 66, 77))
    assertEquals(expectedSize, listSet.size)
    assertEquals(expectedListDistinct, listSet.distinctValues)

    val compListSet = ConstMutableMultiSetImpl(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    expectedSize = 3
    val expectedCompListDistinct = setOf(listOf(1, 2, 3), listOf("abc", "def"))
    assertEquals(expectedSize, compListSet.size)
    assertEquals(expectedCompListDistinct, compListSet.distinctValues)
}
