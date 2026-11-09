package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.multiset.ConstMultiSet
import xyz.lbres.kotlinutils.collection.multiset.MultiSet
import xyz.lbres.kotlinutils.collection.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test

class ConstMultiSetImplTest : AbstractMultiSetTestClass() {
    override val createSet: (Collection<*>) -> MultiSet<*> = { ConstMultiSetImpl(it) }
    override val createOtherSet: (Collection<*>) -> MultiSet<*> = { MutableMultiSetImpl(it) }
    override val isMutableSet: Boolean = false
    override val hasMutableElements: Boolean = false

    override fun <T> runSingleConstructorTest(map: Map<String, Any>) {
        @Suppress(Suppressions.UNCHECKED_CAST)
        val values: Collection<T> = map["values"] as Collection<T>
        val set: ConstMultiSet<T> = ConstMultiSetImpl(values)
        testConstructed(set, map)
    }

    @Test
    fun testMinusC() {
        runOpTest(::runMinusTests, ::runMutableElementMinusTests, const = true, other = ::ConstMutableMultiSetImpl)
    }

    @Test
    fun testPlusC() {
        runOpTest(::runPlusTests, ::runMutableElementPlusTests, const = true, other = ::ConstMutableMultiSetImpl)
    }

    @Test
    fun testIntersectC() {
        runOpTest(::runPlusTests, ::runMutableElementPlusTests, const = true, other = ::ConstMutableMultiSetImpl)
    }
}
