package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MutableMultiSetImplTest {
    private fun <T> createSet(): (Collection<T>) -> MutableMultiSetImpl<T> = { MutableMultiSetImpl(it) }
    private fun <T> createOtherSet(): (Collection<T>) -> ConstMultiSetImpl<T> = { ConstMultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values = map["values"] as Collection<T>
            val set = MutableMultiSetImpl(values)
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
        runMutableEqualsTests(createSet(), createSet(), createSet(), createOtherSet())
        runMutableElementsEqualsTests(createSet(), createSet(), createOtherSet())
    }

    @Test
    fun testContains() {
        runMutableContainsTests(createSet(), createSet(), createSet())
        runMutableElementContainsTests(createSet())
    }

    @Test
    fun testContainsAll() {
        runMutableContainsAllTests(createSet())
        runMutableElementContainsAllTests(createSet())
    }

    @Test fun testClear() = runClearTests(createSet())
    @Test fun testAdd() = runMutableElementsAddTests()
    @Test fun testAddAll() = runMutableElementsAddAllTests()
    @Test fun testRemove() = runMutableElementsRemoveTests()
    @Test fun testRemoveAll() = runMutableElementsRemoveAllTests()
    @Test fun testRetainAll() = runMutableElementsRetainAllTests()

    @Test
    fun testMinus() {
        runMinusTests(createSet(), createSet(), createSet(), createSet(), createOtherSet())
        runMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runPlusTests(createSet(), createSet(), createSet(), createSet(), createOtherSet())
        runMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runIntersectTests(createSet(), createSet(), createSet(), createOtherSet())
        runMutableElementIntersectTests(createSet())
    }

    @Test fun testIsEmpty() = runMutableIsEmptyTests(createSet(), createSet())

    @Test
    fun testGetCountOf() {
        runMutableGetCountOfTests(createSet(), createSet())
        runMutableElementGetCountOfTests(createSet())
    }

    @Test
    fun testIterator() {
        runMutableIteratorTests(createSet(), createSet())
        runMutableElementsIteratorTests(createSet())
    }

    @Test
    fun testToString() {
        runMutableToStringTests(createSet(), createSet())
        runMutableElementToStringTests(createSet())
    }
}
