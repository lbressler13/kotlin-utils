package xyz.lbres.kotlinutils.test

import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AssertUtilsTest {
    @Test
    fun assertContainsTest() {
        // true
        var intOptions: Collection<Int> = listOf(5)
        assertContains(intOptions, 5)

        intOptions = listOf(5, 5, 5, 5, 5)
        assertContains(intOptions, 5)

        intOptions = listOf(15, 20, 23, -8, 2, 5, 4)
        assertContains(intOptions, 5)
        assertContains(intOptions, -8)

        var stringOptions = setOf("hello", "world", "hello world", "farewell planet")
        assertContains(stringOptions, "hello world")

        var setValue = setOf(5, "hello", 'c', 1.5)
        var setOptions = multiSetOf(setOf(5, "hello", 'c', 1.5), setOf(5, "hello", 'c', 1.5), setOf(5, 1.5))
        assertContains(setOptions, setValue)

        var range = 5..10
        assertContains(range, 10)

        // false
        intOptions = setOf(-1)
        assertFailsWith<AssertionError> { assertContains(intOptions, 1) }

        intOptions = listOf(0, 0, 4, 5, 2)
        assertFailsWith<AssertionError> { assertContains(intOptions, 1) }

        stringOptions = setOf("hello", "world", "Hello World", "farewell planet")
        assertFailsWith<AssertionError> { assertContains(stringOptions, "hello world") }

        setValue = setOf(5, "hello", 'c', 1.5)
        setOptions = multiSetOf(setOf(5, "hello", 'c'), setOf("hello", 'c', 1.5), setOf('c', 1.5, "hello"))
        assertFailsWith<AssertionError> { assertContains(setOptions, setValue) }

        range = 5..9
        assertFailsWith<AssertionError> { assertContains(range, 10) }
        assertFailsWith<AssertionError> { assertContains(range, -3) }
    }

    @Test
    fun assertNotContainsTest() {
        // true
        var intOptions: Collection<Int> = setOf(-1)
        assertNotContains(intOptions, 1)

        intOptions = listOf(0, 0, 4, 5, 2)
        assertNotContains(intOptions, 1)

        var stringOptions = setOf("hello", "world", "Hello World", "farewell planet")
        assertNotContains(stringOptions, "hello world")

        var setValue = setOf(5, "hello", 'c', 1.5)
        var setOptions = multiSetOf(setOf(5, "hello", 'c'), setOf("hello", 'c', 1.5), setOf('c', 1.5, "hello"))
        assertNotContains(setOptions, setValue)

        var range = 5..9
        assertNotContains(range, 10)
        assertNotContains(range, -3)

        // false
        intOptions = listOf(5)
        assertFailsWith<AssertionError> { assertNotContains(intOptions, 5) }

        intOptions = listOf(5, 5, 5, 5, 5)
        assertFailsWith<AssertionError> { assertNotContains(intOptions, 5) }

        intOptions = listOf(15, 20, 23, -8, 2, 5, 4)
        assertFailsWith<AssertionError> { assertNotContains(intOptions, 5) }
        assertFailsWith<AssertionError> { assertNotContains(intOptions, -8) }

        stringOptions = setOf("hello", "world", "hello world", "farewell planet")
        assertFailsWith<AssertionError> { assertNotContains(stringOptions, "hello world") }

        setValue = setOf(5, "hello", 'c', 1.5)
        setOptions = multiSetOf(setOf(5, "hello", 'c', 1.5), setOf(5, "hello", 'c', 1.5), setOf(5, 1.5))
        assertFailsWith<AssertionError> { assertNotContains(setOptions, setValue) }

        range = 5..10
        assertFailsWith<AssertionError> { assertNotContains(range, 10) }
    }
}
