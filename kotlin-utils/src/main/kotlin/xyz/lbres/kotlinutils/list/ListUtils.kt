package xyz.lbres.kotlinutils.list

/**
 * Create list where every element is initialized with the same value
 *
 * @param size [Int]: size of list to create
 * @param value [T]: value of all elements
 * @return [List]: list of given size, where every element has the given value
 */
fun <T> listOfValue(size: Int, value: T): List<T> {
    return List(size) { value }
}

/**
 * Create list where every element is initialized as `null`
 *
 * @param size [Int]: size of list to create
 * @return [List]: list of given size, where every element is `null`
 */
fun <T> listOfNulls(size: Int): List<T?> = listOfValue(size, null)
