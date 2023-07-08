package xyz.lbres.kotlinutils.list

/**
 * Create list where every element is initialized with the same value
 *
 * @param size [Int]: size of list to create
 * @param value E: value of all elements
 * @return [List]: list of given size, where every element has the given value
 */
fun <E> listOfValue(size: Int, value: E): List<E> {
    return List(size) { value }
}

/**
 * Create list where every element is initialized as `null`
 *
 * @param size [Int]: size of list to create
 * @return [List]: list of given size, where every element is `null`
 */
fun <E> listOfNulls(size: Int): List<E?> = listOfValue(size, null)
