package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.iterable.ext.countElement
import kotlin.math.min

/**
 * Mutable set implementation that allows multiple occurrences of the same value.
 */
internal class MutableMultiSetImpl<E> : AbstractMultiSetImpl<E>, MutableMultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int
        get() = list.size

    /**
     * A list containing all elements in the set.
     */
    private var list: MutableList<E>

    /**
     * Elements used to generate [hashCodes].
     * Used to determine if mutable values have changed.
     */
    override val hashElements: Collection<E>
        get() = list

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        list = elements.toMutableList()
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        // TODO is this constructor needed?
        list = mutableListOf()

        counts.forEach {
            repeat(it.value) { _ -> list.add(it.key) }
        }
    }

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been added successfully, false otherwise
     */
    override fun add(element: E): Boolean {
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
        list.clear()
    }

    /**
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
     */
    override fun remove(element: E): Boolean {
        val currentCount = list.countElement(element)

        if (currentCount == 0) {
            return false
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
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false

        // TODO don't generate countsMap and use bulk remove
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
        val countsMap = createCountsMap()
        val otherCountsMap = createCountsMap(elements)

        val updatedCounts: MutableMap<E, Int> = mutableMapOf()

        // cannot modify counts during loop, will throw a ConcurrentModificationException
        for (pair in countsMap) {
            val element = pair.key
            val currentCount = pair.value
            val newCount = min(currentCount, otherCountsMap.getOrDefault(element, 0))
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
        return super<AbstractMultiSetImpl>.minus(other) as MutableMultiSet<E>
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MutableMultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MutableMultiSet<E> {
        return super<AbstractMultiSetImpl>.plus(other) as MutableMultiSet<E>
    }

    /**
     * Initialize a new MultiSet from an existing counts map.
     */
    override fun createFromCountsMap(counts: Map<E, Int>): MultiSet<E> = MutableMultiSetImpl(counts)

    /**
     * Convert counts to a mutable map
     */
    override fun finalizeCounts(counts: Map<E, Int>): Map<E, Int> = counts.toMutableMap()

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): MutableIterator<E> = list.toMutableList().iterator()
}
