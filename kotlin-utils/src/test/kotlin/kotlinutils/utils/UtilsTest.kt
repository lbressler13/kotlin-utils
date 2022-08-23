package kotlinutils.utils

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class UtilsTest {
    @Test
    internal fun testRepeat() {
        var baseString = ""
        repeat(20) { baseString += "a" }
        var expectedString = "aaaaaaaaaaaaaaaaaaaa"
        assertEquals(expectedString, baseString)

        baseString = "a"
        repeat(5) { baseString += baseString }
        expectedString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        assertEquals(expectedString, baseString)

        val booleans: MutableList<Boolean> = mutableListOf()
        val r = Random
        repeat(1000) { booleans.add(r.nextBoolean()) }
        val acceptedRange = 400..600
        assertContains(acceptedRange, booleans.count { it })
    }
}
