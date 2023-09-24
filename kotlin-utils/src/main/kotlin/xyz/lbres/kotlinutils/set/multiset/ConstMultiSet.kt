package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager
import xyz.lbres.kotlinutils.set.multiset.manager.MultiSetManager

/**
 * [MultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed.
 */
abstract class ConstMultiSet<E> protected constructor() : MultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int
        get() = constManager.size

    /**
     * All distinct values contained in the set.
     */
    override val distinctValues: Set<E>
        get() = constManager.distinctValues

    /**
     * Manager for the set.
     */
    abstract val manager: MultiSetManager<E>
    private val constManager: ConstMultiSetManager<E>
        get() = manager as ConstMultiSetManager<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = constManager.getCountOf(element)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean = constManager.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If the collection contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as the collection.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if the current set contains at least as many occurrences of each value as the collection, `false` otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean = constManager.containsAll(elements)

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this set
     * @return [MultiSet]<E>: MultiSet containing all values from both sets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> = constManager.plus(other)

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this set
     * @return [MultiSet]<E>: MultiSet containing the items in this set but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> = constManager.minus(other)

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MultiSet]<E>: MultiSet containing only values that are in both set
     */
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> = constManager.intersect(other)

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = constManager.isEmpty()

    /**
     * If the current set contains the same elements as another MultiSet, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: `true` if [other] is a non-null MultiSet which contains the same values as the current set, `false` otherwise
     */
    override fun equals(other: Any?): Boolean = constManager.eq(other)

    /**
     * Get hash code for set.
     *
     * @return [Int]
     */
    override fun hashCode(): Int = constManager.hashCode()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String = constManager.toString()

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> = constManager.iterator()
}
