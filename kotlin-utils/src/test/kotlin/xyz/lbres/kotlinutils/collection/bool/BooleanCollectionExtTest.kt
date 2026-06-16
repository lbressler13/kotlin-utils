package xyz.lbres.kotlinutils.collection.bool

import xyz.lbres.kotlinutils.collection.multiset.multiSetOf
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanCollectionExtTest {
    private val empty: List<Collection<Boolean>> = listOf(emptyList())
    private val trueValues = listOf(listOf(true), listOf(true, true, true), multiSetOf(true, true))
    private val falseValues = listOf(listOf(false), listOf(false, false, false), multiSetOf(false, false))
    private val mixedValues = listOf(
        setOf(true, false),
        multiSetOf(false, true, true, false, true),
        listOf(true, false, true, false, true, false, false),
    )

    @Test
    fun testAll() {
        checkTrueFalse(empty + trueValues, falseValues + mixedValues, { "$it.all()" }) { it.all() }

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
        checkTrueFalse(empty + falseValues, trueValues + mixedValues, { "$it.none()" }) { it.none() }

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
        checkTrueFalse(trueValues + mixedValues, empty + falseValues, { "$it.any()" }) { it.any() }

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
