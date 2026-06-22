package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import xyz.lbres.kotlinutils.testutils.reflex
import kotlin.test.Test

class LongCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric(reflex(), Collection<Long>::filterNotZero)
    @Test fun testSum() = testSumGeneric(reflex(), Collection<Long>::sum)
    @Test fun testProduct() = testProductGeneric(reflex(), Collection<Long>::product)
}
