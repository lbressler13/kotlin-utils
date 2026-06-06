package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
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
        runTestEquals(mutable)

        // mutable
        val intList = Either<Int, List<Int>>(listOf(1, 2, 3))
        val mutable = MutableEither<List<Int>, Int>(listOf(1, 2, 3))
        assertTrue(intList == mutable)
        mutable.left = listOf(1)
        assertFalse(intList == mutable)
    }

    @Test fun testEqualsValue() = runTestEqualsValue(mutable)

    @Test fun testToString() = runTestToString(mutable = mutable)
}
