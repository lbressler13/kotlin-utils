package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.list.StringList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.*
import kotlin.test.assertEquals

fun runAddTests() {
    runMultiSetAddTests(
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
    )

    // mutable elements
    val listSet: MutableMultiSet<List<String>> = mutableMultiSetOf(listOf("hello", "world"), listOf("farewell", "goodbye"), listOf("goodbye"))
    val mutableList = mutableListOf("goodbye")
    var listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(mutableList) }

    mutableList.clear()
    listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), emptyList())
    assertEquals(listExpected, listSet)
}

fun runAddAllTests() {
    runMultiSetAddAllTests { MutableMultiSetImpl(it) }

    // mutable elements
    val mutableList1 = mutableListOf("goodbye")
    val mutableList2 = mutableListOf("hello", "world")
    val listSet: MutableMultiSet<StringList> = mutableMultiSetOf(mutableList1, listOf("goodbye"))
    val listOther: MutableMultiSet<StringList> = mutableMultiSetOf(listOf("farewell", "goodbye"), mutableList2)
    var listExpected: MutableMultiSet<StringList> = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))

    runSingleMutateTest(listSet, listExpected, true) { listSet.addAll(listOther) }

    mutableList1.clear()
    listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), emptyList())
    assertEquals(listExpected, listSet)
}

fun runRemoveTests() {
    runMultiSetRemoveTests { MutableMultiSetImpl(it) }

    // mutable elements
    val mutableList = mutableListOf("goodbye")
    var listSet = mutableMultiSetOf(mutableList, listOf("hello world"))
    var listExpected = mutableMultiSetOf(listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.remove(listOf("goodbye")) }

    listSet = mutableMultiSetOf(mutableList, listOf("hello world"))
    mutableList.clear()
    listExpected = mutableMultiSetOf(emptyList(), listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, false) { listSet.remove(listOf("goodbye")) }

    listExpected = mutableMultiSetOf(listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.remove(emptyList()) }
}

fun runRemoveAllTests() {
    runMultiSetRemoveAllTests { MutableMultiSetImpl(it) }

    // mutable elements
    val mutableList = mutableListOf("goodbye")
    var listSet = mutableMultiSetOf(mutableList, listOf("hello world"), listOf("goodbye"))
    var listExpected = mutableMultiSetOf(listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.removeAll(listOf(listOf("goodbye"), listOf("goodbye"))) }

    listSet = mutableMultiSetOf(mutableList, listOf("hello world"))
    mutableList.clear()
    listExpected = mutableMultiSetOf(emptyList(), listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, false) { listSet.removeAll(listOf(listOf("goodbye"))) }

    listExpected = mutableMultiSetOf(emptyList(), listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, false) { listSet.removeAll(listOf(listOf("123"))) }

    mutableList.add("123")
    listExpected = mutableMultiSetOf(listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.removeAll(listOf(listOf("123"))) }
}

fun runRetainAllTests() {
    runMultiSetRetainAllTests { MutableMultiSetImpl(it) }

    // mutable elements
    val mutableList = mutableListOf(1, 4)
    var listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
    var listExpected = mutableMultiSetOf(listOf(1, 4))
    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(listOf(1, 4))) }

    listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
    mutableList.clear()
    listExpected = mutableMultiSetOf()
    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(listOf(1, 4))) }

    listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
    listExpected = mutableMultiSetOf(emptyList())
    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(emptyList())) }
}

fun runClearTests() {
    runMultiSetClearTests { ints -> MutableMultiSetImpl(ints) }
}
