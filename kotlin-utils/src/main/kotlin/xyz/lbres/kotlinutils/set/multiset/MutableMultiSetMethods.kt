package xyz.lbres.kotlinutils.set.multiset

/**
 * Remove a random element from the MultiSet and return it
 *
 * @return [E]?: an element from the MultiSet, or `null` if the MultiSet is empty
 */
fun <E> MutableMultiSet<E>.popRandom(): E? {
    if (isEmpty()) {
        return null
    }

    val element = random()
    remove(element)
    return element
}
