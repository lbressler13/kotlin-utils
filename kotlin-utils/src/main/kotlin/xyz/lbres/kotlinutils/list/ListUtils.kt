package xyz.lbres.kotlinutils.list

fun <E> listOfNulls(size: Int): List<E?> {
    return List(size) { null }
}

fun <E> listOfValue(size: Int, value: E): List<E> {
    return List(size) { value }
}
