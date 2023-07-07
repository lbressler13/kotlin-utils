package xyz.lbres.kotlinutils.collection.mutable.ext

/**
 * Remove a random element from the collection and return it
 *
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(): T? {
    if (isEmpty()) {
        return null
    }

    val element = random()
    remove(element)
    return element
}

// TODO popRandom with a seed
