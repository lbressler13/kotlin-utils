package xyz.lbres.kotlinutils.utils.either

import kotlin.test.Test
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
        runTestEquals(mutable)

        // immutable
        val mutable = MutableEither<Int, List<Int>>(listOf(1, 2, 3))
        val immutable = Either<List<Int>, Int>(listOf(1, 2, 3))
        assertTrue(mutable == immutable)
        mutable.right = listOf(1, 2)
        assertFalse(mutable == immutable)
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

        val nullable = MutableEither<String?, Int>(123)
        nullable.left = "123"
        checkLeft(nullable, "123")
        nullable.left = null
        checkLeft(nullable, null)
    }

    @Test
    fun testSetRight() {
        var stringInt = MutableEither<String, Int>(123)
        stringInt.right = -6
        checkRight(stringInt, -6)

        stringInt = MutableEither.withLeft("hello")
        stringInt.right = 10
        checkRight(stringInt, 10)

        val nullable = MutableEither<Int, String?>(123)
        nullable.right = "123"
        checkRight(nullable, "123")
        nullable.right = null
        checkRight(nullable, null)
    }

    @Test fun testToString() = runTestToString(mutable)
}
