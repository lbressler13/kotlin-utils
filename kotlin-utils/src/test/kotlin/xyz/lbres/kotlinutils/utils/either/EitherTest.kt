package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.REMOVE_EXPLICIT_TYPES)
class EitherTest {
    private val mutable = false

    @Test fun testConstructor() = runTestConstructor(mutable)
    @Test fun testWithLeft() = runTestWithLeft(mutable)
    @Test fun testWithRight() = runTestWithRight(mutable)

    @Test fun testIsNull() = runTestIsNull(mutable)
    @Test fun testToMutableEither() = runTestToEither(mutable)

    @Test
    fun testEquals() {
        val intString = Either<Int, String>(123)
        assertEquals(intString, intString)

        val arrInt = Either<IntArray, Int>(123)
        bothEquals(intString, arrInt)

        var stringInt = Either<String, Int>(123)
        bothEquals(intString, stringInt)

        stringInt = Either("123")
        bothNotEquals(intString, stringInt)

        val stringInt2 = Either<String, Int>("12")
        bothNotEquals(stringInt, stringInt2)

        var intList = Either<Int, List<Int>>(emptyList())
        var stringList = Either<Int, List<String>>(emptyList())
        bothEquals(intList, stringList)

        intList = Either<Int, List<Int>>(listOf(1, 2, 3))
        stringList = Either<Int, List<String>>(listOf("1"))
        bothNotEquals(intList, stringList)

        // null
        val nullable1 = Either<Int?, String>(null)
        val nullable2 = Either<List<Int>, List<Boolean>?>(null)
        bothEquals(nullable1, nullable2)

        // nested
        stringInt = Either("123")
        val nested = Either<List<Int>, Either<String, Int>>(Either("123"))
        bothEquals(nested, stringInt)

        // mutable
        intList = Either<Int, List<Int>>(listOf(1, 2, 3))
        val mutable = MutableEither<List<Int>, Int>(listOf(1, 2, 3))
        assertTrue(intList == mutable)
        mutable.left = listOf(1)
        assertFalse(intList == mutable)
    }

    @Test
    fun testEqualsValue() {
        var intString = Either<Int, String>(123)
        assertTrue(intString.equalsValue(123))
        assertFalse(intString.equalsValue(12))
        assertFalse(intString.equalsValue("123"))

        intString = Either<Int, String>("hello")
        assertTrue(intString eqV "hello")
        assertFalse(intString eqV 123)

        val nested = Either<List<Int>, Either<String, Int>>(Either("123"))
        assertTrue(nested eqV "123")

        var nestedNested = Either<String, Either<List<Int>, Either<String, Int>>>(nested)
        assertTrue(nestedNested eqV "123")

        nestedNested = Either.withRight(Either.withRight(Either.withRight(5)))
        assertTrue(nestedNested eqV 5)

        // nullable
        val nullable = Either<Int?, Int>(null)
        assertTrue(nullable eqV null)

        // mutable
        intString = Either<Int, String>(123)
        val mutable = MutableEither<Int, String>(123)
        assertTrue(intString eqV mutable)

        mutable.left = 12
        assertFalse(intString eqV mutable)
    }

    @Test fun testToString() = runTestToString(mutable = mutable)
}
