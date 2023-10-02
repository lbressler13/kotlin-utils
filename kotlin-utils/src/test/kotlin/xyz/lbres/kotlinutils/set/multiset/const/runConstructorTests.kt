package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorCompListTestValues
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorExceptionTestValues
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorIntListTestValues
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorIntTestValues
import kotlin.test.assertEquals

@Suppress("UNCHECKED_CAST")
fun runConstConstructorTests() {
    fun <T> testConstructor(map: Map<String, Any>) {
        val values: Collection<T> = map["values"] as Collection<T>
        val expectedSize: Int = map["size"] as Int
        val expectedDistinct: Set<T> = map["distinct"] as Set<T>

        val set: ConstMultiSet<T> = ConstMultiSetImpl(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expectedDistinct, set.distinctValues)
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
        val expectedSize: Int = map["size"] as Int
        val expectedDistinct: Set<T> = map["distinct"] as Set<T>

        val set: ConstMutableMultiSet<T> = ConstMutableMultiSet(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expectedDistinct, set.distinctValues)
    }

    multiSetConstructorIntTestValues.forEach { testConstructor<Int>(it) }
    multiSetConstructorExceptionTestValues.forEach { testConstructor<Exception>(it) }
    multiSetConstructorIntListTestValues.forEach { testConstructor<IntList>(it) }
    multiSetConstructorCompListTestValues.forEach { testConstructor<List<Comparable<*>>>(it) }
}
