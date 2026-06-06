package xyz.lbres.kotlinutils.utils.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MutableEitherTest {
    @Test
    fun testConstructor() {
    }

    @Test fun testWithLeft() = runTestWithLeft(mutable = true)
    @Test fun testWithRight() = runTestWithRight(mutable = true)

    @Test
    fun testEquals() {
        val intString = MutableEither<Int, String>(123)
        val arrInt = MutableEither<IntArray, Int>(123)
        assertTrue(intString == arrInt)

        arrInt.right = 12
        assertFalse(intString == arrInt)

        arrInt.right = 123
        assertTrue(intString == arrInt)

        arrInt.left = intArrayOf(123)
        assertFalse(intString == arrInt)
    }

    @Test
    fun testIsNull() {
    }

    @Test
    fun testEqualsValue() {
    }

    @Test
    fun testSetLeft() {
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
