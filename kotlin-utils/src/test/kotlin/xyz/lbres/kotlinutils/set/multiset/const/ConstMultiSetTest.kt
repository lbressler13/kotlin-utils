package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMultiSetTest {
    private fun <T> createSet(): (Collection<T>) -> ConstMultiSet<T> = { ConstMultiSetImpl(it) }
    private fun <T> createOtherSet(): (Collection<T>) -> MutableMultiSetImpl<T> = { MutableMultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values: Collection<T> = map["values"] as Collection<T>
            val set: ConstMultiSet<T> = ConstMultiSetImpl(values)
            testConstructed(set, map)
        }

        multiSetConstructorIntTestValues.forEach { runTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { runTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { runTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { runTest<List<Comparable<*>>>(it) }
    }

    @Test fun testEquals() = runEqualsTests(createSet(), createOtherSet())

    @Test fun testContains() = runContainsTests(createSet())
    @Test fun testContainsAll() = runContainsAllTests(createSet())

    @Test
    fun testMinus() {
        runMinusTests(createSet(), createOtherSet())
    }

    @Test
    fun testPlus() {
        runPlusTests(createSet(), createOtherSet())
    }

    @Test
    fun testIntersect() {
        runIntersectTests(createSet(), createOtherSet())
    }

    @Test fun testIsEmpty() = runIsEmptyTests(createSet())
    @Test fun testGetCountOf() = runGetCountOfTests(createSet())

    @Test fun testIterator() = runIteratorTests(createSet())
    @Test fun testToString() = runToStringTests(createSet())
}
