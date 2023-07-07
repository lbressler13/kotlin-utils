package xyz.lbres.kotlinutils.list.mutablelist

fun <E> mutableListOfNulls(size: Int): MutableList<E?> {
    return MutableList(size) { null }
}

fun <E> mutableListOfValue(size: Int, value: E): MutableList<E> {
    return MutableList(size) { value }
}
