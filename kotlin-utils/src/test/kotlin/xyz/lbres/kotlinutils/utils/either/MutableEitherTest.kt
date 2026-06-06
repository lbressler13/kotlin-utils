package xyz.lbres.kotlinutils.utils.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MutableEitherTest {
    private val mutable = true

    @Test fun testConstructor() = runTestConstructor(mutable)
    @Test fun testWithLeft() = runTestWithLeft(mutable)
    @Test fun testWithRight() = runTestWithRight(mutable)

    @Test fun testIsNull() = runTestIsNull(mutable)
    @Test fun testToEither() = runTestToEither(mutable)

    @Test
    fun testEquals() {
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

    @Test fun testEqualsValue() = runTestEqualsValue(mutable)

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

    @Test fun testToString() = runTestToString(mutable)
}
