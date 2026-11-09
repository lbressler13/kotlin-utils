package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.multiset.MultiSet
import xyz.lbres.kotlinutils.collection.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.collection.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test

class MutableMultiSetImplTest : AbstractMultiSetTestClass() {
    override val createMutableSet: (Collection<*>) -> MutableMultiSet<*> = { MutableMultiSetImpl(it) }
    override val createSet: (Collection<*>) -> MultiSet<*>
        get() = createMutableSet
    override val createOtherSet: (Collection<*>) -> MultiSet<*> = { ConstMultiSetImpl(it) }
    override val isMutableSet: Boolean = true
    override val hasMutableElements: Boolean = true

    override fun <T> runSingleConstructorTest(map: Map<String, Any>) {
        @Suppress(Suppressions.UNCHECKED_CAST)
        val values = map["values"] as Collection<T>
        val set = MutableMultiSetImpl(values)
        testConstructed(set, map)
    }

    @Test fun testClear() = runClearTests(createMutableSet)
    @Test fun testAdd() = runMutableElementsAddTests()
    @Test fun testAddAll() = runMutableElementsAddAllTests()
    @Test fun testRemove() = runMutableElementsRemoveTests()
    @Test fun testRemoveAll() = runMutableElementsRemoveAllTests()
    @Test fun testRetainAll() = runMutableElementsRetainAllTests()
}
