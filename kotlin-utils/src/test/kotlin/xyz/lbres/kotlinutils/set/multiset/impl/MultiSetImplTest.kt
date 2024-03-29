package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiSetImplTest {
    private fun <T> createSet(): (Collection<T>) -> MultiSetImpl<T> = { MultiSetImpl(it) }
    private fun <T> createOtherSet(): (Collection<T>) -> ConstMutableMultiSetImpl<T> = { ConstMutableMultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values = map["values"] as Collection<T>
            val set = MultiSetImpl(values)
            testConstructed(set, map)
        }

        multiSetConstructorIntTestValues.forEach { runTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { runTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { runTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { runTest<List<Comparable<*>>>(it) }
        testConstructorWithMutableElements { runTest<IntList>(it) }
    }

    @Test
    fun testEquals() {
        runEqualsTests(createSet(), createOtherSet())
        runMutableElementsEqualsTests(createSet(), createOtherSet())
    }

    @Test
    fun testContains() {
        runContainsTests(createSet())
        runMutableElementContainsTests(createSet())
    }

    @Test
    fun testContainsAll() {
        runContainsAllTests(createSet())
        runMutableElementContainsAllTests(createSet())
    }

    @Test
    fun testMinus() {
        runMinusTests(createSet(), createOtherSet())
        runMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runPlusTests(createSet(), createOtherSet())
        runMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runIntersectTests(createSet(), createOtherSet())
        runMutableElementIntersectTests(createSet())
    }

    @Test fun testIsEmpty() = runIsEmptyTests(createSet())

    @Test
    fun testGetCountOf() {
        runGetCountOfTests(createSet())
        runMutableElementGetCountOfTests(createSet())
    }

    @Test
    fun testIterator() {
        runIteratorTests(createSet())
        runMutableElementsIteratorTests(createSet())
    }

    @Test
    fun testToString() {
        runToStringTests(createSet())
        runMutableElementToStringTests(createSet())
    }
}
