package kotlinutils.classes.multiset

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class MultiSetTest {
    @Test
    fun testConstructor() {
        // collection
        var set: MultiSet<Int> = MultiSet(listOf())
        var expectedSize = 0
        assertEquals(expectedSize, set.size)

        set = MultiSet(setOf(-12))
        expectedSize = 1
        assertEquals(expectedSize, set.size)

        set = MultiSet(mutableListOf(10, 10, 10, 10))
        expectedSize = 4
        assertEquals(expectedSize, set.size)

        set = MultiSet(listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25))
        expectedSize = 10
        assertEquals(expectedSize, set.size)

        val errSet = MultiSet(mutableSetOf(ArithmeticException(), NullPointerException()))
        expectedSize = 2
        assertEquals(expectedSize, errSet.size)

        val listSet = MultiSet(listOf(listOf(1, 3, 4), listOf(55, 66, 77)))
        expectedSize = 2
        assertEquals(expectedSize, listSet.size)

        // size and init fn
        set = MultiSet(5) { 2 }
        expectedSize = 5
        var expected = MultiSet(listOf(2, 2, 2, 2, 2))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        set = MultiSet(4) { 3 * it }
        expectedSize = 4
        expected = MultiSet(listOf(0, 3, 6, 9))
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)

        val values = listOf(4, 6, 7, 8, 9, 11, -3, -3)
        set = MultiSet(8) { values[it] }
        expectedSize = 8
        expected = MultiSet(values)
        assertEquals(expectedSize, set.size)
        assertEquals(expected, set)
    }

    @Test
    fun testEquals() {
        // equals
        var set1: MultiSet<Int> = multiSetOf()
        assertEquals(set1, set1)

        set1 = multiSetOf(3)
        assertEquals(set1, set1)

        set1 = multiSetOf(3, 3, 3)
        assertEquals(set1, set1)

        set1 = multiSetOf(2, 3, 4)
        assertEquals(set1, set1)

        set1 = multiSetOf(1, 2, 3)
        var set2 = multiSetOf(3, 1, 2)
        assertEquals(set1, set2)
        assertEquals(set2, set1)

        // not equals
        set1 = multiSetOf()
        set2 = multiSetOf(0)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = multiSetOf(1, 1)
        set2 = multiSetOf(1)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = multiSetOf(1, 2)
        set2 = multiSetOf(2, 2)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        set1 = multiSetOf(-1, 3, 1, -3)
        set2 = multiSetOf(2, -2)
        assertNotEquals(set1, set2)
        assertNotEquals(set2, set1)

        // other type
        val stringSet1 = multiSetOf("", "abc")
        assertEquals(stringSet1, stringSet1)

        val stringSet2 = multiSetOf("abc")
        assertNotEquals(stringSet1, stringSet2)
        assertNotEquals(stringSet2, stringSet1)

        val listSet1 = multiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
        assertEquals(listSet1, listSet1)

        val listSet2 = multiSetOf(listOf(12, 34, 56))
        assertNotEquals(listSet1, listSet2)
        assertNotEquals(listSet2, listSet1)
    }

    @Test
    fun testContains() {
        var set: MultiSet<Int> = multiSetOf()
        assertFalse(set.contains(0))
        assertFalse(set.contains(1000))
        assertFalse(set.contains(-1000))

        set = multiSetOf(1, 2)
        assertFalse(set.contains(0))
        assertTrue(set.contains(1))
        assertTrue(set.contains(2))

        set = multiSetOf(1, 1, 1)
        assertTrue(set.contains(1))
        assertFalse(set.contains(2))

        val error = ArithmeticException()
        val errSet = multiSetOf(ArithmeticException(), error, NumberFormatException())
        assertTrue(errSet.contains(error))

        val listSet = multiSetOf(listOf(), listOf(5, 6), listOf(9, 8, 3))
        assertTrue(listSet.contains(listOf()))
        assertTrue(listSet.contains(listOf(9, 8, 3)))
        assertFalse(listSet.contains(listOf(6, 6)))
    }

    @Test
    fun testContainsAll() {
        // equal
        var set1: MultiSet<Int> = multiSetOf()
        assertTrue(set1.containsAll(set1))

        set1 = multiSetOf(-445)
        assertTrue(set1.containsAll(set1))

        set1 = multiSetOf(1, 1)
        assertTrue(set1.containsAll(set1))

        set1 = multiSetOf(2, 3, 2, 4, 3, 4, 4)
        assertTrue(set1.containsAll(set1))

        set1 = multiSetOf(1, 2, 3)
        var set2 = multiSetOf(3, 1, 2)
        assertTrue(set1.containsAll(set2))
        assertTrue(set2.containsAll(set1))

        // subset
        set1 = multiSetOf(1)
        set2 = multiSetOf()
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(1, 2, 3, 4)
        set2 = multiSetOf(1, 3)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(1, 1, 1)
        set2 = multiSetOf(1, 1)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(1, 3, -1, 5)
        set2 = multiSetOf(1, 3, 5)
        assertTrue(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // overlap
        set1 = multiSetOf(1, 2, 3)
        set2 = multiSetOf(1, 3, 4)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(100, 100, 300, 400)
        set2 = multiSetOf(100, 300, 400, 400)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(-10, 5, -10, -10)
        set2 = multiSetOf(-10, -5, -10, -10)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        // no overlap
        set1 = multiSetOf(1)
        set2 = multiSetOf(2)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(1, 1, 1, 1)
        set2 = multiSetOf(2, 2, 2, 2)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))

        set1 = multiSetOf(4, -4, 5, 7)
        set2 = multiSetOf(22, 23, 22)
        assertFalse(set1.containsAll(set2))
        assertFalse(set2.containsAll(set1))
    }

    @Test
    fun testIsEmpty() {
        // empty
        var intSet: MultiSet<Int> = multiSetOf()
        assertTrue(intSet.isEmpty())

        var stringSet: MultiSet<String> = multiSetOf()
        assertTrue(stringSet.isEmpty())

        // not empty
        intSet = multiSetOf(0)
        assertFalse(intSet.isEmpty())

        intSet = multiSetOf(1000, -1000, 4, 2, 4)
        assertFalse(intSet.isEmpty())

        intSet = multiSetOf(3, 3, 3)
        assertFalse(intSet.isEmpty())

        stringSet = multiSetOf("123", "abc")
        assertFalse(stringSet.isEmpty())

        stringSet = multiSetOf("abcdefg", "abcdefg")
        assertFalse(stringSet.isEmpty())
    }

    @Test
    fun testGetCountOf() {
        var set: MultiSet<Int> = multiSetOf()
        var expected = 0

        var value = 0
        assertEquals(expected, set.getCountOf(value))

        value = 100
        assertEquals(expected, set.getCountOf(value))

        set = multiSetOf(2)

        value = 2
        expected = 1
        assertEquals(expected, set.getCountOf(value))

        value = 1
        expected = 0
        assertEquals(expected, set.getCountOf(value))

        set = multiSetOf(1, 1, 2, 1, -4, 5, 2)

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
    fun testIterator() {
        var set: MultiSet<Int> = multiSetOf()
        var iter = set.iterator()
        assertFalse(iter.hasNext())

        set = multiSetOf(1, 2, 3, 4)
        iter = set.iterator()
        var values: MutableList<Int> = mutableListOf()
        var expected = listOf(1, 2, 3, 4)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())

        set = multiSetOf(1, 2, 3, 4, 1, 4, 5)
        iter = set.iterator()
        values = mutableListOf()
        expected = listOf(1, 1, 2, 3, 4, 4, 5)
        while (iter.hasNext()) {
            values.add(iter.next())
        }
        assertEquals(expected.sorted(), values.sorted())
    }
}
