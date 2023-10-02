package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorCompListTestValues
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorExceptionTestValues
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorIntListTestValues
import xyz.lbres.kotlinutils.set.multiset.testutils.multiSetConstructorIntTestValues
import kotlin.test.assertEquals

@Suppress("UNCHECKED_CAST")
fun runImmutableConstructorTests() {
    fun <T> testConstructor(map: Map<String, Any>) {
        val values: Collection<T> = map["values"] as Collection<T>
        val expectedSize: Int = map["size"] as Int
        val expectedDistinct: Set<T> = map["distinct"] as Set<T>

        val set: MultiSet<T> = MultiSetImpl(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expectedDistinct, set.distinctValues)
    }

    multiSetConstructorIntTestValues.forEach { testConstructor<Int>(it) }
    multiSetConstructorExceptionTestValues.forEach { testConstructor<Exception>(it) }
    multiSetConstructorIntListTestValues.forEach { testConstructor<IntList>(it) }
    multiSetConstructorCompListTestValues.forEach { testConstructor<List<Comparable<*>>>(it) }

    testConstructorWithMutableElements { testConstructor<IntList>(it) }
}

@Suppress("UNCHECKED_CAST")
fun runMutableConstructorTests() {
    fun <T> testConstructor(map: Map<String, Any>) {
        val values: Collection<T> = map["values"] as Collection<T>
        val expectedSize: Int = map["size"] as Int
        val expectedDistinct: Set<T> = map["distinct"] as Set<T>

        val set: MutableMultiSet<T> = MutableMultiSetImpl(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expectedDistinct, set.distinctValues)
    }

    multiSetConstructorIntTestValues.forEach { testConstructor<Int>(it) }
    multiSetConstructorExceptionTestValues.forEach { testConstructor<Exception>(it) }
    multiSetConstructorIntListTestValues.forEach { testConstructor<IntList>(it) }
    multiSetConstructorCompListTestValues.forEach { testConstructor<List<Comparable<*>>>(it) }

    testConstructorWithMutableElements { testConstructor<IntList>(it) }
}

private fun testConstructorWithMutableElements(testConstructor: (Map<String, Any>) -> Unit) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val values = listOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var map = mapOf("values" to values, "size" to 3, "distinct" to setOf(listOf(1, 2, 3)))
    testConstructor(map)

    mutableList1.clear()
    mutableList2.add(4)
    map = mapOf("values" to values, "size" to 3, "distinct" to setOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3, 4)))
    testConstructor(map)
}
