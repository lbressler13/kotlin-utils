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
    }

    @Test
    fun testSetLeft() {

        // nullable

        // nested

    }

    @Test
    fun testSetRight() {
    }

    @Test
    fun testToString() {
        val intString = MutableEither<Int, String>(123)
        assertEquals("Either(123)", intString.toString())

        intString.right = "hello"
        assertEquals("Either(hello)", intString.toString())
    }
}
