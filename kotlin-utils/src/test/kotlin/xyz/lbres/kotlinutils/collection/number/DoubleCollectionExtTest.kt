package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class DoubleCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Double>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Double>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Double>::product)
}
