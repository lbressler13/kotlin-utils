package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class FloatCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Float>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Float>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Float>::product)
}
