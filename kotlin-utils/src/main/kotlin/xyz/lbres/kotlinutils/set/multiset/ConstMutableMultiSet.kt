package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.min

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed.
 */
class ConstMutableMultiSet<E> internal constructor(initialElements: Collection<E>) : MutableMultiSet<E>, ConstMultiSet<E>(initialElements) {
    /**
     * If all properties are updated with the recent changes to the counts map
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

    // override property to get values from mutable map
    override val distinctValues: Set<E>
        get() = counts.keys

    // override property to allow changes to keys and values
    override val counts: MutableMap<E, Int> = createCounts(initialElements).toMutableMap()

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
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
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
     * @return [Boolean]: true if any elements have been removed successfully, false otherwise
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }
        allPropertiesUpdated = allPropertiesUpdated || anySucceeded

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
        allPropertiesUpdated = false
        val elementsCounts = createCounts(elements)

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
            _string = createString(counts)

            allPropertiesUpdated = true
        }
    }
}
