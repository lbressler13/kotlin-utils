package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if mutable values are changed.
 */
abstract class ConstMutableMultiSet<E> protected constructor() : MutableMultiSet<E>, ConstMultiSet<E>() {
    private val constManager: ConstMultiSetManager<E>
        get() = manager as ConstMultiSetManager<E>

    /**
     * Create a new MutableMultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this set
     * @return [MutableMultiSet]<E>: MutableMultiSet containing all values from both sets
     */
    override operator fun plus(other: MultiSet<E>): MutableMultiSet<E> = constManager.plus(other).toMutableMultiSet()

    /**
     * Create a new MutableMultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MutableMultiSet]<E>: values to subtract from this set
     * @return [MultiSet]<E>: MultiSet containing the items in this set but not the other
     */
    override operator fun minus(other: MultiSet<E>): MutableMultiSet<E> = constManager.minus(other).toMutableMultiSet()

    /**
     * Create a new MutableMultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MutableMultiSet]<E>: MultiSet containing only values that are in both set
     */
    override infix fun intersect(other: MultiSet<E>): MutableMultiSet<E> = constManager.intersect(other).toMutableMultiSet()

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean = constManager.add(element)

    /**
     * Add all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, the value will be added multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if any elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean = constManager.addAll(elements)

    /**
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
     */
    override fun remove(element: E): Boolean = constManager.remove(element)

    /**
     * Remove all specified elements from the set.
     * If [elements] contains a single occurrence of a value, only one occurrence of the value will be removed from the set.
     * If there are multiple occurrences, the value will be removed multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if any elements have been removed successfully, false otherwise
     */
    override fun removeAll(elements: Collection<E>): Boolean = constManager.removeAll(elements)

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if elements have been retained successfully, false otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean = constManager.retainAll(elements)

    /**
     * Remove all elements from the set.
     */
    override fun clear() { constManager.clear() }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    override fun iterator(): MutableIterator<E> = constManager.getMutableIterator()
}
