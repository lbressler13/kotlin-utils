package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.assertEmpty
import kotlin.test.assertEquals
import kotlin.test.assertIs

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runPlusConstTests(
    createIntSet: (Collection<Int>) -> ConstMultiSet<Int>,
    createStringSet: (Collection<String>) -> ConstMultiSet<String>,
    createExceptionSet: (Collection<Exception>) -> ConstMultiSet<Exception>,
    createCompListSet: (Collection<List<Comparable<*>>>) -> ConstMultiSet<List<Comparable<*>>>,
    createOtherIntSet: (Collection<Int>) -> ConstMultiSet<Int>
) {
    // empty
    var intSet1: ConstMultiSet<Int> = createIntSet(emptyList())
    var intSet2: ConstMultiSet<Int> = createIntSet(emptyList())
    assertEmpty(intSet1 plusC intSet2)
    assertEmpty(intSet2 plusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 plusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    // non-empty
    intSet1 = createIntSet(listOf(1))
    intSet2 = createIntSet(listOf(1))
    var intExpected = constMultiSetOf(1, 1)
    assertEquals(intExpected, intSet1.plusConst(intSet2)) // test using plusConst method
    assertIs<ConstMultiSet<Int>>(intSet1.plusConst(intSet2))

    intSet1 = createIntSet(listOf(1, 2, 2, 3, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 0))
    intExpected = constMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(intExpected, intSet1 plusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    val otherSet = createOtherIntSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(intExpected, intSet2 plusC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet2 plusC otherSet)

    val stringSet1 = createStringSet(listOf("", "hello", "world", "goodbye"))
    val stringSet2 = createStringSet(listOf("hi", "no", "bye"))
    val stringExpected = constMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(stringExpected, stringSet1 plusC stringSet2)
    assertEquals(stringExpected, stringSet2 plusC stringSet1)
    assertIs<ConstMultiSet<String>>(stringSet1 plusC stringSet2)

    val listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listSet2 = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    val expectedList: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedList, listSet1 plusC listSet2)
    assertEquals(expectedList, listSet2 plusC listSet1)
    assertIs<ConstMultiSet<List<Comparable<*>>>>(listSet1 plusC listSet2)

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 plusC errorSet2)
    assertEquals(expectedError, errorSet2 plusC errorSet1)
    assertIs<ConstMultiSet<Exception>>(errorSet1.plusConst(errorSet2))
}
