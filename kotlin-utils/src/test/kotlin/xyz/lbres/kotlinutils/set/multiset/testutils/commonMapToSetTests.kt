package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertContains
import kotlin.test.assertEquals

private typealias GenericMapFn<S, T> = (set: MultiSet<S>, transform: (S) -> T) -> MultiSet<T>

private var modString = ""
private val modMap: (Int) -> String = {
    modString += "1"
    modString
}

private fun <S, T> runSingleTest(set: MultiSet<S>, expected: MultiSet<T>, const: Boolean, genericMap: GenericMapFn<*, *>, transform: (S) -> T) {
    val result = genericMap(set, transform)
    assertEquals(expected, result)
    assertEquals(const, result is ConstMultiSet<*>)
}

fun runCommonMapToSetTests(createSet: (Collection<*>) -> MultiSet<*>, const: Boolean, genericMap: GenericMapFn<*, *>) {
    runCommonTests(createSet, const, genericMap)
    val createIntSet = getCreateSet<Int>(createSet)

    modString = ""
    val intSet = createIntSet(listOf(1, 1, 2, 1, 3))
    val expectedString = multiSetOf("1", "11", "111", "1111", "11111")
    runSingleTest(intSet, expectedString, const, genericMap, modMap)
}

fun runCommonMapToSetConsistentTests(createSet: (Collection<*>) -> MultiSet<*>, const: Boolean, genericMap: GenericMapFn<*, *>) {
    runCommonTests(createSet, const, genericMap)
    val createIntSet = getCreateSet<Int>(createSet)

    modString = ""
    val intSet = createIntSet(listOf(1, 1, 2, 1, 3))
    val resultOptions = listOf(
        multiSetOf("1", "1", "1", "11", "111"),
        multiSetOf("1", "11", "11", "11", "111"),
        multiSetOf("1", "11", "111", "111", "111")
    )
    val result = genericMap(intSet, modMap)
    assertContains(resultOptions, result)
    assertEquals(const, result is ConstMultiSet<*>)
}

private fun runCommonTests(createSet: (Collection<*>) -> MultiSet<*>, const: Boolean, genericMap: GenericMapFn<*, *>) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)

    var intSet = createIntSet(emptyList())
    var expectedInt = emptyMultiSet<Int>()
    runSingleTest(intSet, expectedInt, const, genericMap) { it * 2 }

    intSet = createIntSet(listOf(1, 1, 5, 7, -3, 0, 2, 5))
    expectedInt = multiSetOf(-3, 0, 1, 1, 2, 5, 5, 7)
    runSingleTest(intSet, expectedInt, const, genericMap) { it }

    expectedInt = multiSetOf(-6, 0, 2, 2, 4, 10, 10, 14)
    runSingleTest(intSet, expectedInt, const, genericMap) { it * 2 }

    var expectedString = multiSetOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    runSingleTest(intSet, expectedString, const, genericMap) { (it - 1).toString() }

    var stringSet = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong"))
    expectedString = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    val helloWorldMap: (String) -> String = {
        when (it) {
            "hello", "hi" -> "greetings"
            "world" -> "planet"
            "goodbye" -> "farewell"
            else -> "leave this planet"
        }
    }
    runSingleTest(stringSet, expectedString, const, genericMap, helloWorldMap)

    var prefix = ""
    stringSet = createStringSet(listOf("1", "2", "3", "4", "5"))
    expectedString = multiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        prefix += "1"
        "$prefix$it"
    }
    runSingleTest(stringSet, expectedString, const, genericMap, addingMap)

    expectedString = multiSetOf("", "", "", "", "")
    runSingleTest(stringSet, expectedString, const, genericMap) { "" }

    stringSet = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong"))
    expectedInt = multiSetOf(1, 1, 1, 2, 2, 3, 3, 3)
    runSingleTest(stringSet, expectedInt, const, genericMap) { stringSet.getCountOf(it) }

    val e1 = NullPointerException("Cannot invoke method on null value")
    val e2 = ArithmeticException()
    val e3 = ClassCastException("Cannot cast Int to List")
    val errorSet = createExceptionSet(listOf(e1, e2, e3))
    val expectedStringNull = multiSetOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    runSingleTest(errorSet, expectedStringNull, const, genericMap) { it.message }

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(4, 5, 6), emptyList(), listOf(7), listOf(7), listOf(7)))
    val expectedList = multiSetOf(emptyList(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    runSingleTest(listSet, expectedList, const, genericMap) {
        simpleIf(it.size > 1, { it.copyWithoutLast() }, { it })
    }
}
