package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class FloatExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2f }

        var float = 0f
        var expected = 2f
        assertEquals(expected, float.ifZero(getValue))

        float = -0f
        assertEquals(expected, float.ifZero(getValue))

        float = 2f
        expected = 2f
        assertEquals(expected, float.ifZero(getValue))

        float = 15f
        expected = 15f
        assertEquals(expected, float.ifZero(getValue))

        float = -0.123f
        expected = -0.123f
        assertEquals(expected, float.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        var float = 0f
        assertFalse(float.isNegative())

        float = -0f
        assertFalse(float.isNegative())

        float = 0.000000000001f
        assertFalse(float.isNegative())

        float = 100f
        assertFalse(float.isNegative())

        float = -0.000000000001f
        assertTrue(float.isNegative())

        float = -100f
        assertTrue(float.isNegative())
    }

    @Test
    fun testIsZero() {
        var float = 0f
        assertTrue(float.isZero())

        float = -0f
        assertTrue(float.isZero())

        float = 0.000000000001f
        assertFalse(float.isZero())

        float = -0.000000000001f
        assertFalse(float.isZero())

        float = 100f
        assertFalse(float.isZero())

        float = -100f
        assertFalse(float.isZero())
    }

    @Test
    fun testIsNullOrZero() {
        var float: Float? = null
        assertTrue(float.isNullOrZero())

        float = 0f
        assertTrue(float.isNullOrZero())

        float = -0f
        assertTrue(float.isNullOrZero())

        float = 0.000000000001f
        assertFalse(float.isNullOrZero())

        float = -0.000000000001f
        assertFalse(float.isNullOrZero())

        float = 100f
        assertFalse(float.isNullOrZero())

        float = -100f
        assertFalse(float.isNullOrZero())
    }
}
