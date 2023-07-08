package xyz.lbres.kotlinutils.list.mutablelist

/**
 * Create mutable list where every element is initialized with the same value
 *
 * @param size [Int]: size of list to create
 * @param value E: value of all elements
 * @return [List]: list of given size, where every element has the given value
 */
fun <E> mutableListOfValue(size: Int, value: E): MutableList<E> {
    return MutableList(size) { value }
}

/**
 * Create mutable list where every element is initialized as `null`
 *
 * @param size [Int]: size of list to create
 * @return [List]: list of given size, where every element is `null`
 */
fun <E> mutableListOfNulls(size: Int): MutableList<E?> = mutableListOfValue(size, null)
