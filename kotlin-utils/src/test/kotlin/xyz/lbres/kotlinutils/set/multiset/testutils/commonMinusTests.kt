package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.CompList
import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

@Suppress(Suppressions.UNCHECKED_CAST)
private val genericMinus: MultiSetOp<*> = { set1: MultiSet<Any>, set2: MultiSet<Any> -> set1 - set2 } as MultiSetOp<*>

@Suppress(Suppressions.UNCHECKED_CAST)
private val genericConstMinus: MultiSetOp<*> = { set1: MultiSet<Any>, set2: MultiSet<Any> ->
    set1 as ConstMultiSet<Any>
    set2 as ConstMultiSet<Any>
    set1 `-c` set2
} as MultiSetOp<*>

fun runMinusTests(createSet: (Collection<*>) -> MultiSet<*>, createOtherSet: (Collection<*>) -> MultiSet<*>, const: Boolean) {
    val minusFn: MultiSetOp<*> = simpleIf(const, genericConstMinus, genericMinus)
    val createIntSet = getCreateSet<Int>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)
    val createCompListSet = getCreateSet<CompList>(createSet)
    val createOtherIntSet = getCreateSet<Int>(createOtherSet)

    // empty
    var intSet1 = createIntSet(emptyList())
    var intSet2 = createIntSet(emptyList())
    assertEmpty(minusFn(intSet1, intSet2))
    assertEmpty(minusFn(intSet2, intSet1))

    assertEquals(const, minusFn(intSet1, intSet2) is ConstMultiSet<*>)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, minusFn(intSet1, intSet2))
    assertEmpty(minusFn(intSet2, intSet1))
    assertEquals(const, minusFn(intSet2, intSet1) is ConstMultiSet<*>)

    // equal
    intSet1 = createIntSet(listOf(1, 2, 3, 4, 5))
    assertEmpty(minusFn(intSet1, intSet1))

    var listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf(456, 789)))
    assertEmpty(minusFn(listSet1, listSet1))

    var otherSet = createOtherIntSet(listOf(1, 2, 3, 4, 5))
    assertEmpty(minusFn(intSet1, otherSet))

    // all overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 4, 4))
    intSet2 = createIntSet(listOf(1, 2, 2, 3, 4, 4))
    var expectedInt = multiSetOf(1, 4)
    assertEquals(expectedInt, minusFn(intSet1, intSet2))
    expectedInt = multiSetOf(2)
    assertEquals(expectedInt, minusFn(intSet2, intSet1))

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    intSet2 = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, minusFn(intSet1, intSet2))
    expectedInt = multiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, minusFn(intSet2, intSet1))

    // no overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8))
    intSet2 = createIntSet(listOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8))
    assertEquals(intSet1, minusFn(intSet1, intSet2))
    assertEquals(intSet2, minusFn(intSet2, intSet1))

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createOtherIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, minusFn(intSet1, otherSet))

    val stringSet1 = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
    val stringSet2 = createStringSet(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
    assertEquals(stringSet1, minusFn(stringSet1, stringSet2))
    assertEquals(stringSet2, minusFn(stringSet2, stringSet1))

    // some overlapping elements
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 5, 5))
    intSet2 = createIntSet(listOf(1, 1, 5, 6, 6, 7))
    expectedInt = multiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, minusFn(intSet1, intSet2))
    expectedInt = multiSetOf(6, 6, 7)
    assertEquals(expectedInt, minusFn(intSet2, intSet1))

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, minusFn(intSet1, otherSet))

    listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listSet2 = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    var expectedList: MultiSet<CompList> = multiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedList, minusFn(listSet1, listSet2))
    expectedList = multiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(expectedList, minusFn(listSet2, listSet1))

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e1, e1))
    var expectedError: MultiSet<Exception> = multiSetOf(e2, e2)
    assertEquals(expectedError, minusFn(errorSet1, errorSet2))
    expectedError = multiSetOf(e3, e3)
    assertEquals(expectedError, minusFn(errorSet2, errorSet1))
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
