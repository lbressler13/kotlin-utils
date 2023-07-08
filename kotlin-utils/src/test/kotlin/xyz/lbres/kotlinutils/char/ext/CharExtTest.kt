package xyz.lbres.kotlinutils.char.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharExtTest {
    @Test
    fun testIfZero() {
        val getValue = { Char(2) }

        var char = Char(0)
        var expected = Char(2)
        assertEquals(expected, char.ifZero(getValue))

        char = Char(2)
        expected = Char(2)
        assertEquals(expected, char.ifZero(getValue))

        char = Char(15)
        expected = Char(15)
        assertEquals(expected, char.ifZero(getValue))
    }

    @Test
    fun testIsZero() {
        var char = Char(0)
        assertTrue(char.isZero())

        char = Char(1)
        assertFalse(char.isZero())

        char = Char(100)
        assertFalse(char.isZero())
    }
}
