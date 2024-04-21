package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMutableMultiSetTest {
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

    @Test fun testEquals() = runMutableEqualsTests(::ConstMutableMultiSetImpl, ::MultiSetImpl)

    @Test fun testContains() = runMutableContainsTests(::ConstMutableMultiSetImpl)
    @Test fun testContainsAll() = runMutableContainsAllTests(::ConstMutableMultiSetImpl)

    @Test fun testClear() = runClearTests(::ConstMutableMultiSetImpl)
    @Test fun testAdd() = runAddTests(::ConstMutableMultiSetImpl)
    @Test fun testAddAll() = runAddAllTests(::ConstMutableMultiSetImpl)
    @Test fun testRemove() = runRemoveTests(::ConstMutableMultiSetImpl)
    @Test fun testRemoveAll() = runRemoveAllTests(::ConstMutableMultiSetImpl)
    @Test fun testRetainAll() = runClearTests(::ConstMutableMultiSetImpl)

    @Test fun testMinus() = runMinusTests(::ConstMutableMultiSetImpl, ::MultiSetImpl, false)
    @Test fun testPlus() = runPlusTests(::ConstMutableMultiSetImpl, ::MultiSetImpl, false)
    @Test fun testIntersect() = runIntersectTests(::ConstMutableMultiSetImpl, ::MultiSetImpl, false)

    @Test fun testMinusC() = runMinusTests(::ConstMutableMultiSetImpl, ::ConstMultiSetImpl, true)
    @Test fun testPlusC() = runPlusTests(::ConstMutableMultiSetImpl, ::ConstMultiSetImpl, true)
    @Test fun testIntersectC() = runIntersectTests(::ConstMutableMultiSetImpl, ::ConstMultiSetImpl, true)

    @Test fun testIsEmpty() = runMutableIsEmptyTests(::ConstMutableMultiSetImpl)
    @Test fun testGetCountOf() = runMutableGetCountOfTests(::ConstMutableMultiSetImpl)

    @Test fun testIterator() = runMutableIteratorTests(::ConstMutableMultiSetImpl)
    @Test fun testToString() = runMutableToStringTests(::ConstMutableMultiSetImpl)
}
