package xyz.lbres.kotlinutils.set.multiset.const

import org.junit.Test

class ConstMultiSetInlineMethodsTest {
    @Test fun testMapToConstSet() = runMapToConstSetTests()
    @Test fun testFilterToConstSet() = runFilterToConstSetTests()
    @Test fun testFilterNotToConstSet() = runFilterNotToConstSetTests()
    @Test fun testMapToConstSetConsistent() = runMapToConstSetConsistent()
    @Test fun testFilterToConstSetConsistent() = runFilterToConstSetConsistentTests()
    @Test fun testFilterNotToConstSetConsistent() = runFilterNotToConstSetConsistentTests()
}
