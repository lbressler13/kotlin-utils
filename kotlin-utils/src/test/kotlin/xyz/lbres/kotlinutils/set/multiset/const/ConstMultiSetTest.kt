package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class ConstMultiSetTest {
    private fun <T> createSet(): (Collection<T>) -> ConstMultiSet<T> = { ConstMultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> doTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values: Collection<T> = map["values"] as Collection<T>
            val set: ConstMultiSet<T> = ConstMultiSetImpl(values)
            testConstructedMultiSet(set, map)
        }

        multiSetConstructorIntTestValues.forEach { doTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { doTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { doTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { doTest<List<Comparable<*>>>(it) }
    }

    @Test fun testEquals() = runMultiSetEqualsTests(createSet(), createSet(), createSet()) { MutableMultiSetImpl(it) }

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
