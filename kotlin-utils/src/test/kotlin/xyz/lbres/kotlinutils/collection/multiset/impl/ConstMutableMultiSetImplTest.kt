package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.list.IntList
import xyz.lbres.kotlinutils.collection.multiset.ConstMutableMultiSet
import xyz.lbres.kotlinutils.collection.multiset.MultiSet
import xyz.lbres.kotlinutils.collection.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.collection.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.CompList
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.Test

class ConstMutableMultiSetImplTest : AbstractMultiSetTestClass() {
    override val createMutableSet: (Collection<*>) -> MutableMultiSet<*> = { ConstMutableMultiSetImpl(it) }
    override val createSet: (Collection<*>) -> MultiSet<*>
        get() = createMutableSet
    override val createOtherSet: (Collection<*>) -> MultiSet<*> = { MultiSetImpl(it) }
    override val isMutableSet: Boolean = true
    override val hasMutableElements: Boolean = false

    @Test
    override fun testConstructor() {
        fun <T> runTest(map: Map<String, Any>) {
            @Suppress(Suppressions.UNCHECKED_CAST)
            val values: Collection<T> = map["values"] as Collection<T>
            val set: ConstMutableMultiSet<T> = ConstMutableMultiSetImpl(values)
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

    @Test fun testClear() = runClearTests(createMutableSet)
    @Test fun testAdd() = runAddTests(createMutableSet)
    @Test fun testAddAll() = runAddAllTests(createMutableSet)
    @Test fun testRemove() = runRemoveTests(createMutableSet)
    @Test fun testRemoveAll() = runRemoveAllTests(createMutableSet)
    @Test fun testRetainAll() = runClearTests(createMutableSet)


    @Test
    fun testMinusC() {
        runOpTest(::runMinusTests, ::runMutableElementMinusTests, const = true, other = ::ConstMultiSetImpl)
    }

    @Test
    fun testPlusC() {
        runOpTest(::runPlusTests, ::runMutableElementPlusTests, const = true, other = ::ConstMultiSetImpl)
    }

    @Test
    fun testIntersectC() {
        runOpTest(::runPlusTests, ::runMutableElementPlusTests, const = true, other = ::ConstMultiSetImpl)
    }
}
