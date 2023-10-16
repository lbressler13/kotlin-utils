package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiSetImplTest {
    private fun <T> createSet(): (Collection<T>) -> MultiSetImpl<T> = { MultiSetImpl(it) }

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
        runEqualsTests(createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMutableElementsEqualsTests(createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
    }

    @Test
    fun testContains() {
        runContainsTests(createSet(), createSet(), createSet())
        runMutableElementContainsTests(createSet())
    }

    @Test
    fun testContainsAll() {
        runContainsAllTests(createSet())
        runMutableElementContainsAllTests(createSet())
    }

    @Test
    fun testMinus() {
        runMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runIntersectTests(createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMutableElementIntersectTests(createSet())
    }

    @Test fun testIsEmpty() = runIsEmptyTests(createSet(), createSet())

    @Test
    fun testGetCountOf() {
        runGetCountOfTests(createSet(), createSet())
        runMutableElementGetCountOfTests(createSet())
    }

    @Test
    fun testIterator() {
        runIteratorTests(createSet(), createSet())
        runMutableElementsIteratorTests(createSet())
    }

    @Test
    fun testToString() {
        runToStringTests(createSet(), createSet())
        runMutableElementToStringTests(createSet())
    }
}
