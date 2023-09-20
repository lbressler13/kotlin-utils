package xyz.lbres.kotlinutils.set.multiset.manager

import kotlin.test.assertEquals

fun runConstructorTests() {
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    assertEquals(0, manager.size)
    assertEquals(emptySet(), manager.distinctValues)

    manager = ConstMultiSetManager(emptyList(), true)
    assertEquals(0, manager.size)
    assertEquals(emptySet(), manager.distinctValues)

    manager = ConstMultiSetManager(setOf(-12), false)
    assertEquals(1, manager.size)
    assertEquals(setOf(-12), manager.distinctValues)

    manager = ConstMultiSetManager(listOf(10, 10, 10, 10), false)
    assertEquals(4, manager.size)
    assertEquals(setOf(10), manager.distinctValues)

    manager = ConstMultiSetManager(listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25), true)
    val expectedDistinct = setOf(-12, 18, 4, 10000, 25, -1, 0, 5)
    assertEquals(10, manager.size)
    assertEquals(expectedDistinct, manager.distinctValues)

    val e1 = ArithmeticException()
    val e2 = NullPointerException()
    val e3 = ArithmeticException()

    val errManager = ConstMultiSetManager(listOf(e1, e2, e3), true)
    assertEquals(3, errManager.size)
    assertEquals(setOf(e1, e2, e3), errManager.distinctValues)

    val listManager = ConstMultiSetManager(listOf(listOf(1, 3, 4), listOf(55, 66, 77)), true)
    val expectedListDistinct = setOf(listOf(1, 3, 4), listOf(55, 66, 77))
    assertEquals(2, listManager.size)
    assertEquals(expectedListDistinct, listManager.distinctValues)

    val compListManager = ConstMultiSetManager(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")), false)
    val expectedCompListDistinct = setOf(listOf(1, 2, 3), listOf("abc", "def"))
    assertEquals(3, compListManager.size)
    assertEquals(expectedCompListDistinct, compListManager.distinctValues)
}
