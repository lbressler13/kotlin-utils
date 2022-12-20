package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.assertEmpty
import kotlin.test.assertEquals

fun runImmutableMinusTests() {
    // empty
    var ms1 = emptyMultiSet<Int>()
    var ms2 = emptyMultiSet<Int>()
    assertEmpty(ms1 - ms2)
    assertEmpty(ms2 - ms1)

    ms1 = multiSetOf(1, 2, 3, 3)
    assertEquals(ms1, ms1 - ms2)
    assertEmpty(ms2 - ms1)

    // equal
    ms1 = multiSetOf(1, 2, 3, 4, 5)
    assertEmpty(ms1 - ms1)

    var listMs1 = multiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEmpty(listMs1 - listMs1)

    // all shared
    ms1 = multiSetOf(1, 1, 2, 3, 4, 4, 4)
    ms2 = multiSetOf(1, 2, 2, 3, 4, 4)
    var expected = multiSetOf(1, 4)
    assertEquals(expected, ms1 - ms2)
    expected = multiSetOf(2)
    assertEquals(expected, ms2 - ms1)

    ms1 = multiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    ms2 = multiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expected = multiSetOf(2, 2, 6)
    assertEquals(expected, ms1 - ms2)
    expected = multiSetOf(1, 5, 5, 7)
    assertEquals(expected, ms2 - ms1)

    // none shared
    ms1 = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    ms2 = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(ms1, ms1 - ms2)
    assertEquals(ms2, ms2 - ms1)

    val sms1 = multiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val sms2 = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(sms1, sms1 - sms2)
    assertEquals(sms2, sms2 - sms1)

    // some shared
    ms1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
    ms2 = multiSetOf(1, 1, 5, 6, 6, 7)
    expected = multiSetOf(2, 3, 4, 5)
    assertEquals(expected, ms1 - ms2)
    expected = multiSetOf(6, 6, 7)
    assertEquals(expected, ms2 - ms1)

    listMs1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listMs2: MultiSet<List<Int>> = multiSetOf(listOf(), listOf(1, 2, 3))
    var lExpected = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(lExpected, listMs1 - listMs2)
    lExpected = multiSetOf(listOf())
    assertEquals(lExpected, listMs2 - listMs1)

    val e1 = ArithmeticException()
    val e2 = NullPointerException()
    val e3 = IllegalArgumentException()
    val ems1: MultiSet<Exception> = multiSetOf(e1, e2, e1, e1, e2)
    val ems2: MultiSet<Exception> = multiSetOf(e1, e3, e3, e1, e1)
    var eExpected: MultiSet<Exception> = multiSetOf(e2, e2)
    assertEquals(eExpected, ems1 - ems2)
    eExpected = multiSetOf(e3, e3)
    assertEquals(eExpected, ems2 - ems1)
}

fun runImmutablePlusTests() {
    // empty
    var ms1 = emptyMultiSet<Int>()
    var ms2 = emptyMultiSet<Int>()
    assertEmpty(ms1 + ms2)
    assertEmpty(ms2 + ms1)

    ms1 = multiSetOf(1, 2, 3, 3)
    assertEquals(ms1, ms1 + ms2)
    assertEquals(ms1, ms2 + ms1)

    // non-empty
    ms1 = multiSetOf(1)
    ms2 = multiSetOf(1)
    var expected = multiSetOf(1, 1)
    assertEquals(expected, ms1 + ms2)
    assertEquals(expected, ms2 + ms1)

    ms1 = multiSetOf(1, 2, 2, 3, 3, 3)
    ms2 = multiSetOf(1, 2, 0)
    expected = multiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expected, ms1 + ms2)
    assertEquals(expected, ms2 + ms1)

    val sms1 = multiSetOf("", "hello", "world", "goodbye")
    val sms2 = multiSetOf("hi", "no", "bye")
    val sExpected = multiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(sExpected, sms1 + sms2)
    assertEquals(sExpected, sms2 + sms1)

    val listMs1 = multiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listMs2 = multiSetOf(listOf(), listOf(1, 2, 3))
    val lExpected = multiSetOf(listOf(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(lExpected, listMs1 + listMs2)
    assertEquals(lExpected, listMs2 + listMs1)

    val e1 = ArithmeticException()
    val e2 = NullPointerException()
    val e3 = IllegalArgumentException()
    val ems1: MultiSet<Exception> = multiSetOf(e1, e2, e1, e2)
    val ems2: MultiSet<Exception> = multiSetOf(e1, e3, e3, e2, e1, e1)
    val eExpected: MultiSet<Exception> = multiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(eExpected, ems1 + ems2)
    assertEquals(eExpected, ems2 + ems1)
}
