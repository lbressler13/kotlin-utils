package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.CompList
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiSetImplTest {
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
        multiSetConstructorCompListTestValues.forEach { runTest<CompList>(it) }
        testConstructorWithMutableElements { runTest<IntList>(it) }
    }

    @Test
    fun testEquals() {
        runEqualsTests(::MultiSetImpl, ::ConstMutableMultiSetImpl)
        runMutableElementsEqualsTests(::MultiSetImpl, ::ConstMutableMultiSetImpl)
    }

    @Test
    fun testContains() {
        runContainsTests(::MultiSetImpl)
        runMutableElementContainsTests(::MultiSetImpl)
    }

    @Test
    fun testContainsAll() {
        runContainsAllTests(::MultiSetImpl)
        runMutableElementContainsAllTests(::MultiSetImpl)
    }

    @Test
    fun testMinus() {
        runMinusTests(::MultiSetImpl, ::ConstMutableMultiSetImpl, const = false)
        runMutableElementMinusTests(::MultiSetImpl)
    }

    @Test
    fun testPlus() {
        runPlusTests(::MultiSetImpl, ::ConstMutableMultiSetImpl, const = false)
        runMutableElementPlusTests(::MultiSetImpl)
    }

    @Test
    fun testIntersect() {
        runIntersectTests(::MultiSetImpl, ::ConstMutableMultiSetImpl, const = false)
        runMutableElementIntersectTests(::MultiSetImpl)
    }

    @Test fun testIsEmpty() = runIsEmptyTests(::MultiSetImpl)

    @Test
    fun testGetCountOf() {
        runGetCountOfTests(::MultiSetImpl)
        runMutableElementGetCountOfTests(::MultiSetImpl)
    }

    @Test
    fun testIterator() {
        runIteratorTests(::MultiSetImpl)
        runMutableElementsIteratorTests(::MultiSetImpl)
    }

    @Test
    fun testToString() {
        runToStringTests(::MultiSetImpl)
        runMutableElementToStringTests(::MultiSetImpl)
    }
}
