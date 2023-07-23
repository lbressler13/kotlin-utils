package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import kotlin.test.assertEquals

fun runMutableConstructorTests() {
    var set: MutableMultiSet<Int> = MutableMultiSetImpl(emptyList())
    var expectedSize = 0
    var expectedDistinct = emptySet<Int>()
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    set = MutableMultiSetImpl(setOf(-12))
    expectedSize = 1
    expectedDistinct = setOf(-12)
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    set = MutableMultiSetImpl(mutableListOf(10, 10, 10, 10))
    expectedSize = 4
    expectedDistinct = setOf(10)
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    set = MutableMultiSetImpl(listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25))
    expectedSize = 10
    expectedDistinct = setOf(-12, 18, 4, 10000, 25, -1, 0, 5)
    assertEquals(expectedSize, set.size)
    assertEquals(expectedDistinct, set.distinctValues)

    val e1 = ArithmeticException()
    val e2 = NullPointerException()
    val e3 = ArithmeticException()
    val errSet = MutableMultiSetImpl(mutableSetOf(e1, e2, e3))
    expectedSize = 3
    val expectedErrDistinct = setOf(e1, e2, e3)
    assertEquals(expectedSize, errSet.size)
    assertEquals(expectedErrDistinct, errSet.distinctValues)

    val listSet = MutableMultiSetImpl(listOf(listOf(1, 3, 4), listOf(55, 66, 77)))
    expectedSize = 2
    val expectedListDistinct = setOf(listOf(1, 3, 4), listOf(55, 66, 77))
    assertEquals(expectedSize, listSet.size)
    assertEquals(expectedListDistinct, listSet.distinctValues)

    val compListSet = MutableMultiSetImpl(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    expectedSize = 3
    val expectedCompListDistinct = setOf(listOf(1, 2, 3), listOf("abc", "def"))
    assertEquals(expectedSize, compListSet.size)
    assertEquals(expectedCompListDistinct, compListSet.distinctValues)
}
