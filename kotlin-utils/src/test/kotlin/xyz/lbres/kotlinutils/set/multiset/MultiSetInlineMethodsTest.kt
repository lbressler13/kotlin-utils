package xyz.lbres.kotlinutils.set.multiset

import kotlin.test.Test
import xyz.lbres.kotlinutils.set.multiset.inline.* // ktlint-disable no-wildcard-imports no-unused-imports

internal class MultiSetInlineMethodsTest {
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
