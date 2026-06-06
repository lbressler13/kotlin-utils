package xyz.lbres.kotlinutils.utils.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MutableEitherTest {
    @Test fun testConstructor() = runTestConstructor(mutable = true)
    @Test fun testWithLeft() = runTestWithLeft(mutable = true)
    @Test fun testWithRight() = runTestWithRight(mutable = true)

    @Test fun testIsNull() = runTestIsNull(mutable = true)

    @Test
    fun testEquals() {
        // TODO this can be shorter
        val intString = MutableEither<Int, String>(123)
        assertEquals(intString, intString)

        val arrInt = MutableEither<IntArray, Int>(123)
        bothEquals(intString, arrInt)

        arrInt.right = 12
        bothNotEquals(intString, arrInt)

        arrInt.right = 123
        bothEquals(intString, arrInt)

        arrInt.left = intArrayOf(123)
        bothNotEquals(intString, arrInt)

        intString.right = "123"
        bothNotEquals(intString, arrInt)

        val intList = MutableEither<Int, List<Int>>(emptyList())
        val stringList = MutableEither<Int, List<String>>(emptyList())
        bothEquals(intList, stringList)

        intList.right = listOf(1)
        bothNotEquals(intList, stringList)

        intList.left = 1
        stringList.left = 1
        bothEquals(intList, stringList)

        // nullable
        val nullable1 = MutableEither<Int?, String>(null)
        val nullable2 = MutableEither<List<Int>, List<Boolean>?>(null)
        bothEquals(nullable1, nullable2)

        nullable1.left = 5
        bothNotEquals(nullable1, nullable2)

        // nested
        intString.right = "123"
        val nested = MutableEither<List<Int>, MutableEither<String, Int>>(MutableEither("123"))
        bothEquals(nested, intString)

        nested.right!!.right = 12
        bothNotEquals(nested, intString)

        intString.left = 12
        bothEquals(nested, intString)

        // immutable
        intList.right = listOf(1, 2, 3)
        val immutable = Either<List<Int>, Int>(listOf(1, 2, 3))
        assertTrue(intList == immutable)

        intList.right = listOf(1, 2)
        assertFalse(intList == immutable)
    }

    @Test
    fun testEqualsValue() {
        val intString = MutableEither<Int, String>(123)
        assertTrue(intString.equalsValue(123))
        assertFalse(intString.equalsValue(12))
        assertFalse(intString.equalsValue("123"))

        intString.right = "hello"
        assertTrue(intString eqV "hello")
        assertFalse(intString eqV 123)

        // nested
        val nested = MutableEither<List<Int>, MutableEither<String, Int>>(MutableEither("123"))
        assertTrue(nested eqV "123")
        assertFalse(nested eqV 12)

        nested.right!!.right = 12
        assertFalse(nested eqV "123")
        assertTrue(nested eqV 12)

        nested.left = emptyList()
        assertTrue(nested eqV emptyList<Int>())

        nested.right = MutableEither("123")
        val nestedNested = MutableEither<String, MutableEither<List<Int>, MutableEither<String, Int>>>(nested)
        assertTrue(nestedNested eqV "123")
        nested.right!!.right = 12
        assertTrue(nested eqV 12)

        // nullable
        val nullable = MutableEither<Int?, Int>(null)
        assertTrue(nullable eqV null)
        nullable.left = 2
        assertTrue(nullable eqV 2)
        assertFalse(nullable eqV null)

        // immutable
        intString.left = 123
        val immutable = Either<Int, String>(123)
        assertTrue(intString eqV immutable)

        intString.right = ""
        assertFalse(intString eqV immutable)
    }

    @Test
    fun testSetLeft() {
        var intString = MutableEither<Int, String>(123)
        intString.left = -6
        checkLeft(intString, -6)

        intString = MutableEither.withRight("hello")
        intString.left = 10
        checkLeft(intString, 10)

        val stringInt = MutableEither<String?, Int>(123)
        stringInt.left = "123"
        checkLeft(stringInt, "123")
        stringInt.left = null
        checkLeft(stringInt, null)
    }

    @Test
    fun testSetRight() {
        var stringInt = MutableEither<String, Int>(123)
        stringInt.right = -6
        checkRight(stringInt, -6)

        stringInt = MutableEither.withLeft("hello")
        stringInt.right = 10
        checkRight(stringInt, 10)

        val intString = MutableEither<Int, String?>(123)
        intString.right = "123"
        checkRight(intString, "123")
        intString.right = null
        checkRight(intString, null)
    }

    @Test
    fun testToString() {
        val intString = MutableEither<Int, String>(123)
        assertEquals("Either(123)", intString.toString())

        intString.right = "hello"
        assertEquals("Either(hello)", intString.toString())

        val nullable = MutableEither<Int?, Int>(null)
        assertEquals("Either(null)", nullable.toString())

        nullable.left = 3
        assertEquals("Either(3)", nullable.toString())

        nullable.right = 3
        assertEquals("Either(3)", nullable.toString())

        val nested = MutableEither<List<Int>, MutableEither<String, Int>>(MutableEither("123"))
        assertEquals("Either(Either(123))", nested.toString())

        nested.right!!.right = 12
        assertEquals("Either(Either(12))", nested.toString())

        nested.left = listOf(1, 4)
        assertEquals("Either([1, 4])", nested.toString())
    }
}
