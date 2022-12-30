package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.consistent.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiSetInlineConsistentTest {
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
