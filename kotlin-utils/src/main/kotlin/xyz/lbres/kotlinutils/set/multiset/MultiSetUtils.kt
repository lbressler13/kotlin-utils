package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMultiSet
import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet
import kotlin.math.min

/**
 * Create a MultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MultiSet]<E>
 */
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = elements.toList().toMultiSet()

/**
 * Create a MutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MutableMultiSet]<E>
 */
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = elements.toList().toMutableMultiSet()

/**
 * Create a MultiSet containing 0 elements.
 *
 * @return [MultiSet]<E>
 */
fun <E> emptyMultiSet(): MultiSet<E> = MultiSetImpl(emptyList())

/**
 * Create a MultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MultiSet]<E>
 */
fun <E> MultiSet(size: Int, init: (Int) -> E): MultiSet<E> = MultiSetImpl((0 until size).map(init))

/**
 * Create a MutableMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MutableMultiSet]<E>
 */
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): MutableMultiSet<E> = MutableMultiSetImpl((0 until size).map(init))

/**
 * Plus function that can be used in any MultiSet implementation.
 * May not be as efficient as implementation-specific code.
 *
 * @param set [MultiSet]<E>: first MultiSet
 * @param other [MultiSet]<E>: second MultiSet
 * @return [MultiSet]<E>: MultiSet containing all elements in both sets
 */
fun <E> defaultPlus(set: MultiSet<E>, other: MultiSet<E>): MultiSet<E> {
    val values = (set.distinctValues + other.distinctValues).flatMap { element ->
        val totalCount = set.getCountOf(element) + other.getCountOf(element)
        List(totalCount) { element }
    }

    return values.toMultiSet()
}

/**
 * Minus function that can be used in any MultiSet implementation.
 * May not be as efficient as implementation-specific code.
 *
 * @param set [MultiSet]<E>: MultiSet to subtract from
 * @param other [MultiSet]<E>: values to subtract from [set]
 * @return [MultiSet]<E>: MultiSet containing the elements in [set] but not in [other]
 */
fun <E> defaultMinus(set: MultiSet<E>, other: MultiSet<E>): MultiSet<E> {
    val values = set.distinctValues.flatMap { element ->
        val totalCount = set.getCountOf(element) - other.getCountOf(element)

        if (totalCount <= 0) {
            emptyList()
        } else {
            List(totalCount) { element }
        }
    }

    return values.toMultiSet()
}

/**
 * Intersect function that can be used in any MultiSet implementation.
 * May not be as efficient as implementation-specific code.
 *
 * @param set [MultiSet]<E>: first MultiSet
 * @param other [MultiSet]<E>: second MultiSet
 * @return [MultiSet]<E>: MultiSet containing only the elements that occur in both sets
 */
fun <E> defaultIntersect(set: MultiSet<E>, other: MultiSet<E>): MultiSet<E> {
    val values = (set.distinctValues intersect other.distinctValues).flatMap { element ->
        val totalCount = min(set.getCountOf(element), other.getCountOf(element))
        List(totalCount) { element }
    }

    return values.toMultiSet()
}
