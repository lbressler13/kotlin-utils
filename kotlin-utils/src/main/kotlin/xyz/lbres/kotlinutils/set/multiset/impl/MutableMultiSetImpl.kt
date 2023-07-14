package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
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
     * List containing all elements in the set.
     */
    private val list: MutableList<E>

    /**
     * Elements in the set.
     */
    override val values: Collection<E>
        get() = list

    /**
     * Initialize set from existing counts.
     */
    constructor(elements: Collection<E>) {
        list = elements.toMutableList()
    }

    /**
     * Initialize set from existing counts.
     */
    private constructor(counts: Map<E, Int>) {
        list = mutableListOf()

        counts.forEach {
            repeat(it.value) { _ -> list.add(it.key) }
        }
    }

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean {
        return list.add(element)
    }

    /**
     * Add all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, the value will be added multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if any elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false
        elements.forEach { someSucceeded = add(it) || someSucceeded }

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
        return list.remove(element)
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
        elements.forEach { someSucceeded = remove(it) || someSucceeded }

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
        val counts = getCounts()
        val otherCounts = getCounts(elements)

        list.clear()

        for (pair in counts) {
            val element = pair.key
            val currentCount = pair.value
            val newCount = min(currentCount, otherCounts.getOrDefault(element, 0))
            repeat(newCount) { list.add(element) }
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
     * Initialize a new MultiSet from existing counts.
     */
    override fun createFromCounts(counts: Map<E, Int>): MultiSet<E> = MutableMultiSetImpl(counts)

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): MutableIterator<E> = list.toMutableList().iterator()
}
