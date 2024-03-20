package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.combineCounts
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

/**
 * Partial [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal abstract class AbstractMultiSetImpl<E>(initialElements: Collection<E>) : MultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int
        get() = elements.size

    /**
     * All distinct values contained in the MultiSet.
     */
    override val distinctValues: Set<E>
        get() = elements.toSet()

    /**
     * Elements in the set.
     */
    protected open val elements: Collection<E> = initialElements

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = elements.countElement(element)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean = elements.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If the collection contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as the collection.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if the current set contains at least as many occurrences of each value as the collection, `false` otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val counts = getCounts()
        val otherCounts = createCountsMap(elements)

        return otherCounts.all { (element, otherCount) ->
            otherCount <= counts.getOrDefault(element, 0)
        }
    }

    /**
     * If two MultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: `true` if [other] is a non-null MultiSet which contains the same values as the current set, `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        @Suppress(Suppressions.UNCHECKED_CAST)
        return tryOrDefault(false, listOf(ClassCastException::class)) {
            val counts = getCounts()
            val distinct = counts.keys

            // more efficient equality check for AbstractMultiSetImpl
            if (other is AbstractMultiSetImpl<*>) {
                other as AbstractMultiSetImpl<E>

                val otherCounts = other.getCounts()
                counts == otherCounts
            } else {
                other as MultiSet<E>
                size == other.size && distinct.all { counts[it] == other.getCountOf(it) }
            }
        }
    }

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
     */
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap.from(elements), other, ::min, useAllValues = false, const = false)
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap.from(elements), other, Int::minus, useAllValues = false, const = false)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap.from(elements), other, Int::plus, useAllValues = true, const = false)
    }

    /**
     * Create a mapping of each element in the set to the number of occurrences of the element.
     *
     * @return [Map]<E, Int>: mapping where keys are distinct elements in the set,
     * and values are the number of occurrences of the element in [elements]
     */
    protected fun getCounts(): Map<E, Int> = createCountsMap(elements)

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = elements.isEmpty()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        if (elements.isEmpty()) {
            return "[]"
        }

        val elementsString = elements.joinToString(", ")
        return "[$elementsString]"
    }

    /**
     * Generate hash code for set.
     *
     * @return [Int]
     */
    override fun hashCode(): Int {
        var result = getCounts().hashCode()
        result = 31 * result + MultiSet::class.java.name.hashCode()
        return result
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> = elements.toList().iterator()
}
