package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMutableMultiSetTest {
    private fun <T> createSet(): (Collection<T>) -> ConstMutableMultiSet<T> = { ConstMutableMultiSetImpl(it) }
    private fun <T> createOtherSet(): (Collection<T>) -> MultiSetImpl<T> = { MultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values: Collection<T> = map["values"] as Collection<T>
            val set: ConstMutableMultiSet<T> = ConstMutableMultiSetImpl(values)
            testConstructed(set, map)
        }

        multiSetConstructorIntTestValues.forEach { runTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { runTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { runTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { runTest<List<Comparable<*>>>(it) }
    }

    @Test fun testEquals() = runMutableEqualsTests(createSet(), createSet(), createSet(), createOtherSet())

    @Test fun testContains() = runMutableContainsTests(createSet(), createSet(), createSet())
    @Test fun testContainsAll() = runMutableContainsAllTests(createSet())

    @Test fun testClear() = runClearTests(createSet())
    @Test fun testAdd() = runAddTests(createSet(), createSet())
    @Test fun testAddAll() = runAddAllTests(createSet())
    @Test fun testRemove() = runRemoveTests(createSet())
    @Test fun testRemoveAll() = runRemoveAllTests(createSet())
    @Test fun testRetainAll() = runClearTests(createSet())

    @Test
    fun testMinus() {
        runMinusTests(createSet(), createSet(), createSet(), createSet(), createOtherSet())
    }

    @Test
    fun testPlus() {
        runPlusTests(createSet(), createSet(), createSet(), createSet(), createOtherSet())
    }

    @Test
    fun testIntersect() {
        runIntersectTests(createSet(), createSet(), createSet(), createOtherSet())
    }

    @Test
    fun testMinusConst() {
        // TODO
    }

    @Test
    fun testPlusConst() {
        // TODO
    }

    @Test
    fun testIntersectConst() {
        // TODO
    }

    @Test fun testIsEmpty() = runMutableIsEmptyTests(createSet(), createSet())
    @Test fun testGetCountOf() = runMutableGetCountOfTests(createSet(), createSet())

    @Test fun testIterator() = runMutableIteratorTests(createSet(), createSet())
    @Test fun testToString() = runMutableToStringTests(createSet(), createSet())
}
