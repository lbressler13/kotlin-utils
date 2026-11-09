package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.list.IntList
import xyz.lbres.kotlinutils.collection.multiset.ConstMultiSet
import xyz.lbres.kotlinutils.collection.multiset.MultiSet
import xyz.lbres.kotlinutils.collection.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.CompList
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.Test

class ConstMultiSetImplTest: AbstractMultiSetTestClass() {
    override val createSet: (Collection<*>) -> MultiSet<*> = { ConstMultiSetImpl(it) }
    override val createOtherSet: (Collection<*>) -> MultiSet<*> = { MutableMultiSetImpl(it) }
    override val isMutableSet: Boolean = false
    override val hasMutableElements: Boolean = false

    @Test
    override fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values: Collection<T> = map["values"] as Collection<T>
            val set: ConstMultiSet<T> = ConstMultiSetImpl(values)
            testConstructed(set, map)
        }

        multiSetConstructorIntTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<Int>(it) }
        }
        multiSetConstructorExceptionTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<Exception>(it) }
        }
        multiSetConstructorIntListTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<IntList>(it) }
        }
        multiSetConstructorCompListTestValues.forEach {
            runWithFailMessage("Testing constructor with $it") { runTest<CompList>(it) }
        }
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
