package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.list.IntList
import xyz.lbres.kotlinutils.collection.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.CompList
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.Test

class MutableMultiSetImplTest {
    @Test
    fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values = map["values"] as Collection<T>
            val set = MutableMultiSetImpl(values)
            testConstructed(set, map)
        }

        multiSetConstructorIntTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<Int>(it) }
        }
        multiSetConstructorExceptionTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<Exception>(it) }
        }
        multiSetConstructorIntListTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<IntList>(it) }
        }
        multiSetConstructorCompListTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<CompList>(it) }
        }
    }

    @Test
    fun testEquals() {
        runMutableEqualsTests(::MutableMultiSetImpl, ::ConstMultiSetImpl)
        runMutableElementsEqualsTests(::MutableMultiSetImpl, ::ConstMultiSetImpl)
    }

    @Test
    fun testContains() {
        runMutableContainsTests(::MutableMultiSetImpl)
        runMutableElementContainsTests(::MutableMultiSetImpl)
    }

    @Test
    fun testContainsAll() {
        runMutableContainsAllTests(::MutableMultiSetImpl)
        runMutableElementContainsAllTests(::MutableMultiSetImpl)
    }

    @Test fun testClear() = runClearTests(::MutableMultiSetImpl)
    @Test fun testAdd() = runMutableElementsAddTests()
    @Test fun testAddAll() = runMutableElementsAddAllTests()
    @Test fun testRemove() = runMutableElementsRemoveTests()
    @Test fun testRemoveAll() = runMutableElementsRemoveAllTests()
    @Test fun testRetainAll() = runMutableElementsRetainAllTests()

    @Test
    fun testMinus() {
        runMinusTests(::MutableMultiSetImpl, ::ConstMultiSetImpl, const = false)
        runMutableElementMinusTests(::MutableMultiSetImpl)
    }

    @Test
    fun testPlus() {
        runPlusTests(::MutableMultiSetImpl, ::ConstMultiSetImpl, const = false)
        runMutableElementPlusTests(::MutableMultiSetImpl)
    }

    @Test
    fun testIntersect() {
        runIntersectTests(::MutableMultiSetImpl, ::ConstMultiSetImpl, const = false)
        runMutableElementIntersectTests(::MutableMultiSetImpl)
    }

    @Test fun testIsEmpty() = runMutableIsEmptyTests(::MutableMultiSetImpl)

    @Test
    fun testGetCountOf() {
        runMutableGetCountOfTests(::MutableMultiSetImpl)
        runMutableElementGetCountOfTests(::MutableMultiSetImpl)
    }

    @Test
    fun testIterator() {
        runMutableIteratorTests(::MutableMultiSetImpl)
        runMutableElementsIteratorTests(::MutableMultiSetImpl)
    }

    @Test
    fun testToString() {
        runMutableToStringTests(::MutableMultiSetImpl)
        runMutableElementToStringTests(::MutableMultiSetImpl)
    }
}
