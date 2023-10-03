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
        fun <T> doTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values = map["values"] as Collection<T>
            val set = MultiSetImpl(values)
            testConstructedMultiSet(set, map)
        }

        multiSetConstructorIntTestValues.forEach { doTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { doTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { doTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { doTest<List<Comparable<*>>>(it) }
        testConstructorWithMutableElements { doTest<IntList>(it) }
    }

    @Test
    fun testEquals() {
        runMultiSetEqualsTests(createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementsEqualsTests(createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
    }

    @Test
    fun testContains() {
        runMultiSetContainsTests(createSet(), createSet(), createSet())
        runMultiSetMutableElementContainsTests(createSet())
    }

    @Test
    fun testContainsAll() {
        runMultiSetContainsAllTests(createSet())
        runMultiSetMutableElementContainsAllTests(createSet())
    }

    @Test
    fun testMinus() {
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementIntersectTests(createSet())
    }

    @Test fun testIsEmpty() = runMultiSetIsEmptyTests(createSet(), createSet())

    @Test
    fun testGetCountOf() {
        runMultiSetGetCountOfTests(createSet(), createSet())
        runMultiSetMutableElementGetCountOfTests(createSet())
    }

    @Test
    fun testIterator() {
        runMultiSetIteratorTests(createSet(), createSet())
        runMultiSetMutableElementsIteratorTests(createSet())
    }

    @Test
    fun testToString() {
        runMultiSetToStringTests(createSet(), createSet())
        runMultiSetMutableElementToStringTests(createSet())
    }
}
