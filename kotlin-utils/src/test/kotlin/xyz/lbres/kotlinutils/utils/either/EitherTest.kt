package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class EitherTest {
    @Test fun testConstructor() = runTestConstructor(mutable = false)
    @Test fun testWithLeft() = runTestWithLeft(mutable = false)
    @Test fun testWithRight() = runTestWithRight(mutable = false)

    @Test
    fun testIsNull() {
        // not null
        var intString = Either<Int?, String?>("hello")
        assertFalse(intString.isNull())
        intString = Either(123)
        assertFalse(intString.isNull())

        var intInt = Either<Int, Int?>(4)
        assertFalse(intInt.isNull())

        intInt = Either.withRight(4)
        assertFalse(intInt.isNull())

        var listString = Either<List<String?>?, String?>("123")
        assertFalse(listString.isNull())

        listString = Either(listOf(null, null, null))
        assertFalse(listString.isNull())

        // null
        intString = Either(null)
        assertTrue(intString.isNull())

        intInt = Either.withRight(null)
        assertTrue(intInt.isNull())

        listString = Either.withLeft(null)
        assertTrue(listString.isNull())

        listString = Either.withRight(null)
        assertTrue(listString.isNull())
    }

    @Test
    fun testEquals() {
        val intString = Either<Int, String>(123)
        assertEquals(intString, intString)

        val arrInt = Either<IntArray, Int>(123)
        assertTrue(intString == arrInt)

        var stringInt = Either<String, Int>(123)
        assertTrue(intString == stringInt)

        stringInt = Either("123")
        assertFalse(intString == stringInt)

        val stringInt2 = Either<String, Int>("12")
        assertNotEquals(stringInt, stringInt2)

        var intList = Either<Int, List<Int>>(emptyList())
        var stringList = Either<Int, List<String>>(emptyList())
        assertTrue(intList == stringList)

        intList = Either<Int, List<Int>>(listOf(1, 2, 3))
        stringList = Either<Int, List<String>>(listOf("1"))
        assertFalse(intList == stringList)

        // nested
        stringInt = Either("123")
        val nested = Either<List<Int>, Either<String, Int>>(Either("123"))
        assertTrue(nested == stringInt)
        assertTrue(stringInt == nested)

        intList = Either<Int, List<Int>>(listOf(1, 2, 3))
        val mutable = MutableEither<List<Int>, Int>(listOf(1, 2, 3))
        assertTrue(intList == mutable)
        mutable.left = listOf(1)
        assertFalse(intList == mutable)
    }

    @Test
    fun testEqualsValue() {
        var intString = Either<Int, String>(123)
        assertTrue(intString eqV 123)
        assertFalse(intString eqV 12)
        assertFalse(intString eqV "123")

        intString = Either<Int, String>("hello")
        assertTrue(intString eqV "hello")
        assertFalse(intString eqV 123)

        val nested = Either<List<Int>, Either<String, Int>>(Either("123"))
        assertTrue(nested eqV "123")

        var nestedNested = Either<String, Either<List<Int>, Either<String, Int>>>(nested)
        assertTrue(nestedNested eqV "123")

        nestedNested = Either.withRight(Either.withRight(Either.withRight(5)))
        assertTrue(nestedNested eqV 5)

        val nullable = Either<Int?, Int>(null)
        assertTrue(nullable eqV null)
    }

    @Test
    fun testToString() {
        var intString = Either<Int, String>(123)
        assertEquals("Either(123)", intString.toString())

        intString = Either<Int, String>("hello")
        assertEquals("Either(hello)", intString.toString())

        val nullable = Either<Int?, Int>(null)
        assertEquals("Either(null)", nullable.toString())

        var nested = Either<List<Int>, Either<String, Int>>(Either("123"))
        assertEquals("Either(Either(123))", nested.toString())

        nested = Either.withRight(Either.withRight(12))
        assertEquals("Either(Either(12))", nested.toString())

        nested = Either(listOf(1, 4))
        assertEquals("Either([1, 4])", nested.toString())
    }
}
