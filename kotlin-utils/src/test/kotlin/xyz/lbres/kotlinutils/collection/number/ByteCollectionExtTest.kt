package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class ByteCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Byte, Collection<Byte>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Byte, Collection<Byte>>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Byte, Collection<Byte>>({ it }) { it.product() }
}
