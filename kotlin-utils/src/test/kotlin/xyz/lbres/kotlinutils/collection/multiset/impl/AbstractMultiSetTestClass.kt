package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.multiset.MultiSet
import xyz.lbres.kotlinutils.collection.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.collection.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

typealias Create = (Collection<*>) -> MultiSet<*>
typealias MCreate = (Collection<*>) -> MutableMultiSet<*>

abstract class AbstractMultiSetTestClass {
    abstract val createSet: (Collection<*>) -> MultiSet<*>
    open val createMutableSet: ((Collection<*>) -> MutableMultiSet<*>)? = null
    abstract val createOtherSet: (Collection<*>) -> MultiSet<*>
    abstract val isMutableSet: Boolean
    abstract val hasMutableElements: Boolean

    @Test
    abstract fun testConstructor()

    @Test
    fun testEquals() {
        runTestWithOther(::runEqualsTests, ::runMutableEqualsTests, ::runMutableElementsEqualsTests)
    }

    @Test
    fun testContains() {
        runTest(::runContainsTests, ::runMutableContainsTests, ::runMutableElementContainsTests)
    }

    @Test
    fun testContainsAll() {
        runTest(::runContainsAllTests, ::runMutableContainsAllTests, ::runMutableElementContainsAllTests)
    }

    @Test
    fun testMinus() {
        runOpTest(::runMinusTests, ::runMutableElementMinusTests, const = false)
    }

    @Test
    fun testPlus() {
        runOpTest(::runPlusTests, ::runMutableElementPlusTests, const = false)
    }

    @Test
    fun testIntersect() {
        runOpTest(::runIntersectTests, ::runMutableElementIntersectTests, const = false)
    }

    @Test
    fun testIsEmpty() {
        runTest(::runIsEmptyTests, ::runMutableIsEmptyTests)
    }

    @Test
    fun testGetCountOf() {
        runTest(::runGetCountOfTests, ::runMutableGetCountOfTests, ::runMutableElementGetCountOfTests)
    }

    @Test
    fun testIterator() {
        runTest(::runIteratorTests, ::runMutableIteratorTests, ::runMutableElementIteratorTests)
    }

    @Test
    fun testToString() {
        runTest(::runToStringTests, ::runMutableToStringTests, ::runMutableElementToStringTests)
    }

    protected fun runTest(standardTest: (Create) -> Unit, mutableTest: (MCreate) -> Unit, mutableElementsTest: ((Create) -> Unit)? = null) {
        if (isMutableSet) {
            mutableTest(createMutableSet!!)
        } else {
            standardTest(createSet)
        }

        if (hasMutableElements && mutableElementsTest != null) {
            mutableElementsTest(createSet)
        }
    }

    protected fun runTestWithOther(standardTest: (Create, Create) -> Unit, mutableTest: ((MCreate, Create) -> Unit)? = null, mutableElementsTest: ((Create, Create) -> Unit)? = null) {
        if (isMutableSet && mutableTest != null) {
            mutableTest(createMutableSet!!, createOtherSet)
        } else if (!isMutableSet) {
            standardTest(createSet, createOtherSet)
        }

        if (hasMutableElements && mutableElementsTest != null) {
            mutableElementsTest(createSet, createOtherSet)
        }
    }

    protected fun runOpTest(standardTest: (Create, Create, Boolean) -> Unit, mutableElementsTest: (Create) -> Unit, const: Boolean, other: Create = createOtherSet) {
        standardTest(createSet, other, const)

        if (hasMutableElements) {
            mutableElementsTest(createSet)
        }
    }
}