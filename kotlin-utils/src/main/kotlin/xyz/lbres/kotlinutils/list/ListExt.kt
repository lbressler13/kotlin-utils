package xyz.lbres.kotlinutils.list

import xyz.lbres.kotlinutils.collection.multiset.impl.ConstMultiSetImpl
import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Create a copy of a list, with one value changed
 *
 * @param index [Int]: index of value to change
 * @param value T: value to substitute at index
 * @return [List]: list identical to this, with the exception of the value at index i
 * @throws IndexOutOfBoundsException if index is less than zero or greater than lastIndex
 */
fun <T> List<T>.copyWithReplacement(index: Int, value: T): List<T> {
    val before = subList(0, index)
    val after = simpleIf(index == lastIndex, { emptyList() }, { subList(index + 1, size) })

    return before + value + after
}

/**
 * Create a copy of a list, with the last value changed
 *
 * @param value T: new value for last index
 * @return [List]: list identical to this, with the exception of the value at the last index
 */
fun <T> List<T>.copyWithLastReplaced(value: T): List<T> = copyWithReplacement(lastIndex, value)

/**
 * Create a copy of a list, with the first value changed
 *
 * @param value T: new value for first index
 * @return [List]: list identical to this, with the exception of the value at the first index
 */
fun <T> List<T>.copyWithFirstReplaced(value: T): List<T> = copyWithReplacement(0, value)

/**
 * Create a copy of a list, without the last value
 *
 * @return [List]: list identical to this, with the last value removed
 */
fun <T> List<T>.copyWithoutLast(): List<T> = subList(0, lastIndex)

/**
 * If list consists of a single value
 *
 * @return [Boolean]: true if list consists of single value, false otherwise
 */
fun <T> List<T>.isSingleValue(): Boolean = size == 1

/**
 * Determine if the elements in this list are identical to the elements in another list,
 * even if elements are not in the same order.
 *
 * @param other [List]<E>: list to check element equality
 * @return [Boolean] `true` if the lists contain identical elements, `false` otherwise
 */
fun <E> List<E>.elementsEqual(other: List<E>): Boolean {
    return ConstMultiSetImpl(this) == ConstMultiSetImpl(other)
}

/**
 * Multiply a list by a number
 *
 * @param other [Int]
 * @return [List]: list containing all the elements of this list, replicated [other] number of times
 */
operator fun <E> List<E>.times(other: Int): List<E> = List(other) { this }.flatten()
