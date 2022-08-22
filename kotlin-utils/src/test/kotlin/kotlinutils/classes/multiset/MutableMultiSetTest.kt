package kotlinutils.classes.multiset

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class MutableMultiSetTest {
    @Test
    fun testConstructor() {
        // collection
        var set: MutableMultiSet<Int> = MutableMultiSet(listOf())
        var expectedSize = 0
        assertEquals(expectedSize, set.size)

        set = MutableMultiSet(setOf(-12))
        expectedSize = 1
        assertEquals(expectedSize, set.size)

        set = MutableMultiSet(mutableListOf(10, 10, 10, 10))
        expectedSize = 4
        assertEquals(expectedSize, set.size)

        set = MutableMultiSet(listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25))
        expectedSize = 10
        assertEquals(expectedSize, set.size)

        val errSet = MutableMultiSet(mutableSetOf(ArithmeticException(), NullPointerException()))
        expectedSize = 2
        assertEquals(expectedSize, errSet.size)

        val listSet = MutableMultiSet(listOf(listOf(1, 3, 4), listOf(55, 66, 77)))
        expectedSize = 2
        assertEquals(expectedSize, listSet.size)

        // size and init fn
        set = MutableMultiSet(5) { 2 }
        expectedSize = 5
        var expected = MutableMultiSet(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MutableMultiSet(4) { 3 * it }
        expectedSize = 4
        expected = MutableMultiSet(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = MutableMultiSet(8) { values[it] }
        expectedSize = 8
        expected = MutableMultiSet(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }

    @Test
    fun testEquals() {
        // equals
        var set1: MutableMultiSet<Int> = mutableMultiSetOf()
        assertEquals(set1, set1)

        set1 = mutableMultiSetOf(3)
        assertEquals(set1, set1)

        set1 = mutableMultiSetOf(3, 3, 3)
        assertEquals(set1, set1)

        set1 = mutableMultiSetOf(2, 3, 4)
        assertEquals(set1, set1)

        set1 = mutableMultiSetOf(1, 2, 3)
        var set2 = mutableMultiSetOf(3, 1, 2)
        assertEquals(set1, set2)
        assertEquals(set2, set1)

        // not equals
        set1 = mutableMultiSetOf()
        set2 = mutableMultiSetOf(0)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = mutableMultiSetOf(1, 1)
        set2 = mutableMultiSetOf(1)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = mutableMultiSetOf(1, 2)
        set2 = mutableMultiSetOf(2, 2)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = mutableMultiSetOf(-1, 3, 1, -3)
        set2 = mutableMultiSetOf(2, -2)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        // other type
        val stringSet1 = mutableMultiSetOf("", "abc")
        assertEquals(stringSet1, stringSet1)

        val stringSet2 = mutableMultiSetOf("abc")
        assertNotEquals(stringSet1, stringSet2)
        assertNotEquals(stringSet2, stringSet1)

        val listSet1 = mutableMultiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
        assertEquals(listSet1, listSet1)

        val listSet2 = mutableMultiSetOf(listOf(12, 34, 56))
        assertNotEquals(listSet1, listSet2)
        assertNotEquals(listSet2, listSet1)
    }

    @Test
    fun testGetCountOf() {
        var set: MutableMultiSet<Int> = mutableMultiSetOf()
        var expected = 0

        var value = 0
        assertEquals(expected, set.getCountOf(value))

        value = 100
        assertEquals(expected, set.getCountOf(value))

        set = mutableMultiSetOf(2)

        value = 2
        expected = 1
        assertEquals(expected, set.getCountOf(value))

        value = 1
        expected = 0
        assertEquals(expected, set.getCountOf(value))

        set = mutableMultiSetOf(1, 1, 2, 1, -4, 5, 2)

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
    fun testClear() {
        var set: MutableMultiSet<Int> = mutableMultiSetOf()
        set.clear()
        assertEquals(0, set.size)
        assertEquals(setOf(), set)

        set = mutableMultiSetOf(1, 2, 3)
        set.clear()
        assertEquals(0, set.size)
        assertEquals(setOf(), set)

        set = mutableMultiSetOf(-45, -45, -45, -45)
        set.clear()
        assertEquals(0, set.size)
        assertEquals(setOf(), set)
    }

    @Test
    fun testIterator() {
        var set: MutableMultiSet<Int> = mutableMultiSetOf()
        var iter = set.iterator()
        assertFalse(iter.hasNext())

        set = mutableMultiSetOf(1, 2, 3, 4)
        iter = set.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set = mutableMultiSetOf(1, 2, 3, 4, 1, 4, 5)
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

    @Test fun testAdd() = runAddTests()
    @Test fun testAddAll() = runAddAllTests()
    @Test fun testRemove() = runRemoveTests()
    @Test fun testRemoveAll() = runRemoveAllTests()
    @Test fun testRetainAll() = runRetainAllTests()

    @Test
    fun testContains() {
        var set: MutableMultiSet<Int> = mutableMultiSetOf()
        assertFalse(set.contains(0))
        assertFalse(set.contains(1000))
        assertFalse(set.contains(-1000))

        set = mutableMultiSetOf(1, 2)
        assertFalse(set.contains(0))
        assertTrue(set.contains(1))
        assertTrue(set.contains(2))

        set = mutableMultiSetOf(1, 1, 1)
        assertTrue(set.contains(1))
        assertFalse(set.contains(2))

        val error = ArithmeticException()
        val errSet = mutableMultiSetOf(ArithmeticException(), error, NumberFormatException())
        assertTrue(errSet.contains(error))

        val listSet = mutableMultiSetOf(listOf(), listOf(5, 6), listOf(9, 8, 3))
        assertTrue(listSet.contains(listOf()))
        assertTrue(listSet.contains(listOf(9, 8, 3)))
        assertFalse(listSet.contains(listOf(6, 6)))

        // adding elements
        set = mutableMultiSetOf()
        set.add(1)
        assertTrue(set.contains(1))
        assertFalse(set.contains(2))
        set.add(2)
        assertTrue(set.contains(2))
    }

    @Test
    fun testContainsAll() {
        // equal
        var set1: MutableMultiSet<Int> = mutableMultiSetOf()
        assertTrue(set1.containsAll(set1))

        set1 = mutableMultiSetOf(-445)
        assertTrue(set1.containsAll(set1))

        set1 = mutableMultiSetOf(1, 1)
        assertTrue(set1.containsAll(set1))

        set1 = mutableMultiSetOf(2, 3, 2, 4, 3, 4, 4)
        assertTrue(set1.containsAll(set1))

        set1 = mutableMultiSetOf(1, 2, 3)
        var set2 = mutableMultiSetOf(3, 1, 2)
        assertTrue(set1.containsAll(set2))
        assertTrue(set2.containsAll(set1))

        // subset
        set1 = mutableMultiSetOf(1)
        set2 = mutableMultiSetOf()
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(1, 2, 3, 4)
        set2 = mutableMultiSetOf(1, 3)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(1, 1, 1)
        set2 = mutableMultiSetOf(1, 1)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(1, 3, -1, 5)
        set2 = mutableMultiSetOf(1, 3, 5)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // overlap
        set1 = mutableMultiSetOf(1, 2, 3)
        set2 = mutableMultiSetOf(1, 3, 4)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(100, 100, 300, 400)
        set2 = mutableMultiSetOf(100, 300, 400, 400)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(-10, 5, -10, -10)
        set2 = mutableMultiSetOf(-10, -5, -10, -10)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // no overlap
        set1 = mutableMultiSetOf(1)
        set2 = mutableMultiSetOf(2)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(1, 1, 1, 1)
        set2 = mutableMultiSetOf(2, 2, 2, 2)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = mutableMultiSetOf(4, -4, 5, 7)
        set2 = mutableMultiSetOf(22, 23, 22)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // adding elements
        set1 = mutableMultiSetOf(1, 2, 3)
        set2 = mutableMultiSetOf(2, 4)
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
    fun testIsEmpty() {
        // empty
        var intSet: MutableMultiSet<Int> = mutableMultiSetOf()
        assertTrue(intSet.isEmpty())

        var stringSet: MutableMultiSet<String> = mutableMultiSetOf()
        assertTrue(stringSet.isEmpty())

        // not empty
        intSet = mutableMultiSetOf(0)
        assertFalse(intSet.isEmpty())

        intSet = mutableMultiSetOf(1000, -1000, 4, 2, 4)
        assertFalse(intSet.isEmpty())

        intSet = mutableMultiSetOf(3, 3, 3)
        assertFalse(intSet.isEmpty())

        stringSet = mutableMultiSetOf("123", "abc")
        assertFalse(stringSet.isEmpty())

        stringSet = mutableMultiSetOf("abcdefg", "abcdefg")
        assertFalse(stringSet.isEmpty())

        // remove elements
        intSet = mutableMultiSetOf(1)
        intSet.remove(1)
        assertTrue(intSet.isEmpty())

        intSet = mutableMultiSetOf(1, 1)
        intSet.remove(1)
        assertFalse(intSet.isEmpty())
        intSet.remove(1)
        assertTrue(intSet.isEmpty())

        intSet = mutableMultiSetOf(2, 3)
        intSet.remove(3)
        assertFalse(intSet.isEmpty())
        intSet.remove(2)
        assertTrue(intSet.isEmpty())

        intSet = mutableMultiSetOf(2, 3)
        intSet.clear()
        assertTrue(intSet.isEmpty())
    }
}