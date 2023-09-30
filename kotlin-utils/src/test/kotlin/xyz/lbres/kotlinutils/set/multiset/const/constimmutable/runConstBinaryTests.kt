package xyz.lbres.kotlinutils.set.multiset.const.constimmutable

import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetIntersectTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMinusTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetPlusTests

fun runConstMinusTests() {
    runMultiSetMinusTests(
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { MutableMultiSetImpl(it) }
    )
}

fun runConstPlusTests() {
    runMultiSetPlusTests(
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { MutableMultiSetImpl(it) }
    )
}

fun runConstIntersectTests() {
    runMultiSetIntersectTests(
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { ConstMultiSetImpl(it) },
        { MutableMultiSetImpl(it) }
    )
}
