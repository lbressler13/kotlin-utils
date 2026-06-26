package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import xyz.lbres.kotlinutils.testutils.reflex
import kotlin.test.Test

class IntCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric(reflex(), Collection<Int>::filterNotZero)
    @Test fun testSum() = testSumGeneric(reflex(), Collection<Int>::sum)
    @Test fun testProduct() = testProductGeneric(reflex(), Collection<Int>::product)
}
