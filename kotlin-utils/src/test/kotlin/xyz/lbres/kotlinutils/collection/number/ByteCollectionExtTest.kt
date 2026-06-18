package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroByte
import xyz.lbres.kotlinutils.testutils.number.testProductByte
import xyz.lbres.kotlinutils.testutils.number.testSumByte
import kotlin.test.Test

class ByteCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroByte({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumByte({ it }) { it.sum() }
    @Test fun testProduct() = testProductByte({ it }) { it.product() }
}
