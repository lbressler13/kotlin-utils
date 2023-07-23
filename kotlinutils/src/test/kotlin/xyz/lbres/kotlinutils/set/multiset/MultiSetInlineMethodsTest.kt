package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.inline.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiSetInlineMethodsTest {
    @Test fun testMapToSet() = runMapToSetTests()
    @Test fun testFilterToSet() = runFilterToSetTests()
    @Test fun testFilterNotToSet() = runFilterNotToSetTests()

    @Test fun testMapConsistent() = runMapConsistentTests()
    @Test fun testFilterConsistent() = runFilterConsistentTests()
    @Test fun testFilterNotConsistent() = runFilterNotConsistentTests()

    @Test fun testMapToSetConsistent() = runMapToSetConsistentTests()
    @Test fun testFilterToSetConsistent() = runFilterToSetConsistentTests()
    @Test fun testFilterNotToSetConsistent() = runFilterNotToSetConsistentTests()

    @Test fun testAnyConsistent() = runAnyConsistentTests()
    @Test fun testAllConsistent() = runAllConsistentTests()
    @Test fun testNoneConsistent() = runNoneConsistentTests()

    @Test fun testMinByConsistent() = runMinByConsistentTests()
    @Test fun testMaxByConsistent() = runMaxByConsistentTests()
}
