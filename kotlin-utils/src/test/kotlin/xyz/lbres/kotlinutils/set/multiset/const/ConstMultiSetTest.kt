package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMultiSetTest {
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

    @Test fun testEquals() = runEqualsTests(::ConstMultiSetImpl, ::MutableMultiSetImpl)

    @Test fun testContains() = runContainsTests(::ConstMultiSetImpl)
    @Test fun testContainsAll() = runContainsAllTests(::ConstMultiSetImpl)

    @Test fun testMinus() = runMinusTests(::ConstMultiSetImpl, ::MutableMultiSetImpl, false)
    @Test fun testPlus() = runPlusTests(::ConstMultiSetImpl, ::MutableMultiSetImpl)
    @Test fun testIntersect() = runIntersectTests(::ConstMultiSetImpl, ::MutableMultiSetImpl)

    @Test fun testMinusC() = runMinusTests(::ConstMultiSetImpl, ::ConstMutableMultiSetImpl, true)
    @Test fun testPlusC() = runPlusCTests(::ConstMultiSetImpl, ::ConstMutableMultiSetImpl)
    @Test fun testIntersectC() = runIntersectCTests(::ConstMultiSetImpl, ::ConstMutableMultiSetImpl)

    @Test fun testIsEmpty() = runIsEmptyTests(::ConstMultiSetImpl)
    @Test fun testGetCountOf() = runGetCountOfTests(::ConstMultiSetImpl)

    @Test fun testIterator() = runIteratorTests(::ConstMultiSetImpl)
    @Test fun testToString() = runToStringTests(::ConstMultiSetImpl)
}
