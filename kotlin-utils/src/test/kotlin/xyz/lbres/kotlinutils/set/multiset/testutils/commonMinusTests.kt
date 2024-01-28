package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals
import kotlin.test.assertIsNot

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

@Suppress(Suppressions.UNCHECKED_CAST)
fun runMinusTests(
    createSet: (Collection<*>) -> MultiSet<*>,
    createOtherSet: (Collection<*>) -> MultiSet<*>
) {
    val createIntSet: (Collection<Int>) -> MultiSet<Int> = { createSet(it) as MultiSet<Int> }
    val createStringSet: (Collection<String>) -> MultiSet<String> = { createSet(it) as MultiSet<String> }
    val createExceptionSet: (Collection<Exception>) -> MultiSet<Exception> = { createSet(it) as MultiSet<Exception> }
    val createCompListSet: (Collection<List<Comparable<*>>>) -> MultiSet<List<Comparable<*>>> = { createSet(it) as MultiSet<List<Comparable<*>>> }

    val createOtherIntSet: (Collection<Int>) -> MultiSet<Int> = { createOtherSet(it) as MultiSet<Int> }

    // empty
    var intSet1 = createIntSet(emptyList())
    var intSet2 = createIntSet(emptyList())
    assertEmpty(intSet1 - intSet2)
    assertEmpty(intSet2 - intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 - intSet2)
    assertEmpty(intSet2 - intSet1)

    // equal
    intSet1 = createIntSet(listOf(1, 2, 3, 4, 5))
    assertEmpty(intSet1 - intSet1)

    var listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf(456, 789)))
    assertEmpty(listSet1 - listSet1)

    var otherSet = createOtherIntSet(listOf(1, 2, 3, 4, 5))
    assertEmpty(intSet1 - otherSet)

    // all overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 4, 4))
    intSet2 = createIntSet(listOf(1, 2, 2, 3, 4, 4))
    var expectedInt = multiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = multiSetOf(2)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    intSet2 = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = multiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    // none overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8))
    intSet2 = createIntSet(listOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8))
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(intSet2, intSet2 - intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createOtherIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    val stringSet1 = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
    val stringSet2 = createStringSet(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
    assertEquals(stringSet1, stringSet1 - stringSet2)
    assertEquals(stringSet2, stringSet2 - stringSet1)

    // some overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 5, 5))
    intSet2 = createIntSet(listOf(1, 1, 5, 6, 6, 7))
    expectedInt = multiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = multiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listSet2 = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    var expectedList: MultiSet<List<Comparable<*>>> = multiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = multiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(expectedList, listSet2 - listSet1)

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e1, e1))
    var expectedError: MultiSet<Exception> = multiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 - errorSet2)
    expectedError = multiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 - errorSet1)
}

fun runMutableElementMinusTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    val listSet2: MultiSet<IntList> = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 7)))

    assertEmpty(listSet1 - listSet2)
    assertEmpty(listSet2 - listSet1)

    mutableList2.add(3)
    var expectedList = createIntListSet(listOf(listOf(0, 5, 7, 3)))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = createIntListSet(listOf(listOf(0, 5, 7)))
    assertEquals(expectedList, listSet2 - listSet1)
}
