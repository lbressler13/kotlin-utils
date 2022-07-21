package kotlinutils.int.ext

import kotlin.test.Test
import kotlin.test.assertEquals

internal class IntExtTest {
    @Test
    internal fun testIfZero() {
        val getValue = { 2 }

        var int = 0
        var expected = 2
        assertEquals(expected, int.ifZero(getValue))

        int = 2
        expected = 2
        assertEquals(expected, int.ifZero(getValue))

        int = 15
        expected = 15
        assertEquals(expected, int.ifZero(getValue))

        int = -100
        expected = -100
        assertEquals(expected, int.ifZero(getValue))
    }
}
