package xyz.lbres.kotlinutils.set.multiset.utils

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CountsMapTest {
    @Test
    fun testFrom() {
        // TODO
    }

    @Test
    fun testDistinct() {
        // TODO
    }

    @Test
    fun testGetCountOf() {
        var intMap: CountsMap<Int> = CountsMap.from(emptyList())
        assertEquals(0, intMap.getCountOf(0))
        assertEquals(0, intMap.getCountOf(100))

        intMap = CountsMap.from(listOf(2))
        assertEquals(1, intMap.getCountOf(2))
        assertEquals(0, intMap.getCountOf(1))

        intMap = CountsMap.from(listOf(1, 1, 2, 1, -4, 5, 2))
        assertEquals(3, intMap.getCountOf(1))
        assertEquals(2, intMap.getCountOf(2))
        assertEquals(1, intMap.getCountOf(-4))
        assertEquals(1, intMap.getCountOf(5))

        val listMap = CountsMap.from(listOf(listOf(1, 2, 3), listOf(1, 2, 3)))
        assertEquals(2, listMap.getCountOf(listOf(1, 2, 3)))
        assertEquals(0, listMap.getCountOf(listOf(1, 2)))
    }

    @Test
    fun testIsEmpty() {
        // empty
        var intMap: CountsMap<Int> = CountsMap.from(emptyList())
        assertTrue(intMap.isEmpty())

        var stringMap: CountsMap<String> = CountsMap.from(emptyList())
        assertTrue(stringMap.isEmpty())

        // not empty
        intMap = CountsMap.from(listOf(0))
        assertFalse(intMap.isEmpty())

        intMap = CountsMap.from(listOf(1000, -1000, 4, 2, 4))
        assertFalse(intMap.isEmpty())

        intMap = CountsMap.from(listOf(3, 3, 3))
        assertFalse(intMap.isEmpty())

        stringMap = CountsMap.from(listOf("123", "abc"))
        assertFalse(stringMap.isEmpty())

        stringMap = CountsMap.from(listOf("hello world", "hello world"))
        assertFalse(stringMap.isEmpty())
    }

    @Test
    fun testContains() {
        var intMap: CountsMap<Int> = CountsMap.from(emptyList())
        assertFalse(intMap.contains(0))
        assertFalse(intMap.contains(1000))
        assertFalse(intMap.contains(-1000))

        intMap = CountsMap.from(listOf(1, 2))
        assertFalse(intMap.contains(0))
        assertTrue(intMap.contains(1))
        assertTrue(intMap.contains(2))

        intMap = CountsMap.from(listOf(1, 1, 1))
        assertTrue(intMap.contains(1))
        assertFalse(intMap.contains(2))

        val error = ArithmeticException()
        val errorMap = CountsMap.from(listOf(ArithmeticException(), error, NumberFormatException()))
        assertTrue(errorMap.contains(error))

        val listMap = CountsMap.from(listOf(emptyList(), listOf(5, 6), listOf(9, 8, 3)))
        assertTrue(listMap.contains(emptyList()))
        assertTrue(listMap.contains(listOf(9, 8, 3)))
        assertFalse(listMap.contains(listOf(6, 6)))
    }

    @Test
    fun testContainsAll() {
        // equal
        var map: CountsMap<Int> = CountsMap.from(emptyList())
        var collection: Collection<Int> = emptyList()
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(listOf(-445))
        collection = listOf(-445)
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(listOf(1, 1))
        collection = listOf(1, 1)
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(listOf(2, 3, 2, 4, 3, 4, 4))
        collection = listOf(2, 3, 2, 4, 3, 4, 4)
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(listOf(1, 2, 3))
        collection = listOf(3, 1, 2)
        assertTrue(map.containsAll(collection))

        // submap
        map = CountsMap.from(listOf(1))
        collection = emptyList()
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(emptyList())
        collection = listOf(1)
        assertFalse(map.containsAll(collection))

        map = CountsMap.from(listOf(1, 2, 3, 4))
        collection = listOf(1, 3)
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(listOf(1, 1, 1))
        collection = listOf(1, 1)
        assertTrue(map.containsAll(collection))

        map = CountsMap.from(listOf(1, 3, 5))
        collection = listOf(1, 3, -1, 5)
        assertFalse(map.containsAll(collection))

        // overlapping keys
        map = CountsMap.from(listOf(1, 2, 3))
        collection = listOf(1, 3, 4)
        assertFalse(map.containsAll(collection))

        map = CountsMap.from(listOf(100, 100, 300, 400))
        collection = listOf(100, 300, 400, 400)
        assertFalse(map.containsAll(collection))

        map = CountsMap.from(listOf(-10, 5, -10, -10))
        collection = listOf(-10, -5, -10, -10)
        assertFalse(map.containsAll(collection))

        // no overlapping keys
        map = CountsMap.from(listOf(1))
        collection = listOf(2)
        assertFalse(map.containsAll(collection))

        map = CountsMap.from(listOf(1, 1, 1, 1))
        collection = listOf(2, 2, 2, 2)
        assertFalse(map.containsAll(collection))

        map = CountsMap.from(listOf(4, -4, 5, 7))
        collection = listOf(22, 23, 22)
        assertFalse(map.containsAll(collection))
    }

    @Test
    fun testForEach() {
        // TODO
    }

    @Test
    fun testToString() {
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        assertEquals("[]", intCounts.toString())

        intCounts = CountsMap(mapOf(4 to 1))
        assertEquals("[4]", intCounts.toString())

        intCounts = CountsMap(mapOf(4 to 3))
        assertEquals("[4, 4, 4]", intCounts.toString())

        intCounts = CountsMap(mapOf(3 to 1, 1 to 2, 2 to 1))
        var options = setOf(
            "[1, 1, 2, 3]", "[1, 1, 3, 2]", "[2, 1, 1, 3]", "[2, 3, 1, 1]", "[3, 1, 1, 2]", "[3, 2, 1, 1]"
        )
        assertContains(options, intCounts.toString())

        var stringCounts = CountsMap(mapOf("abc" to 4, "hello" to 2, "world" to 3))
        options = setOf(
            "[abc, abc, abc, abc, hello, hello, world, world, world]",
            "[abc, abc, abc, abc, world, world, world, hello, hello]",
            "[hello, hello, abc, abc, abc, abc, world, world, world]",
            "[hello, hello, world, world, world, abc, abc, abc, abc]",
            "[world, world, world, abc, abc, abc, abc, hello, hello]",
            "[world, world, world, hello, hello, abc, abc, abc, abc]"
        )
        assertContains(options, stringCounts.toString())

        stringCounts = CountsMap(mapOf("" to 6))
        assertEquals("[, , , , , ]", stringCounts.toString())

        val listCounts = CountsMap(mapOf(listOf(1, 2, 3) to 2, listOf("hello") to 1))
        options = setOf("[[1, 2, 3], [1, 2, 3], [hello]]", "[[hello], [1, 2, 3], [1, 2, 3]]")
        assertContains(options, listCounts.toString())
    }

    @Test
    fun testToList() {
        var intCounts: CountsMap<Int> = CountsMap(emptyMap())
        assertEquals(emptyList(), intCounts.toList())

        intCounts = CountsMap(mapOf(2 to 3))
        assertEquals(listOf(2, 2, 2), intCounts.toList())

        intCounts = CountsMap(mapOf(0 to 2))
        assertEquals(listOf(0, 0), intCounts.toList())

        intCounts = CountsMap(mapOf(1 to 2, 8 to 1, -4 to 3))
        val intOptions = setOf(
            listOf(1, 1, 8, -4, -4, -4),
            listOf(1, 1, -4, -4, -4, 8),
            listOf(8, 1, 1, -4, -4, -4),
            listOf(8, -4, -4, -4, 1, 1),
            listOf(-4, -4, -4, 1, 1, 8),
            listOf(-4, -4, -4, 8, 1, 1)
        )
        assertContains(intOptions, intCounts.toList())

        val stringCounts = CountsMap(mapOf("hello" to 1, "world" to 2))
        val stringOptions = setOf(listOf("hello", "world", "world"), listOf("world", "world", "hello"))
        assertContains(stringOptions, stringCounts.toList())
    }
}
