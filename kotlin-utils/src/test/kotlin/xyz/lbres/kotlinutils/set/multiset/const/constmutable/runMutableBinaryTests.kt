package xyz.lbres.kotlinutils.set.multiset.const.constmutable

import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetIntersectTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMinusTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetPlusTests

fun runMutableConstMinusTests() {
    runMultiSetMinusTests(
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { MultiSetImpl(it) }
    )
}

fun runMutableConstPlusTests() {
    runMultiSetPlusTests(
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { MultiSetImpl(it) }
    )
}

fun runMutableConstIntersectTests() {
    runMultiSetIntersectTests(
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { MultiSetImpl(it) }
    )
}
