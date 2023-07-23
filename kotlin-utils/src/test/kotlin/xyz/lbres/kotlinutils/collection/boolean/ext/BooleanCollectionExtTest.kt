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

    @Test
    fun testNone() {
        // true
        var list: BoolList = emptyList()
        assertTrue(list.none())

        list = listOf(false)
        assertTrue(list.none())

        list = listOf(false, false, false)
        assertTrue(list.none())

        var multiSet = multiSetOf(false, false)
        assertTrue(multiSet.none())

        // false
        list = listOf(true)
        assertFalse(list.none())

        list = listOf(true, true, true)
        assertFalse(list.none())

        multiSet = multiSetOf(true, true)
        assertFalse(multiSet.none())

        // mix
        val set = setOf(true, false)
        assertFalse(set.none())

        multiSet = multiSetOf(false, false)
        assertTrue(multiSet.none())

        multiSet = multiSetOf(false, true, true, false, true)
        assertFalse(multiSet.none())

        list = listOf(true, false, true, false, true, false, false)
        assertFalse(list.none())

        // mutable
        val mutableList = mutableListOf(true)
        assertFalse(mutableList.none())

        mutableList[0] = false
        assertTrue(mutableList.none())

        mutableList[0] = true
        mutableList.add(true)
        assertFalse(mutableList.none())

        mutableList.add(false)
        assertFalse(mutableList.none())
    }

    @Test
    fun testAny() {
        // empty
        var list: BoolList = emptyList()
        assertFalse(list.any())

        // true
        list = listOf(true)
        assertTrue(list.any())

        list = listOf(true, true, true)
        assertTrue(list.any())

        var multiSet = multiSetOf(true, true)
        assertTrue(multiSet.any())

        // false
        list = listOf(false)
        assertFalse(list.any())

        list = listOf(false, false, false)
        assertFalse(list.any())

        multiSet = multiSetOf(false, false)
        assertFalse(multiSet.any())

        // mix
        val set = setOf(true, false)
        assertTrue(set.any())

        multiSet = multiSetOf(false, false)
        assertFalse(multiSet.any())

        multiSet = multiSetOf(false, true, true, false, true)
        assertTrue(multiSet.any())

        list = listOf(true, false, true, false, true, false, false)
        assertTrue(list.any())

        // mutable
        val mutableList = mutableListOf(true)
        assertTrue(mutableList.any())

        mutableList[0] = false
        assertFalse(mutableList.any())

        mutableList[0] = true
        mutableList.add(true)
        assertTrue(mutableList.any())

        mutableList.add(false)
        assertTrue(mutableList.any())
    }
}
