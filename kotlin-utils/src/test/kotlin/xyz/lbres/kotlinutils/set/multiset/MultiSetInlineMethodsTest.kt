package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.inline.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiSetInlineMethodsTest {
    @Test fun testMapToSet() = runMapToSetTests()
    @Test fun testFilterToSet() = runFilterToSetTests()
    @Test fun testFilterNotToSet() = runFilterNotToSetTests()

    @Test fun testMapConsistent() = runMapConsistentTests()
    @Test fun testFilterConsistent() = runFilterConsistentTests() // TODO
    @Test fun testFilterNotConsistent() = runFilterNotConsistentTests() // TODO

    @Test fun testMapToSetConsistent() = runMapToSetConsistentTests()
    @Test fun testFilterToSetConsistent() = runFilterToSetConsistentTests() // TODO
    @Test fun testFilterNotToSetConsistent() = runFilterNotToSetConsistentTests() // TODO

    @Test fun testAnyConsistent() = runAnyConsistentTests()
    @Test fun testAllConsistent() = runAllConsistentTests()
    @Test fun testNoneConsistent() = runNoneConsistentTests()

    @Test fun testMinByConsistent() = runMinByConsistentTests() // TODO
    @Test fun testMaxByConsistent() = runMaxByConsistentTests() // TODO
}
