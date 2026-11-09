package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.multiset.MultiSet
import xyz.lbres.kotlinutils.collection.multiset.testutils.testConstructed
import xyz.lbres.kotlinutils.internal.constants.Suppressions

class MultiSetImplTest : AbstractMultiSetTestClass() {
    override val createSet: (Collection<*>) -> MultiSet<*> = { MultiSetImpl(it) }
    override val createOtherSet: (Collection<*>) -> MultiSet<*> = { ConstMutableMultiSetImpl(it) }
    override val isMutableSet: Boolean = false
    override val hasMutableElements: Boolean = true

    override fun <T> runSingleConstructorTest(map: Map<String, Any>) {
        @Suppress(Suppressions.UNCHECKED_CAST)
        val values = map["values"] as Collection<T>
        val set = MultiSetImpl(values)
        testConstructed(set, map)
    }
}
