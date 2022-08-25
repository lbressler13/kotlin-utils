package kotlinutils.classes.multiset

/**
 * Interface for set that allows multiple occurrences of a value.
 * The interface supports read/write access to the values.
 */
interface MutableMultiSet<E> : MultiSet<E>, MutableSet<E>
