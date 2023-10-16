package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runIsEmptyTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createStringSet: (Collection<String>) -> MultiSet<String>
) {
    // empty
    var intSet: MultiSet<Int> = createIntSet(emptyList())
    assertTrue(intSet.isEmpty())

    var stringSet: MultiSet<String> = createStringSet(emptyList())
    assertTrue(stringSet.isEmpty())

    // not empty
    intSet = createIntSet(listOf(0))
    assertFalse(intSet.isEmpty())

    intSet = createIntSet(listOf(1000, -1000, 4, 2, 4))
    assertFalse(intSet.isEmpty())

    intSet = createIntSet(listOf(3, 3, 3))
    assertFalse(intSet.isEmpty())

    stringSet = createStringSet(listOf("123", "abc"))
    assertFalse(stringSet.isEmpty())

    stringSet = createStringSet(listOf("hello world", "hello world"))
    assertFalse(stringSet.isEmpty())
}

fun runGetCountOfTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>
) {
    var set: MultiSet<Int> = createIntSet(emptyList())
    assertEquals(0, set.getCountOf(0))
    assertEquals(0, set.getCountOf(100))

    set = createIntSet(listOf(2))
    assertEquals(1, set.getCountOf(2))
    assertEquals(0, set.getCountOf(1))

    set = createIntSet(listOf(1, 1, 2, 1, -4, 5, 2))
    assertEquals(3, set.getCountOf(1))
    assertEquals(2, set.getCountOf(2))
    assertEquals(1, set.getCountOf(-4))
    assertEquals(1, set.getCountOf(5))

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3)))
    assertEquals(2, listSet.getCountOf(listOf(1, 2, 3)))
    assertEquals(0, listSet.getCountOf(listOf(1, 2)))
}

fun runMutableIsEmptyTests(
    createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>,
    createMutableStringSet: (Collection<String>) -> MutableMultiSet<String>
) {
    runIsEmptyTests(createMutableIntSet, createMutableStringSet)

    var intSet = createMutableIntSet(emptyList())
    intSet.remove(1)
    assertTrue(intSet.isEmpty())

    intSet = createMutableIntSet(listOf(1, 1))
    intSet.remove(1)
    assertFalse(intSet.isEmpty())
    intSet.remove(1)
    assertTrue(intSet.isEmpty())
    intSet.add(1)
    assertFalse(intSet.isEmpty())

    intSet = createMutableIntSet(listOf(2, 3))
    intSet.remove(3)
    assertFalse(intSet.isEmpty())
    intSet.remove(2)
    assertTrue(intSet.isEmpty())

    intSet = createMutableIntSet(listOf(2, 3))
    intSet.clear()
    assertTrue(intSet.isEmpty())
}

fun runMutableGetCountOfTests(
    createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>,
    createMutableIntListSet: (Collection<IntList>) -> MutableMultiSet<IntList>
) {
    runGetCountOfTests(createMutableIntSet, createMutableIntListSet)

    val set = createMutableIntSet(listOf(1, 1, 2, 1, -4, 5, 2))
    set.add(2)
    assertEquals(3, set.getCountOf(2))
    set.remove(5)
    assertEquals(0, set.getCountOf(5))
}

fun runMutableElementGetCountOfTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))

    var listValue = listOf(1, 2, 3)
    var expected = 2
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 0
    assertEquals(expected, listSet.getCountOf(listValue))

    mutableList1.remove(3)

    listValue = listOf(1, 2, 3)
    expected = 1
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 1
    assertEquals(expected, listSet.getCountOf(listValue))
}
