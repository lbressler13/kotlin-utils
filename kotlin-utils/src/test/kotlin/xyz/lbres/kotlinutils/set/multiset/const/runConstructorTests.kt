package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports

@Suppress("UNCHECKED_CAST")
fun runConstConstructorTests() {
    fun <T> testConstructor(map: Map<String, Any>) {
        val values: Collection<T> = map["values"] as Collection<T>
        val set: ConstMultiSet<T> = ConstMultiSetImpl(values)
        testConstructedMultiSet(set, map)
    }

    multiSetConstructorIntTestValues.forEach { testConstructor<Int>(it) }
    multiSetConstructorExceptionTestValues.forEach { testConstructor<Exception>(it) }
    multiSetConstructorIntListTestValues.forEach { testConstructor<IntList>(it) }
    multiSetConstructorCompListTestValues.forEach { testConstructor<List<Comparable<*>>>(it) }
}

@Suppress("UNCHECKED_CAST")
fun runMutableConstConstructorTests() {
    fun <T> testConstructor(map: Map<String, Any>) {
        val values: Collection<T> = map["values"] as Collection<T>
        val set: ConstMutableMultiSet<T> = ConstMutableMultiSet(values)
        testConstructedMultiSet(set, map)
    }

    multiSetConstructorIntTestValues.forEach { testConstructor<Int>(it) }
    multiSetConstructorExceptionTestValues.forEach { testConstructor<Exception>(it) }
    multiSetConstructorIntListTestValues.forEach { testConstructor<IntList>(it) }
    multiSetConstructorCompListTestValues.forEach { testConstructor<List<Comparable<*>>>(it) }
}
