package xyz.lbres.kotlinutils.set.multiset.const.constmutable

import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.testutils.*
import kotlin.test.assertEquals

fun runAddTests() {
    runMultiSetAddTests(
        { ints -> ConstMutableMultiSet(ints) },
        { stringLists -> ConstMutableMultiSet(stringLists) },
    )
}

fun runAddAllTests() {
    runMultiSetAddAllTests { ints -> ConstMutableMultiSet(ints) }
}

fun runRemoveTests() {
    runMultiSetRemoveTests { ints -> ConstMutableMultiSet(ints) }
}

fun runRemoveAllTests() {
    runMultiSetRemoveAllTests { ints -> ConstMutableMultiSet(ints) }
}

fun runRetainAllTests() {
    runMultiSetRetainAllTests { ints -> ConstMutableMultiSet(ints) }
}

fun runClearTests() {
    runMultiSetClearTests { ints -> ConstMutableMultiSet(ints) }
}
