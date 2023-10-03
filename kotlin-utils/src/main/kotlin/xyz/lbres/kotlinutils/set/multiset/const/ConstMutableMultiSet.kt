package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.countsToString
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed (i.e. elements are added to a mutable list).
 */
sealed class ConstMutableMultiSet<E> constructor(initialElements: Collection<E>) : MutableMultiSet<E>, ConstMultiSet<E>(initialElements) {
    /**
     * If all properties are up-to-date with the most recent changes to the counts map
     */
    private var allPropertiesUpdated: Boolean = false

    // elements list is up-to-date only when allPropertiesUpdated == true
    private var _elements: MutableList<E> = initialElements.toMutableList()
    override val elements: Collection<E>
        get() {
            updateMutableValues() // update elements before returning value
            return _elements
        }

    // string is up-to-date only when allPropertiesUpdated == true
    private var _string: String = ""
    override val string: String
        get() {
            updateMutableValues() // update string before returning value
            return _string
        }

    override val counts: MutableMap<E, Int> = initializeCounts().toMutableMap()

    override val distinctValues: Set<E>
        get() = counts.keys

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean {
        allPropertiesUpdated = false
        counts[element] = getCountOf(element) + 1
        _size++
        return true
    }

    /**
     * Add all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, the value will be added multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if any elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean {
        allPropertiesUpdated = false
        elements.forEach(this::add)
        return true
    }

    /**
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been removed successfully, `false` otherwise
     */
    override fun remove(element: E): Boolean {
        when (getCountOf(element)) {
            0 -> return false
            1 -> counts.remove(element)
            else -> counts[element] = getCountOf(element) - 1
        }

        allPropertiesUpdated = false
        _size--
        return true
    }

    /**
     * Remove all specified elements from the set.
     * If [elements] contains a single occurrence of a value, only one occurrence of the value will be removed from the set.
     * If there are multiple occurrences, the value will be removed multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if any elements have been removed successfully, `false` otherwise
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }
        allPropertiesUpdated = allPropertiesUpdated && !anySucceeded

        return anySucceeded
    }

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if elements have been retained successfully, `false` otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        val initialSize = size
        val elementsCounts = createCountsMap(elements)

        val newCounts = distinctValues.associateWith {
            min(getCountOf(it), elementsCounts.getOrDefault(it, 0))
        }

        counts.clear()
        _size = 0

        newCounts.forEach { (element, count) ->
            if (count > 0) {
                counts[element] = count
                _size += count
            }
        }

        allPropertiesUpdated = allPropertiesUpdated && size == initialSize

        return true
    }

    /**
     * Remove all elements from the set.
     */
    override fun clear() {
        counts.clear()
        _size = 0
        allPropertiesUpdated = false
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    override fun iterator(): MutableIterator<E> {
        updateMutableValues()
        return _elements.iterator()
    }

    /**
     * Update the elements list and string based on the current values in the counts map
     */
    private fun updateMutableValues() {
        if (!allPropertiesUpdated) {
            _elements.clear()
            counts.forEach { (element, count) ->
                repeat(count) { _elements.add(element) }
            }
            _string = countsToString(counts)

            allPropertiesUpdated = true
        }
    }
}
