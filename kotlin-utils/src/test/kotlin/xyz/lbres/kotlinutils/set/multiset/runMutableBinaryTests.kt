package xyz.lbres.kotlinutils.set.multiset

import kotlin.test.assertEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

internal fun runMutableMinusTests() {
    // empty
    var ms1 = mutableMultiSetOf<Int>()
    var ms2 = mutableMultiSetOf<Int>()
    assertEquals(emptyMultiSet(), ms1 - ms2)
    assertEquals(emptyMultiSet(), ms2 - ms1)

    ms1 = mutableMultiSetOf(1, 2, 3, 3)
    assertEquals(ms1, ms1 - ms2)
    assertEquals(emptyMultiSet(), ms2 - ms1)

    // equal
    ms1 = mutableMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(emptyMultiSet(), ms1 - ms1)

    var listMs1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEquals(emptyMultiSet(), listMs1 - listMs1)

    // all shared
    ms1 = mutableMultiSetOf(1, 1, 2, 3, 4, 4, 4)
    ms2 = mutableMultiSetOf(1, 2, 2, 3, 4, 4)
    var expected = mutableMultiSetOf(1, 4)
    assertEquals(expected, ms1 - ms2)
    expected = mutableMultiSetOf(2)
    assertEquals(expected, ms2 - ms1)

    ms1 = mutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    ms2 = mutableMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expected = mutableMultiSetOf(2, 2, 6)
    assertEquals(expected, ms1 - ms2)
    expected = mutableMultiSetOf(1, 5, 5, 7)
    assertEquals(expected, ms2 - ms1)

    // none shared
    ms1 = mutableMultiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    ms2 = mutableMultiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(ms1, ms1 - ms2)
    assertEquals(ms2, ms2 - ms1)

    val sms1 = mutableMultiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val sms2 = mutableMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(sms1, sms1 - sms2)
    assertEquals(sms2, sms2 - sms1)

    // some shared
    ms1 = mutableMultiSetOf(1, 1, 2, 3, 4, 5, 5)
    ms2 = mutableMultiSetOf(1, 1, 5, 6, 6, 7)
    expected = mutableMultiSetOf(2, 3, 4, 5)
    assertEquals(expected, ms1 - ms2)
    expected = mutableMultiSetOf(6, 6, 7)
    assertEquals(expected, ms2 - ms1)

    listMs1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listMs2: MutableMultiSet<List<Int>> = mutableMultiSetOf(listOf(), listOf(1, 2, 3))
    var lExpected = mutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(lExpected, listMs1 - listMs2)
    lExpected = mutableMultiSetOf(listOf())
    assertEquals(lExpected, listMs2 - listMs1)

    val ems1: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e2, e1, e1, e2)
    val ems2: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e3, e3, e1, e1)
    var eExpected: MutableMultiSet<Exception> = mutableMultiSetOf(e2, e2)
    assertEquals(eExpected, ems1 - ems2)
    eExpected = mutableMultiSetOf(e3, e3)
    assertEquals(eExpected, ems2 - ems1)

    val compListMs1: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf())
    var compListExpected: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - compListMs2)
    compListExpected = mutableMultiSetOf(listOf(1, 2, 3), listOf())
    assertEquals(compListExpected, compListMs2 - compListMs1)

    // with immutable
    ms1 = mutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expected = mutableMultiSetOf(2, 3)
    assertEquals(expected, ms1 - immutable)
}

internal fun runMutablePlusTests() {
    // empty
    var ms1 = emptyMultiSet<Int>()
    var ms2 = emptyMultiSet<Int>()
    assertEquals(emptyMultiSet(), ms1 + ms2)
    assertEquals(emptyMultiSet(), ms2 + ms1)

    ms1 = mutableMultiSetOf(1, 2, 3, 3)
    assertEquals(ms1, ms1 + ms2)

    // non-empty
    ms1 = mutableMultiSetOf(1)
    ms2 = mutableMultiSetOf(1)
    var expected = mutableMultiSetOf(1, 1)
    assertEquals(expected, ms1 + ms2)

    ms1 = mutableMultiSetOf(1, 2, 2, 3, 3, 3)
    ms2 = mutableMultiSetOf(1, 2, 0)
    expected = mutableMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expected, ms1 + ms2)

    val sms1 = mutableMultiSetOf("", "hello", "world", "goodbye")
    val sms2 = mutableMultiSetOf("hi", "no", "bye")
    val sExpected = mutableMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(sExpected, sms1 + sms2)
    assertEquals(sExpected, sms2 + sms1)

    val listMs1 = mutableMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listMs2 = mutableMultiSetOf(listOf(), listOf(1, 2, 3))
    val lExpected = mutableMultiSetOf(listOf(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(lExpected, listMs1 + listMs2)
    assertEquals(lExpected, listMs2 + listMs1)

    val ems1: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e2, e1, e2)
    val ems2: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val eExpected: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(eExpected, ems1 + ems2)
    assertEquals(eExpected, ems2 + ems1)

    val compListMs1: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf())
    val compListExpected: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 + compListMs2)
    assertEquals(compListExpected, compListMs2 + compListMs1)

    // with immutable
    ms1 = mutableMultiSetOf(1, 2, 3)
    val immutable = multiSetOf(1, 4, 5)
    expected = mutableMultiSetOf(1, 1, 2, 3, 4, 5)
    assertEquals(expected, ms1 + immutable)
}

internal fun runMutableIntersectTests() {
    // empty
    var ms1 = emptyMultiSet<Int>()

    var ms2 = emptyMultiSet<Int>()
    assertEquals(emptyMultiSet(), ms1.intersect(ms2))

    ms2 = mutableMultiSetOf(1, 2, 3)
    assertEquals(emptyMultiSet(), ms1.intersect(ms2))
    assertEquals(emptyMultiSet(), ms2.intersect(ms1))

    // none shared
    ms1 = mutableMultiSetOf(1, 2, 3)
    ms2 = mutableMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(emptyMultiSet(), ms1.intersect(ms2))
    assertEquals(emptyMultiSet(), ms2.intersect(ms1))

    var listMs1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listMs2 = mutableMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(emptyMultiSet(), listMs1.intersect(listMs2))
    assertEquals(emptyMultiSet(), listMs2.intersect(listMs1))

    // all shared
    ms1 = mutableMultiSetOf(1, 2, 3)
    ms2 = mutableMultiSetOf(1, 2, 3)
    var expected = mutableMultiSetOf(1, 2, 3)
    assertEquals(expected, ms1.intersect(ms2))
    assertEquals(expected, ms2.intersect(ms1))

    ms1 = mutableMultiSetOf(1, 1, 2, 2, 3, 3)
    ms2 = mutableMultiSetOf(1, 2, 2, 2, 3)
    expected = mutableMultiSetOf(1, 2, 2, 3)
    assertEquals(expected, ms1.intersect(ms2))
    assertEquals(expected, ms2.intersect(ms1))

    // some shared
    ms1 = mutableMultiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    ms2 = mutableMultiSetOf(-1, 14, 3, 9, 9, 6)
    expected = mutableMultiSetOf(-1, 6)
    assertEquals(expected, ms1.intersect(ms2))
    assertEquals(expected, ms2.intersect(ms1))

    listMs1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listMs2 = mutableMultiSetOf(listOf(), listOf(1, 2, 3))
    val lExpected = mutableMultiSetOf(listOf(1, 2, 3))
    assertEquals(lExpected, listMs1.intersect(listMs2))
    assertEquals(lExpected, listMs2.intersect(listMs1))

    val ems1: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e2, e1, e2)
    val ems2: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val eExpected: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e2)
    assertEquals(eExpected, ems1.intersect(ems2))
    assertEquals(eExpected, ems2.intersect(ems1))

    // with immutable
    ms1 = mutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expected = mutableMultiSetOf(1, 4)
    assertEquals(expected, ms1.intersect(immutable))
}
