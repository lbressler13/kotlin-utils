package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMutableMultiSetTest {
    private fun <T> createSet(): (Collection<T>) -> ConstMutableMultiSet<T> = { ConstMutableMultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> doTest(map: Map<String, Any>) {
            val values: Collection<T> = map["values"] as Collection<T>
            val set: ConstMutableMultiSet<T> = ConstMutableMultiSetImpl(values)
            testConstructedMultiSet(set, map)
        }

        multiSetConstructorIntTestValues.forEach { doTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { doTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { doTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { doTest<List<Comparable<*>>>(it) }
    }

    @Test fun testEquals() = runMultiSetEqualsTests(createSet(), createSet(), createSet()) { MultiSetImpl(it) }

    @Test fun testContains() = runMultiSetMutableContainsTests(createSet(), createSet(), createSet())
    @Test fun testContainsAll() = runMultiSetMutableContainsAllTests(createSet())

    @Test fun testClear() = runMultiSetClearTests(createSet())
    @Test fun testAdd() = runMultiSetAddTests(createSet(), createSet())
    @Test fun testAddAll() = runMultiSetAddAllTests(createSet())
    @Test fun testRemove() = runMultiSetRemoveTests(createSet())
    @Test fun testRemoveAll() = runMultiSetRemoveAllTests(createSet())
    @Test fun testRetainAll() = runMultiSetClearTests(createSet())

    @Test
    fun testMinus() {
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { MultiSetImpl(it) }
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { MultiSetImpl(it) }
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { MultiSetImpl(it) }
    }

    @Test fun testIsEmpty() = runMultiSetMutableIsEmptyTests(createSet(), createSet())
    @Test fun testGetCountOf() = runMultiSetMutableGetCountOfTests(createSet(), createSet())

    @Test fun testIterator() = runMultiSetMutableIteratorTests(createSet(), createSet())

    @Test fun testToString() = runMultiSetMutableToStringTests(createSet(), createSet())
}
