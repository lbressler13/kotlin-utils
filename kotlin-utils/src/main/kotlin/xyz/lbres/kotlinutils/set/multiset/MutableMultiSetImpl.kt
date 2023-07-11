package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.int.ext.isZero
import kotlin.math.min

// TODO lots of cleanup

/**
 * Mutable set implementation that allows multiple occurrences of the same value.
 */
internal class MutableMultiSetImpl<E> : MutableMultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int
        get() = _size // TODO maybe just list.size

    /**
     * Mutable variable to store number of elements in the set.
     */
    private var _size: Int

    /**
     * All distinct values contained in the MultiSet, without any counts
     */
    override val distinctValues: Set<E>
        get() {
            updateValues()
            return countsMap.keys
        }

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than zero.
     */
    private var countsMap: MutableMap<E, Int>

    /**
     * A list containing all elements in the set.
     */
    private var list: MutableList<E>

    /**
     * Store the hash codes for all the values in the set.
     * Used to determine if any mutable values have changed.
     */
    private var hashCodes: Map<Int, Int>

    /**
     * String representation of the set.
     */
    private var string: String

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        _size = elements.size
        countsMap = mutableMapOf()

        list = elements.toMutableList()
        string = createString()
        hashCodes = getCurrentHashCodes()

        for (element in elements) {
            countsMap[element] = getCountWithoutUpdate(element) + 1
        }
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        countsMap = counts.toMutableMap()
        _size = counts.values.fold(0, Int::plus)

        list = countsMap.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }.toMutableList()
        string = createString()
        hashCodes = getCurrentHashCodes()
    }

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been added successfully, false otherwise
     */
    override fun add(element: E): Boolean {
        updateValues()
        countsMap[element] = getCountWithoutUpdate(element) + 1
        _size++
        list.add(element)
        return true
    }

    /**
     * Add all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, the value will be added multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if any elements have been added successfully, false otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        updateValues()
        var someSucceeded = false

        for (element in elements) {
            someSucceeded = add(element) || someSucceeded
        }

        return someSucceeded
    }

    /**
     * Remove all elements from the set.
     */
    override fun clear() {
        countsMap.clear()
        list.clear()
        _size = 0
        updateValues()
    }

    /**
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
     */
    override fun remove(element: E): Boolean {
        updateValues()
        val currentCount = getCountWithoutUpdate(element)

        when (currentCount) {
            0 -> return false
            1 -> countsMap.remove(element)
            else -> countsMap[element] = currentCount - 1
        }

        list.remove(element)
        _size--
        return true
    }

    /**
     * Remove all specified elements from the set.
     * If [elements] contains a single occurrence of a value, only one occurrence of the value will be removed from the set.
     * If there are multiple occurrences, the value will be removed multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if any elements have been removed successfully, false otherwise
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        updateValues()
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false

        for (element in elements) {
            someSucceeded = remove(element) || someSucceeded
        }

        return someSucceeded
    }

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if elements have been retained successfully, false otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        updateValues()
        val other = MutableMultiSetImpl(elements)

        val updatedCounts: MutableMap<E, Int> = mutableMapOf()

        // cannot modify counts during loop, will throw a ConcurrentModificationException
        for (pair in countsMap) {
            val element = pair.key
            val currentCount = pair.value
            val newCount = min(currentCount, other.getCountOf(element))
            updatedCounts[element] = newCount
        }

        list.clear()
        updatedCounts.forEach {
            if (it.value == 0) {
                countsMap.remove(it.key)
            } else {
                countsMap[it.key] = it.value
                repeat(it.value) { _ ->
                    list.add(it.key)
                }
            }
        }

        _size = countsMap.values.fold(0, Int::plus)

        return true
    }

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: true if [element] is in the set, false otherwise
     */
    override fun contains(element: E): Boolean {
        updateValues()
        return countsMap.contains(element)
    }

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If [elements] contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as [elements].
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if the current set contains at least as many occurrences of each value as [elements]
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        updateValues()
        val newSet = MultiSetImpl(elements) // less overhead than creating a MutableMultiSetImpl
        return newSet.distinctValues.all {
            getCountWithoutUpdate(it) > 0 && newSet.getCountOf(it) <= getCountWithoutUpdate(it)
        }
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MutableMultiSet<E> {
        updateValues()
        val newCounts = distinctValues.associateWith {
            getCountWithoutUpdate(it) - other.getCountOf(it)
        }.filter { it.value > 0 }

        return MutableMultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MutableMultiSet<E> {
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            getCountWithoutUpdate(it) + other.getCountOf(it)
        }

        return MutableMultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [MultiSet]<E>: values to intersect with the MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing only values that are in both MultiSets
     */
    override infix fun intersect(other: MultiSet<E>): MutableMultiSet<E> {
        updateValues()
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            val count = getCountWithoutUpdate(it)
            val otherCount = other.getCountOf(it)
            min(count, otherCount)
        }.filter { it.value > 0 }

        return MutableMultiSetImpl(newCounts)
    }

    /**
     * Get hash codes for the elements
     *
     * @return [Map]<Int, Int>: hash codes for all elements in the set
     */
    private fun getCurrentHashCodes(): Map<Int, Int> {
        val hashCodeCounts: MutableMap<Int, Int> = mutableMapOf()

        list.forEach {
            val code = it.hashCode()
            hashCodeCounts[code] = (hashCodeCounts[code] ?: 0) + 1
        }

        return hashCodeCounts
    }

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = _size.isZero()

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int {
        updateValues()
        return getCountWithoutUpdate(element)
    }

    /**
     * Get number of occurrences of a given element, without updating the values in the counts map.
     * Assumes that values have already been updated.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    private fun getCountWithoutUpdate(element: E): Int = countsMap.getOrDefault(element, 0)

    /**
     * If two MutableMultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: true if [other] is a non-null MultiSet which contains the same values as the current set, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        updateValues()
        return try {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            if (other is MutableMultiSetImpl<*>) {
                other.updateValues()
                return countsMap == other.countsMap
            }

            // less efficient equality check
            val otherDistinct = other.distinctValues
            return distinctValues == otherDistinct && distinctValues.all { getCountWithoutUpdate(it) == other.getCountOf(it) }
        } catch (_: Exception) {
            false
        }
    }

    private fun updateValues() {
        val currentCodes = getCurrentHashCodes()
        if (currentCodes != hashCodes) {
            string = createString()

            countsMap = list.groupBy { it }.map { it.key to it.value.size }.toMap().toMutableMap()
            hashCodes = currentCodes
        }
    }

    private fun createString(): String {
        if (size == 0) {
            return "[]"
        }

        val elementsString = list.joinToString(", ")
        return "[$elementsString]"
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): MutableIterator<E> {
        // updateList()
        return list.toMutableList().iterator()
    }

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        // updateList()
        updateValues()
        return string
    }

    override fun hashCode(): Int = listOf(javaClass.name, countsMap).hashCode()
}
