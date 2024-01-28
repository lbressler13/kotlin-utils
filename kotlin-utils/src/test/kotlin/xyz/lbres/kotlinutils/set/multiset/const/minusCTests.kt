package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.assertEquals
import kotlin.test.assertIs

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

@Suppress(Suppressions.UNCHECKED_CAST)
fun runMinusCTests(
    createSet: (Collection<*>) -> ConstMultiSet<*>,
    createOtherSet: (Collection<*>) -> ConstMultiSet<*>
) {
    val createIntSet: (Collection<Int>) -> ConstMultiSet<Int> = { createSet(it) as ConstMultiSet<Int> }
    val createStringSet: (Collection<String>) -> ConstMultiSet<String> = { createSet(it) as ConstMultiSet<String> }
    val createExceptionSet: (Collection<Exception>) -> ConstMultiSet<Exception> = { createSet(it) as ConstMultiSet<Exception> }
    val createCompListSet: (Collection<List<Comparable<*>>>) -> ConstMultiSet<List<Comparable<*>>> = { createSet(it) as ConstMultiSet<List<Comparable<*>>> }

    val createOtherIntSet: (Collection<Int>) -> ConstMultiSet<Int> = { createOtherSet(it) as ConstMultiSet<Int> }

    // empty
    var intSet1 = createIntSet(emptyList())
    var intSet2 = createIntSet(emptyList())
    assertEmpty(intSet1 minusC intSet2)
    assertEmpty(intSet2 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 minusC intSet2)
    assertEmpty(intSet2 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    // equal
    intSet1 = createIntSet(listOf(1, 2, 3, 4, 5))
    assertEmpty(intSet1 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    var listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf(456, 789)))
    assertEmpty(listSet1 minusC listSet1)
    assertIs<ConstMultiSet<List<Comparable<*>>>>(intSet1 minusC intSet2)

    var otherSet = createOtherIntSet(listOf(1, 2, 3, 4, 5))
    assertEmpty(intSet1 minusC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    // all overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 4, 4))
    intSet2 = createIntSet(listOf(1, 2, 2, 3, 4, 4))
    var expectedInt = constMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 minusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    expectedInt = constMultiSetOf(2)
    assertEquals(expectedInt, intSet2 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet2 minusC intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    intSet2 = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = constMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 minusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    expectedInt = constMultiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet2 minusC intSet1)

    // none overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8))
    intSet2 = createIntSet(listOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8))
    assertEquals(intSet1, intSet1 minusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    assertEquals(intSet2, intSet2 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet2 minusC intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createOtherIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = constMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 minusC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    val stringSet1 = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
    val stringSet2 = createStringSet(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
    assertEquals(stringSet1, stringSet1 minusC stringSet2)
    assertIs<ConstMultiSet<String>>(stringSet1 minusC stringSet2)

    assertEquals(stringSet2, stringSet2 minusC stringSet1)
    assertIs<ConstMultiSet<String>>(stringSet2 minusC stringSet1)

    // some overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 5, 5))
    intSet2 = createIntSet(listOf(1, 1, 5, 6, 6, 7))
    expectedInt = constMultiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 minusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    expectedInt = constMultiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 minusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet2 minusC intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = constMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 minusC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listSet2 = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    var expectedList: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedList, listSet1 minusC listSet2)
    assertIs<ConstMultiSet<List<Comparable<*>>>>(listSet1 minusC listSet2)

    expectedList = constMultiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(expectedList, listSet2 minusC listSet1)
    assertIs<ConstMultiSet<List<Comparable<*>>>>(listSet2 minusC listSet1)

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e1, e1))
    var expectedError: ConstMultiSet<Exception> = constMultiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 minusC errorSet2)
    assertIs<ConstMultiSet<Exception>>(errorSet1 minusC errorSet2)

    expectedError = constMultiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 minusC errorSet1)
    assertIs<ConstMultiSet<Exception>>(errorSet2 minusC errorSet1)
}
