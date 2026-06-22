package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class ByteCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Byte>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Byte>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Byte>::product)
}
