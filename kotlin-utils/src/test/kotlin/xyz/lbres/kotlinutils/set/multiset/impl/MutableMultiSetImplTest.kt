package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMutableElementContainsTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMutableIteratorTests
import kotlin.test.Test
import kotlin.test.assertEquals

class MutableMultiSetImplTest {
    private fun <T> createSet(): (Collection<T>) -> MutableMultiSetImpl<T> = { MutableMultiSetImpl(it) }

    @Test
    fun testConstructor() {
        fun <T> doTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values = map["values"] as Collection<T>
            val set = MutableMultiSetImpl(values)
            testConstructedMultiSet(set, map)
        }

        multiSetConstructorIntTestValues.forEach { doTest<Int>(it) }
        multiSetConstructorExceptionTestValues.forEach { doTest<Exception>(it) }
        multiSetConstructorIntListTestValues.forEach { doTest<IntList>(it) }
        multiSetConstructorCompListTestValues.forEach { doTest<List<Comparable<*>>>(it) }
        testConstructorWithMutableElements { doTest<IntList>(it) }
    }

    @Test
    fun testEquals() {
        runMultiSetEqualsTests(createSet(), createSet(), createSet()) { ConstMultiSetImpl(it) }
        runMultiSetMutableElementsEqualsTests(createSet(), createSet()) { ConstMultiSetImpl(it) }
    }

    @Test
    fun testContains() {
        runMultiSetMutableContainsTests(createSet(), createSet(), createSet())
        runMultiSetMutableElementContainsTests(createSet())
    }

    @Test
    fun testContainsAll() {
        runMultiSetMutableContainsAllTests(createSet())
        runMultiSetMutableElementContainsAllTests(createSet())
    }

    @Test fun testClear() = runClearTests()
    @Test fun testAdd() = runAddTests()
    @Test fun testAddAll() = runAddAllTests()
    @Test fun testRemove() = runRemoveTests()
    @Test fun testRemoveAll() = runRemoveAllTests()
    @Test fun testRetainAll() = runRetainAllTests()

    @Test
    fun testMinus() {
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMultiSetImpl(it) }
        runMultiSetMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMultiSetImpl(it) }
        runMultiSetMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { ConstMultiSetImpl(it) }
        runMultiSetMutableElementIntersectTests(createSet())
    }

    @Test fun testIsEmpty() = runMultiSetMutableIsEmptyTests(createSet(), createSet())
    @Test
    fun testGetCountOf() {
        runMultiSetMutableGetCountOfTests(createSet(), createSet())
        runMultiSetMutableElementGetCountOfTests(createSet())
    }

    @Test
    fun testIterator() {
        runMultiSetMutableIteratorTests(createSet(), createSet())

        // mutable elements
        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(0, 5, 7)
        val listSet: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)

        var listIter = listSet.iterator()
        var listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
        var listValues: MutableList<IntList> = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)

        mutableList2.clear()
        listIter = listSet.iterator()
        listExpected = listOf(listOf(1, 2, 3), emptyList())
        listValues = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)
    }

    @Test
    fun testToString() {
        runMultiSetMutableToStringTests(createSet(), createSet())
        runMultiSetMutableElementToStringTests(createSet())
    }
}
