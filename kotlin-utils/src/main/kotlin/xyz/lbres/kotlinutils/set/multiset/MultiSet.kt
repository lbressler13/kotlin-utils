package xyz.lbres.kotlinutils.set.multiset

// TODO still need to have original interface functions

/**
 * Interface for set that allows multiple occurrences of a value.
 * The interface supports only read access to the values.
 * Read/write access is available through the [MutableMultiSet] interface.
 */
// TODO this should be a collection, not a set
interface MultiSet<E> : Collection<E> {
    /**
     * All distinct values contained in the MultiSet
     */
    val distinctValues: Set<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element E
     * @return [Int]: The number of occurrences of [element]. 0 if the element does not exist.
     */
    fun getCountOf(element: E): Int

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this MultiSet
     * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    operator fun minus(other: MultiSet<E>): MultiSet<E>

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this MultiSet
     * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    operator fun plus(other: MultiSet<E>): MultiSet<E>

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [MultiSet]<E>: values to intersect with this MultiSet
     * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
     */
    infix fun intersect(other: MultiSet<E>): MultiSet<E>

    // TODO these should return lists!!

    /**
     * Create a new MultiSet with the results of applying the transform function to each value in the current MultiSet.
     *
     * @param transform (E) -> T: transformation function
     * @return [MultiSet]<T>: new MultiSet with transformed values
     */
    fun <T> map(transform: (E) -> T): MultiSet<T>

    /**
     * Create a new MultiSet containing only elements that match the given predicate.
     *
     * @param predicate (E) -> [Boolean]: predicate to use for filtering
     * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `true`
     */
    fun filter(predicate: (E) -> Boolean): MultiSet<E>

    /**
     * Create a new MultiSet containing only elements that do not match the given predicate.
     *
     * @param predicate (E) -> [Boolean]: predicate to use for filtering
     * @return [MultiSet]<E>: MultiSet containing only values for which [predicate] returns `false`
     */
    fun filterNot(predicate: (E) -> Boolean): MultiSet<E>

    /**
     * Accumulates value starting with [initial] value and applying [operation] from left to right
     * to current accumulator value and each element.
     *
     * Returns the specified [initial] value if the collection is empty.
     *
     * @param initial [T]: initial value for applying operation
     * @param [operation] (T, E) -> T: function that takes current accumulator value and an element, and calculates the next accumulator value.
     * @return [T]: the accumulated value, or [initial] if the MultiSet is empty
     */
    fun <T> fold(initial: T, operation: (acc: T, E) -> T): T

    // fun minByOrNull(minFunction: (E, E) -> Int): E?
    // fun maxByOrNull(maxFunction: (E, E) -> Int): E?
    // fun any(anyFunction: (E) -> Boolean): Boolean
    // fun all(allFunction: (E) -> Boolean): Boolean

    fun random(): E
}
