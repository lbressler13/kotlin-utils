package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMultiSetTest {
    private fun <T> createSet(): (Collection<T>) -> ConstMultiSet<T> = { ConstMultiSetImpl(it) }

    @Test fun testConstructor() = runConstConstructorTests()
    @Test fun testEquals() = runConstEqualsTests()

    @Test fun testContains() = runMultiSetContainsTests(createSet(), createSet(), createSet())
    @Test fun testContainsAll() = runMultiSetContainsAllTests(createSet())

    @Test
    fun testMinus() {
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }
    }

    @Test fun testIsEmpty() = runMultiSetIsEmptyTests(createSet(), createSet())
    @Test fun testGetCountOf() = runMultiSetGetCountOfTests(createSet(), createSet())

    @Test fun testIterator() = runMultiSetIteratorTests(createSet(), createSet())
    @Test fun testToString() = runMultiSetToStringTests(createSet(), createSet())
}
