package xyz.lbres.kotlinutils.set.multiset.manager

import xyz.lbres.kotlinutils.set.multiset.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertIsNot

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runMinusTests() {
    // empty
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    runSingleBinaryOpTest(manager, emptyList()) { it - emptyMultiSet() }

    manager = ConstMultiSetManager(listOf(1, 2, 3, 4), true)
    runSingleBinaryOpTest(manager, listOf(1, 2, 3, 4)) { it - emptyMultiSet() }

    // equal
    var values = listOf(1, 2, 3, 4, 5)
    manager = ConstMultiSetManager(values, false)
    runSingleBinaryOpTest(manager, emptyList()) { it - MultiSetImpl(values) }

    val listValues = listOf(listOf(1, 2, 3), listOf(456, 789))
    var listManager = ConstMultiSetManager(listValues, true)
    runSingleBinaryOpTest(listManager, emptyList()) { it - MultiSetImpl(listValues) }

    // all shared
    values = listOf(1, 1, 2, 3, 4, 4, 4)
    var values2 = listOf(1, 2, 2, 3, 4, 4)

    manager = ConstMultiSetManager(values, true)
    runSingleBinaryOpTest(manager, listOf(1, 4)) { it - MultiSetImpl(values2) }
    manager = ConstMultiSetManager(values2, true)
    runSingleBinaryOpTest(manager, listOf(2)) { it - MultiSetImpl(values) }

    values = listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    values2 = listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)

    manager = ConstMultiSetManager(values, true)
    runSingleBinaryOpTest(manager, listOf(2, 2, 6)) { it - MultiSetImpl(values2) }
    manager = ConstMultiSetManager(values2, true)
    runSingleBinaryOpTest(manager, listOf(1, 5, 5, 7)) { it - MultiSetImpl(values) }

    // none shared
    values = listOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    values2 = listOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)

    manager = ConstMultiSetManager(values, true)
    runSingleBinaryOpTest(manager, values) { it - MultiSetImpl(values2) }
    manager = ConstMultiSetManager(values2, false)
    runSingleBinaryOpTest(manager, values2) { it - MultiSetImpl(values) }

    val stringManager = ConstMultiSetManager(listOf("hello", "world", "goodbye"), true)
    runSingleBinaryOpTest(stringManager, listOf("hello", "world", "goodbye")) {
        it - multiSetOf("hello world", "farewell", "planet")
    }

    // some shared
    values = listOf(1, 1, 2, 3, 4, 5, 5)
    values2 = listOf(1, 1, 5, 6, 6, 7)

    manager = ConstMultiSetManager(values, false)
    runSingleBinaryOpTest(manager, listOf(2, 3, 4, 5)) { manager - MultiSetImpl(values2) }
    manager = ConstMultiSetManager(values2, true)
    runSingleBinaryOpTest(manager, listOf(6, 6, 7)) { manager - MultiSetImpl(values) }

    listManager = ConstMultiSetManager(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)), true)
    val expectedList = listOf(listOf(1, 2, 3), listOf(2, 3, 4))
    runSingleBinaryOpTest(listManager, expectedList) { listManager - multiSetOf(emptyList(), listOf(1, 2, 3)) }

    val errorManager = ConstMultiSetManager(listOf(e1, e2, e1, e1, e2), false)
    runSingleBinaryOpTest(errorManager, listOf(e2, e2)) { it - multiSetOf(e1, e3, e3, e1, e1) }

    val compListValues = listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListValues2 = listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())

    var compListManager: ConstMultiSetManager<List<Comparable<*>>> = ConstMultiSetManager(compListValues, false)
    var compListExpected: List<List<Comparable<*>>> = listOf(listOf("abc", "def"), listOf("abc", "def"))
    runSingleBinaryOpTest(compListManager, compListExpected) { it - MultiSetImpl(compListValues2) }

    compListManager = ConstMultiSetManager(compListValues2, true)
    compListExpected = listOf(listOf(1, 2, 3), emptyList())
    runSingleBinaryOpTest(compListManager, compListExpected) { it - MultiSetImpl(compListValues) }
}

fun runPlusTests() {
    // empty
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    runSingleBinaryOpTest(manager, emptyList()) { it + emptyMultiSet() }

    manager = ConstMultiSetManager(listOf(1, 2, 3, 4), true)
    runSingleBinaryOpTest(manager, listOf(1, 2, 3, 4)) { it + emptyMultiSet() }

    // non-empty
    manager = ConstMultiSetManager(setOf(1), false)
    runSingleBinaryOpTest(manager, listOf(1, 1)) { it + multiSetOf(1) }

    val values = listOf(1, 2, 2, 3, 3, 3)
    manager = ConstMultiSetManager(values, true)
    val expected = listOf(1, 2, 2, 3, 3, 3, 1, 2, 0)
    runSingleBinaryOpTest(manager, expected) { it + multiSetOf(1, 2, 0) }

    val stringManager = ConstMultiSetManager(listOf("", "hello", "world"), false)
    val stringExpected = listOf("", "hello", "world", "hi", "bye")
    runSingleBinaryOpTest(stringManager, stringExpected) { it + multiSetOf("hi", "bye") }

    val listValues = listOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listManager = ConstMultiSetManager(listValues, true)
    val listExpected = listOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    runSingleBinaryOpTest(listManager, listExpected) { it + multiSetOf(emptyList(), listOf(1, 2, 3)) }

    val errorManager = ConstMultiSetManager(listOf(e1, e2, e1, e2), true)
    val errorExpected = listOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    runSingleBinaryOpTest(errorManager, errorExpected) { it + multiSetOf(e1, e3, e3, e2, e1, e1) }

    val compListValues = listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListManager = ConstMultiSetManager<List<Comparable<*>>>(compListValues, false)
    val compListExpected = listOf(
        listOf(1, 2, 3),
        listOf("abc", "def"),
        listOf("abc", "def"),
        listOf(1, 2, 3),
        listOf(1, 2, 3),
        emptyList()
    )
    runSingleBinaryOpTest(compListManager, compListExpected) {
        it + multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    }
}

fun runIntersectTests() {
    // empty
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    runSingleBinaryOpTest(manager, emptyList()) { it intersect emptyMultiSet() }

    manager = ConstMultiSetManager(listOf(1, 2, 3, 4), true)
    runSingleBinaryOpTest(manager, emptyList()) { it intersect emptyMultiSet() }

    manager = ConstMultiSetManager(emptyList(), true)
    runSingleBinaryOpTest(manager, emptyList()) { it intersect multiSetOf(1, 2, 3, 4) }

    // none shared
    manager = ConstMultiSetManager(listOf(1, 2, 3), false)
    runSingleBinaryOpTest(manager, emptyList()) { it intersect multiSetOf(4, 5, 6, 7, 8) }

    var listManager = ConstMultiSetManager(listOf(listOf(1, 2, 3), listOf(4, 5)), true)
    runSingleBinaryOpTest(listManager, emptyList()) { it intersect multiSetOf(listOf(1, 2), listOf(3, 4, 5)) }

    // all shared
    manager = ConstMultiSetManager(listOf(1, 2, 3), false)
    runSingleBinaryOpTest(manager, listOf(1, 2, 3)) { it intersect multiSetOf(1, 2, 3) }

    manager = ConstMultiSetManager(listOf(1, 1, 2, 2, 3, 3), false)
    runSingleBinaryOpTest(manager, listOf(1, 2, 2, 3)) { it intersect multiSetOf(1, 2, 2, 2, 3) }

    // some shared
    val values = listOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    manager = ConstMultiSetManager(values, true)
    runSingleBinaryOpTest(manager, listOf(-1, 6)) { it intersect multiSetOf(-1, 14, 3, 9, 9, 6) }

    val listValues = listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listManager = ConstMultiSetManager(listValues, false)
    runSingleBinaryOpTest(listManager, listOf(listOf(1, 2, 3))) { it intersect multiSetOf(emptyList(), listOf(1, 2, 3)) }

    val errorValues = listOf(e1, e3, e3, e2, e1, e1)
    val errorManager = ConstMultiSetManager(errorValues, true)
    runSingleBinaryOpTest(errorManager, listOf(e1, e1, e2)) { it intersect multiSetOf(e1, e2, e1, e2) }
}

private fun <T> runSingleBinaryOpTest(
    manager: ConstMultiSetManager<T>,
    expectedValues: List<T>,
    performOp: (ConstMultiSetManager<T>) -> MultiSet<T>
) {
    val result = performOp(manager)
    assertEquals(MultiSetImpl(expectedValues), result)
    assertIs<MultiSet<T>>(result)
    assertIsNot<ConstMultiSet<T>>(result)
}
