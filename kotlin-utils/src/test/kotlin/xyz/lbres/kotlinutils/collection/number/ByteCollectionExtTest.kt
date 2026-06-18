package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductByte
import xyz.lbres.kotlinutils.testutils.number.testSumByte
import kotlin.test.Test

class ByteCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Byte, Collection<Byte>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumByte({ it }) { it.sum() }
    @Test fun testProduct() = testProductByte({ it }) { it.product() }
}
