package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class CharCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Char>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Char>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Char>::product)
}
