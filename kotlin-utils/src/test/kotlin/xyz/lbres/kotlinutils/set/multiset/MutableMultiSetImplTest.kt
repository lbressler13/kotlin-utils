package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.classes.multiset.MutableMultiSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class MutableMultiSetImplTest {
    @Test
    internal fun testConstructor() {
        var set: MutableMultiSet<Int> = MutableMultiSetImpl(listOf())
        var expectedSize = 0
        assertEquals(expectedSize, set.size)

        set = MutableMultiSetImpl(setOf(-12))
        expectedSize = 1
        assertEquals(expectedSize, set.size)

        set = MutableMultiSetImpl(mutableListOf(10, 10, 10, 10))
        expectedSize = 4
        assertEquals(expectedSize, set.size)

        set = MutableMultiSetImpl(listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25))
        expectedSize = 10
        assertEquals(expectedSize, set.size)

        val errSet = MutableMultiSetImpl(mutableSetOf(ArithmeticException(), NullPointerException()))
        expectedSize = 2
        assertEquals(expectedSize, errSet.size)

        val listSet = MutableMultiSetImpl(listOf(listOf(1, 3, 4), listOf(55, 66, 77)))
        expectedSize = 2
        assertEquals(expectedSize, listSet.size)
    }

    @Test
    internal fun testEquals() {
        // equals
        var set1: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertEquals(set1, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(3)
        assertEquals(set1, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(3, 3, 3)
        assertEquals(set1, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3, 4)
        assertEquals(set1, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
        var set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(3, 1, 2)
        assertEquals(set1, set2)
        assertEquals(set2, set1)

        // not equals
        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(0)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 2)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-1, 3, 1, -3)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, -2)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        // other type
        val stringSet1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf("", "abc")
        assertEquals(stringSet1, stringSet1)

        val stringSet2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf("abc")
        assertNotEquals(stringSet1, stringSet2)
        assertNotEquals(stringSet2, stringSet1)

        val listSet1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(
            listOf(12, 34, 56),
            listOf(77, 78, 0, 15),
            listOf(5)
        )
        assertEquals(listSet1, listSet1)

        val listSet2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(listOf(12, 34, 56))
        assertNotEquals(listSet1, listSet2)
        assertNotEquals(listSet2, listSet1)
    }

    @Test
    internal fun testGetCountOf() {
        var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        var expected = 0

        var value = 0
        assertEquals(expected, set.getCountOf(value))

        value = 100
        assertEquals(expected, set.getCountOf(value))

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2)

        value = 2
        expected = 1
        assertEquals(expected, set.getCountOf(value))

        value = 1
        expected = 0
        assertEquals(expected, set.getCountOf(value))

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 2, 1, -4, 5, 2)

        value = 1
        expected = 3
        assertEquals(expected, set.getCountOf(value))

        value = 2
        expected = 2
        assertEquals(expected, set.getCountOf(value))

        value = -4
        expected = 1
        assertEquals(expected, set.getCountOf(value))

        value = 5
        expected = 1
        assertEquals(expected, set.getCountOf(value))
    }

    @Test
    internal fun testIterator() {
        var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        var iter = set.iterator()
        assertFalse(iter.hasNext())

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)
        iter = set.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4, 1, 4, 5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set.add(5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set.remove(2)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 3, 4, 4, 5, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())
    }

    @Test internal fun testClear() = runClearTests()
    @Test internal fun testAdd() = runAddTests()
    @Test internal fun testAddAll() = runAddAllTests()
    @Test internal fun testRemove() = runRemoveTests()
    @Test internal fun testRemoveAll() = runRemoveAllTests()
    @Test internal fun testRetainAll() = runRetainAllTests()

    @Test
    internal fun testContains() {
        var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertFalse(set.contains(0))
        assertFalse(set.contains(1000))
        assertFalse(set.contains(-1000))

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2)
        assertFalse(set.contains(0))
        assertTrue(set.contains(1))
        assertTrue(set.contains(2))

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1)
        assertTrue(set.contains(1))
        assertFalse(set.contains(2))

        val error = ArithmeticException()
        val errSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(
            ArithmeticException(),
            error,
            NumberFormatException()
        )
        assertTrue(errSet.contains(error))

        val listSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(listOf(), listOf(5, 6), listOf(9, 8, 3))
        assertTrue(listSet.contains(listOf()))
        assertTrue(listSet.contains(listOf(9, 8, 3)))
        assertFalse(listSet.contains(listOf(6, 6)))

        // adding elements
        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        set.add(1)
        assertTrue(set.contains(1))
        assertFalse(set.contains(2))
        set.add(2)
        assertTrue(set.contains(2))
    }

    @Test
    internal fun testContainsAll() {
        // equal
        var set1: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertTrue(set1.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-445)
        assertTrue(set1.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
        assertTrue(set1.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3, 2, 4, 3, 4, 4)
        assertTrue(set1.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
        var set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(3, 1, 2)
        assertTrue(set1.containsAll(set2))
        assertTrue(set2.containsAll(set1))

        // subset
        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 3)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 3, -1, 5)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 3, 5)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // overlap
        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 3, 4)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(100, 100, 300, 400)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(100, 300, 400, 400)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-10, 5, -10, -10)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-10, -5, -10, -10)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // no overlap
        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1, 1)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 2, 2, 2)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(4, -4, 5, 7)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(22, 23, 22)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // adding elements
        set1 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
        set2 = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 4)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1.add(4)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set2.add(1)
        set2.add(3)
        assertTrue(set1.containsAll(set2))
        assertTrue(set2.containsAll(set1))
    }

    @Test
    internal fun testIsEmpty() {
        // empty
        var intSet: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertTrue(intSet.isEmpty())

        var stringSet: MutableMultiSet<String> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        assertTrue(stringSet.isEmpty())

        // not empty
        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(0)
        assertFalse(intSet.isEmpty())

        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1000, -1000, 4, 2, 4)
        assertFalse(intSet.isEmpty())

        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(3, 3, 3)
        assertFalse(intSet.isEmpty())

        stringSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf("123", "abc")
        assertFalse(stringSet.isEmpty())

        stringSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf("abcdefg", "abcdefg")
        assertFalse(stringSet.isEmpty())

        // remove elements
        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
        intSet.remove(1)
        assertTrue(intSet.isEmpty())

        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
        intSet.remove(1)
        assertFalse(intSet.isEmpty())
        intSet.remove(1)
        assertTrue(intSet.isEmpty())

        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3)
        intSet.remove(3)
        assertFalse(intSet.isEmpty())
        intSet.remove(2)
        assertTrue(intSet.isEmpty())

        intSet = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3)
        intSet.clear()
        assertTrue(intSet.isEmpty())
    }

    @Test
    internal fun testToString() {
        var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
        var expected = "[]"
        assertEquals(expected, set.toString())

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(100, -200, 400, 800)
        expected = "[100, -200, 400, 800]"
        assertEquals(expected, set.toString())

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1, 1)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, set.toString())

        set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 4, 2, 1)
        expected = "[2, 2, 4, 1]"
        assertEquals(expected, set.toString())

        set.add(5)
        expected = "[2, 2, 4, 1, 5]"
        assertEquals(expected, set.toString())

        set.remove(2)
        expected = "[2, 4, 1, 5]"
        assertEquals(expected, set.toString())
    }
}
