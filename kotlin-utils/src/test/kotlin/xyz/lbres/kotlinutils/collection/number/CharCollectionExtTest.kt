package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import xyz.lbres.kotlinutils.testutils.reflex
import kotlin.test.Test

class CharCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric(reflex(), Collection<Char>::filterNotZero)
    @Test fun testSum() = testSumGeneric(reflex(), Collection<Char>::sum)
    @Test fun testProduct() = testProductGeneric(reflex(), Collection<Char>::product)
}
