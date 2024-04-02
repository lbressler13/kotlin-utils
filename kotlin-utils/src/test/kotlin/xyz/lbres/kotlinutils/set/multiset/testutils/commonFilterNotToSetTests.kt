package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

private fun <S> runSingleTest(set: MultiSet<S>, expected: MultiSet<S>, const: Boolean, genericFilter: GenericFilterFn<*>, filterFn: (S) -> Boolean) {
    val result = genericFilter(set, filterFn)
    assertEquals(expected, result)
    assertEquals(const, result is ConstMultiSet<*>)
}

fun runCommonFilterNotToSetTests(createSet: (Collection<*>) -> MultiSet<*>, genericFilter: GenericFilterFn<*>, const: Boolean) {
    runCommonTests(createSet, genericFilter, const)
    val createIntSet = getCreateSet<Int>(createSet)

    val intSet = createIntSet(listOf(1, 1, 3, 2, 14, 14))
    val intOptions = listOf(multiSetOf(1, 1), multiSetOf(1, 3))
    var previousOdd = false
    val intPredicate: (Int) -> Boolean = {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    val result = genericFilter(intSet, intPredicate)
    assertContains(intOptions, result)
    assertEquals(const, result is ConstMultiSet<*>)
}

fun runCommonFilterNotToSetConsistentTests(createSet: (Collection<*>) -> MultiSet<*>, genericFilter: GenericFilterFn<*>, const: Boolean) {
    runCommonTests(createSet, genericFilter, const)
    val createIntSet = getCreateSet<Int>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)

    val intSet = createIntSet(listOf(1, 1, 3, 2, 14, 14))
    val intOptions = listOf(multiSetOf(1, 1), multiSetOf(3))
    var previousOdd = false
    val intPredicate: (Int) -> Boolean = {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    val intResult = genericFilter(intSet, intPredicate)
    assertContains(intOptions, intResult)
    assertEquals(const, intResult is ConstMultiSet<*>)

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 7), listOf(1, 2, 3)))

    var previous12 = false
    val listPredicate: (IntList) -> Boolean = {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            false
        } else {
            previous12 = true
            true
        }
    }
    val listExpected: MultiSet<IntList> = emptyMultiSet()
    runSingleTest(listSet, listExpected, const, genericFilter, listPredicate)
}

private fun runCommonTests(createSet: (Collection<*>) -> MultiSet<*>, genericFilter: GenericFilterFn<*>, const: Boolean) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)

    var intSet = createIntSet(emptyList())
    var intExpected = emptyMultiSet<Int>()
    runSingleTest(intSet, intExpected, const, genericFilter) { true }

    intSet = createIntSet(listOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0))
    intExpected = emptyMultiSet()
    runSingleTest(intSet, intExpected, const, genericFilter) { true }

    intExpected = createIntSet(listOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0))
    runSingleTest(intSet, intExpected, const, genericFilter) { false }

    intExpected = createIntSet(listOf(2, 3, 4, 6, -1))
    runSingleTest(intSet, intExpected, const, genericFilter) { intSet.getCountOf(it) > 1 }

    intExpected = createIntSet(listOf(2, 4, 5, 5, 0, 0))
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    runSingleTest(intSet, intExpected, const, genericFilter) { it in testInts }

    val stringSet = createStringSet(listOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world"))
    val stringExpected = multiSetOf("goodbye", "hi")
    runSingleTest(stringSet, stringExpected, const, genericFilter) { it.length in 3..5 }

    val errorSet = createExceptionSet(listOf(e1, e1, e2, e3, e4, e4))
    val errorExpected: MultiSet<Exception> = multiSetOf(e1, e1, e2)
    runSingleTest(errorSet, errorExpected, const, genericFilter) { it is ClassCastException }

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 6)))
    val listExpected = multiSetOf(listOf(1, 2, 3))
    runSingleTest(listSet, listExpected, const, genericFilter) { it.contains(0) }
}
