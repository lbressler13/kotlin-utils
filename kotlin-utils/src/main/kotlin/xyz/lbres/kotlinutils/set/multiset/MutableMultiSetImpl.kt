package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.min

/**
 * Mutable set implementation that allows multiple occurrences of the same value.
 */
internal class MutableMultiSetImpl<E> : AbstractMultiSet<E>, MutableMultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int
        get() = list.size

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
    override var countsMap: Map<E, Int>

    /**
     * A list containing all elements in the set.
     */
    private var list: MutableList<E>

    override val hashElements: Collection<E>
        get() = list

    /**
     * Store the hash codes for all the values in the set.
     * Used to determine if any mutable values have changed.
     */
    override var hashCodes: Map<Int, Int>

    /**
     * String representation of the set.
     */
    override var string: String

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        val mutableCounts: MutableMap<E, Int> = mutableMapOf()

        list = elements.toMutableList()
        string = createString()
        hashCodes = getCurrentHashCodes()

        for (element in elements) {
            mutableCounts[element] = mutableCounts.getOrDefault(element, 0) + 1
        }

        countsMap = mutableCounts
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        countsMap = counts.toMutableMap()

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
        (countsMap as MutableMap)[element] = getCountWithoutUpdate(element) + 1
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
        (countsMap as MutableMap).clear()
        list.clear()
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
            1 -> (countsMap as MutableMap).remove(element)
            else -> (countsMap as MutableMap)[element] = currentCount - 1
        }

        list.remove(element)
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
                (countsMap as MutableMap).remove(it.key)
            } else {
                (countsMap as MutableMap)[it.key] = it.value
                repeat(it.value) { _ ->
                    list.add(it.key)
                }
            }
        }

        return true
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MutableMultiSet<E> {
        return super<AbstractMultiSet>.minus(other) as MutableMultiSet<E>
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MutableMultiSet<E> {
        return super<AbstractMultiSet>.plus(other) as MutableMultiSet<E>
    }

    override fun createFromCountsMap(counts: Map<E, Int>): MultiSet<E> {
        return MutableMultiSetImpl(counts)
    }

    /**
     * If the hash code values are changed, update all properties related to the values of the set.
     * Overrides default to use mutable map.
     */
    override fun updateValues() {
        val currentCodes = getCurrentHashCodes()
        if (currentCodes != hashCodes) {
            string = createString()

            countsMap = list.groupBy { it }.map { it.key to it.value.size }.toMap().toMutableMap()
            hashCodes = currentCodes
        }
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): MutableIterator<E> = list.toMutableList().iterator()
}
