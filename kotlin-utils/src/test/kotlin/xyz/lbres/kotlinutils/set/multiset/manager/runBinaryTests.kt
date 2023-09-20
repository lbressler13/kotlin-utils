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
    runSingleBinaryOpTest(stringManager, listOf("hello", "world", "goodbye")) { it - multiSetOf("hello world", "farewell", "planet") }

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

// fun runPlusTests() {
//    // empty
//    var intSet1 = constMultiSetOf<Int>()
//    var intSet2 = constMultiSetOf<Int>()
//    assertEquals(constMultiSetOf(), intSet1 + intSet2)
//    assertEquals(constMultiSetOf(), intSet2 + intSet1)
//
//    intSet1 = constMutableMultiSetOf(1, 2, 3, 3)
//    assertEquals(intSet1, intSet1 + intSet2)
//
//    // non-empty
//    intSet1 = constMutableMultiSetOf(1)
//    intSet2 = constMutableMultiSetOf(1)
//    var expected = constMutableMultiSetOf(1, 1)
//    assertEquals(expected, intSet1 + intSet2)
//
//    intSet1 = constMutableMultiSetOf(1, 2, 2, 3, 3, 3)
//    intSet2 = constMutableMultiSetOf(1, 2, 0)
//    expected = constMutableMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
//    assertEquals(expected, intSet1 + intSet2)
//
//    val otherSet = TestMutableMultiSet(listOf(1, 2, 2, 3, 3, 3))
//    assertEquals(expected, intSet2 + otherSet)
//
//    val sms1 = constMutableMultiSetOf("", "hello", "world", "goodbye")
//    val sms2 = constMutableMultiSetOf("hi", "no", "bye")
//    val sExpected = constMutableMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
//    assertEquals(sExpected, sms1 + sms2)
//    assertEquals(sExpected, sms2 + sms1)
//
//    val listSet1 = constMutableMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
//    val listSet2 = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3))
//    val expectedList = constMutableMultiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
//    assertEquals(expectedList, listSet1 + listSet2)
//    assertEquals(expectedList, listSet2 + listSet1)
//
//    val errorSet1: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e2, e1, e2)
//    val errorSet2: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e3, e3, e2, e1, e1)
//    val expectedError: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
//    assertEquals(expectedError, errorSet1 + errorSet2)
//    assertEquals(expectedError, errorSet2 + errorSet1)
//
//    val compListMs1: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
//    val compListMs2: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
//    val compListExpected: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
//    assertEquals(compListExpected, compListMs1 + compListMs2)
//    assertEquals(compListExpected, compListMs2 + compListMs1)
//
//    // with immutable
//    intSet1 = constMutableMultiSetOf(1, 2, 3)
//    val immutable = multiSetOf(1, 4, 5)
//    expected = constMutableMultiSetOf(1, 1, 2, 3, 4, 5)
//    assertEquals(expected, intSet1 + immutable)
// }
//
// fun runIntersectTests() {
//    // empty
//    var intSet1 = constMultiSetOf<Int>()
//
//    var intSet2 = constMultiSetOf<Int>()
//    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)
//
//    intSet2 = constMutableMultiSetOf(1, 2, 3)
//    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)
//    assertEquals<MultiSet<Int>>(constMultiSetOf(), intSet2 intersect intSet1)
//
//    // none shared
//    intSet1 = constMutableMultiSetOf(1, 2, 3)
//    intSet2 = constMutableMultiSetOf(4, 5, 6, 7, 8)
//    assertEquals<MultiSet<Int>>(constMultiSetOf(), intSet1 intersect intSet2)
//    assertEquals<MultiSet<Int>>(constMultiSetOf(), intSet2 intersect intSet1)
//
//    var otherSet = TestMutableMultiSet(listOf(4, 5, 6, 7, 8))
//    assertEquals<MultiSet<Int>>(constMultiSetOf(), intSet1 intersect otherSet)
//
//    var listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
//    var listSet2 = constMutableMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
//    assertEquals<MultiSet<IntList>>(constMultiSetOf(), listSet1 intersect listSet2)
//    assertEquals<MultiSet<IntList>>(constMultiSetOf(), listSet2 intersect listSet1)
//
//    // all shared
//    intSet1 = constMutableMultiSetOf(1, 2, 3)
//    intSet2 = constMutableMultiSetOf(1, 2, 3)
//    var expectedInt = constMutableMultiSetOf(1, 2, 3)
//    assertEquals(expectedInt, intSet1 intersect intSet2)
//    assertEquals(expectedInt, intSet2 intersect intSet1)
//
//    intSet1 = constMutableMultiSetOf(1, 1, 2, 2, 3, 3)
//    intSet2 = constMutableMultiSetOf(1, 2, 2, 2, 3)
//    expectedInt = constMutableMultiSetOf(1, 2, 2, 3)
//    assertEquals(expectedInt, intSet1 intersect intSet2)
//    assertEquals(expectedInt, intSet2 intersect intSet1)
//
//    // some shared
//    intSet1 = constMutableMultiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
//    intSet2 = constMutableMultiSetOf(-1, 14, 3, 9, 9, 6)
//    expectedInt = constMutableMultiSetOf(-1, 6)
//    assertEquals(expectedInt, intSet1 intersect intSet2)
//    assertEquals(expectedInt, intSet2 intersect intSet1)
//
//    otherSet = TestMutableMultiSet(listOf(-1, 14, 3, 9, 9, 6))
//    expectedInt = constMutableMultiSetOf(-1, 6)
//    assertEquals(expectedInt, intSet1 intersect otherSet)
//
//    listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
//    listSet2 = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3))
//    val expectedList = constMutableMultiSetOf(listOf(1, 2, 3))
//    assertEquals(expectedList, listSet1 intersect listSet2)
//    assertEquals(expectedList, listSet2 intersect listSet1)
//
//    val errorSet1: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e2, e1, e2)
//    val errorSet2: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e3, e3, e2, e1, e1)
//    val expectedError: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e1, e2)
//    assertEquals(expectedError, errorSet1 intersect errorSet2)
//    assertEquals(expectedError, errorSet2 intersect errorSet1)
//
//    // with immutable
//    intSet1 = constMutableMultiSetOf(1, 2, 3, 4)
//    val immutable = multiSetOf(1, 4, 4, 5)
//    expectedInt = constMutableMultiSetOf(1, 4)
//    assertEquals(expectedInt, intSet1 intersect immutable)
// }

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
