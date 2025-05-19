package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap

/**
 * [MutableMultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal class MutableMultiSetImpl<E>(elements: Collection<E>) : AbstractMultiSetImpl<E>(elements), MutableMultiSet<E> {
    /**
     * Elements in the set.
     */
    override val elements: MutableList<E> = elements.toMutableList()
    override val immutableElements: MutableList<E> = elements.filter { isImmutable(it) }.toMutableList()
    override val mutableElements: MutableList<E> = elements.filterNot { isImmutable(it) }.toMutableList()

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean = _add(element, true)

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
        elements.forEach { anySucceeded = _add(it, false) || anySucceeded }
        updateImmutableCounts()

        return anySucceeded
    }

    /**
     * Wrapper for adding an element, with an additional parameter to indicate if [updateImmutableCounts]
     * should be called for an immutable element
     */
    private fun _add(element: E, updateImmutables: Boolean): Boolean {
        var success = elements.add(element)
        if (isImmutable(element)) {
            success = immutableElements.add(element) && success

            if (updateImmutables) {
                updateImmutableCounts()
            }
        } else {
            success = mutableElements.add(element) && success
        }
        return success
    }

    /**
     * Remove all elements from the set.
     */
    override fun clear() {
        elements.clear()
        immutableElements.clear()
        mutableElements.clear()
        updateImmutableCounts()
    }

    /**
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
     */
    override fun remove(element: E): Boolean = _remove(element, true)

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
        elements.forEach { anySucceeded = _remove(it, false) || anySucceeded }
        updateImmutableCounts()

        return anySucceeded
    }

    /**
     * Wrapper for removing an element, with an additional parameter to indicate if [updateImmutableCounts]
     * should be called for an immutable element
     */
    private fun _remove(element: E, updateImmutables: Boolean): Boolean {
        var success = elements.remove(element)
        if (isImmutable(element)) {
            success = immutableElements.remove(element) && success

            if (updateImmutables) {
                updateImmutableCounts()
            }
        } else {
            success = mutableElements.remove(element) && success
        }
        return success
    }

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if elements have been retained successfully, false otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        if (this.elements.isEmpty()) {
            return true
        }

        if (elements.isEmpty()) {
            this.elements.clear()
            immutableElements.clear()
            mutableElements.clear()
            updateImmutableCounts()
            return true
        }

        val counts = this.counts // get map before clearing elements

        val otherCounts = try {
            when (elements) {
                is MutableMultiSetImpl<*> -> (elements as MutableMultiSetImpl<E>).counts
                is ConstMultiSetImpl<*> -> (elements as ConstMultiSetImpl<E>).toCountsMap()
                is ConstMutableMultiSetImpl<*> -> (elements as ConstMutableMultiSetImpl<E>).toCountsMap()
                else -> CountsMap.from(elements)
            }
        } catch (_: ClassCastException) {
            CountsMap.from(elements)
        }

        this.elements.clear()
        immutableElements.clear()
        mutableElements.clear()

        val newCounts = counts intersect otherCounts
        val countsList = newCounts.toList()

        this.elements.addAll(countsList)
        countsList.forEach { simpleIf(isImmutable(it), immutableElements, mutableElements).add(it) }
        updateImmutableCounts()

        return true
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    override fun iterator(): MutableIterator<E> = elements.toMutableList().iterator()

    /**
     * Wrapper function for updating the immutable counts with the current list of immutable elements
     */
    private fun updateImmutableCounts() {
        immutableCounts = CountsMap.from(immutableElements)
    }
}
