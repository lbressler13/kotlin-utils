package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertContains
import kotlin.test.assertEquals

fun runToStringTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>
) {
    var set: MultiSet<Int> = createIntSet(emptyList())
    var expected = "[]"
    assertEquals(expected, set.toString())

    set = createIntSet(listOf(1, 1, 1, 1))
    expected = "[1, 1, 1, 1]"
    assertEquals(expected, set.toString())

    set = createIntSet(listOf(2, 4, 2, 1))
    var options = setOf(
        "[1, 2, 2, 4]", "[1, 2, 4, 2]", "[1, 4, 2, 2]",
        "[2, 1, 2, 4]", "[2, 1, 4, 2]", "[2, 2, 1, 4]",
        "[2, 2, 4, 1]", "[2, 4, 1, 2]", "[2, 4, 2, 1]",
        "[4, 1, 2, 2]", "[4, 2, 1, 2]", "[4, 2, 2, 1]"
    )
    assertContains(options, set.toString())

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 7)))
    options = setOf("[[1, 2, 3], [0, 5, 7]]", "[[0, 5, 7], [1, 2, 3]]")
    assertContains(options, listSet.toString())
}

fun runMutableToStringTests(
    createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>,
    createMutableIntListSet: (Collection<IntList>) -> MutableMultiSet<IntList>
) {
    runToStringTests(createMutableIntSet, createMutableIntListSet)

    val set = createMutableIntSet(listOf(2, 2, 4))
    var options = setOf("[2, 2, 4]", "[2, 4, 2]", "[4, 2, 2]")
    assertContains(options, set.toString())

    set.add(1)
    options = setOf(
        "[1, 2, 2, 4]", "[1, 2, 4, 2]", "[1, 4, 2, 2]",
        "[2, 1, 2, 4]", "[2, 1, 4, 2]", "[2, 2, 1, 4]",
        "[2, 2, 4, 1]", "[2, 4, 1, 2]", "[2, 4, 2, 1]",
        "[4, 1, 2, 2]", "[4, 2, 1, 2]", "[4, 2, 2, 1]"
    )
    assertContains(options, set.toString())

    set.remove(2)
    options = setOf(
        "[1, 2, 4]", "[1, 4, 2]", "[2, 1, 4]", "[2, 4, 1]", "[4, 1, 2]", "[4, 2, 1]"
    )
    assertContains(options, set.toString())
}

fun runMutableElementToStringTests(createIntListSet: (Collection<IntList>) -> MultiSet<IntList>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val set = createIntListSet(listOf(mutableList1, mutableList2))

    var options = setOf("[[1, 2, 3], [0, 5, 7]]", "[[0, 5, 7], [1, 2, 3]]")
    assertContains(options, set.toString())

    mutableList2.clear()
    options = setOf("[[1, 2, 3], []]", "[[], [1, 2, 3]]")
    assertContains(options, set.toString())
}
