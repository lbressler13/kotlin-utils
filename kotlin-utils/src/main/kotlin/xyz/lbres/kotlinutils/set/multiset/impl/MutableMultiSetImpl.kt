package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.math.min

/**
 * Mutable set implementation that allows multiple occurrences of the same value.
 */
internal class MutableMultiSetImpl<E> : PartialMultiSetImpl<E>, MutableMultiSet<E> {
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
    override val elements: Collection<E>
        get() = list

    /**
     * Initialize set from a collection of values.
     */
    constructor(elements: Collection<E>) {
        list = elements.toMutableList()
    }

    /**
     * Initialize set from existing counts.
     */
    internal constructor(counts: Map<E, Int>) {
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

        var anySucceeded = false
        elements.forEach { anySucceeded = add(it) || anySucceeded }

        return anySucceeded
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

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }

        return anySucceeded
    }

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection]<E>
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
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    override fun iterator(): MutableIterator<E> = list.toMutableList().iterator()
}
