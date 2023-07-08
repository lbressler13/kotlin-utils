package xyz.lbres.kotlinutils.collection.boolean.ext

import xyz.lbres.kotlinutils.list.BoolList
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanCollectionExtTest {
    @Test
    fun testAll() {
        // true
        var list: BoolList = emptyList()
        assertTrue(list.all())

        list = listOf(true)
        assertTrue(list.all())

        list = listOf(true, true, true)
        assertTrue(list.all())

        var multiSet = multiSetOf(true, true)
        assertTrue(multiSet.all())

        // false
        list = listOf(false)
        assertFalse(list.all())

        list = listOf(false, false, false)
        assertFalse(list.all())

        multiSet = multiSetOf(false, false)
        assertFalse(multiSet.all())

        // mix
        val set = setOf(true, false)
        assertFalse(set.all())

        multiSet = multiSetOf(false, false)
        assertFalse(multiSet.all())

        multiSet = multiSetOf(false, true, true, false, true)
        assertFalse(multiSet.all())

        list = listOf(true, false, true, false, true, false, false)
        assertFalse(list.all())

        // mutable
        val mutableList = mutableListOf(true)
        assertTrue(mutableList.all())

        mutableList[0] = false
        assertFalse(mutableList.all())

        mutableList[0] = true
        mutableList.add(true)
        assertTrue(mutableList.all())

        mutableList.add(false)
        assertFalse(mutableList.all())
    }
}
