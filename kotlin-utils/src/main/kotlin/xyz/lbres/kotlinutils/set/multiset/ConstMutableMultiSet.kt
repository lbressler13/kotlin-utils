package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager

abstract class ConstMutableMultiSet<E> protected constructor(): MutableMultiSet<E>, ConstMultiSet<E>() {
    private val constManager: ConstMultiSetManager<E>
        get() = manager as ConstMultiSetManager<E>

    override operator fun plus(other: MultiSet<E>): MutableMultiSet<E> = constManager.plus(other).toMutableMultiSet()
    override operator fun minus(other: MultiSet<E>): MutableMultiSet<E> = constManager.minus(other).toMutableMultiSet()
    override infix fun intersect(other: MultiSet<E>): MutableMultiSet<E> = constManager.intersect(other).toMutableMultiSet()

    override fun add(element: E): Boolean = constManager.add(element)
    override fun addAll(elements: Collection<E>): Boolean = constManager.addAll(elements)
    override fun remove(element: E): Boolean = constManager.remove(element)
    override fun removeAll(elements: Collection<E>): Boolean = constManager.removeAll(elements)
    override fun retainAll(elements: Collection<E>): Boolean = constManager.retainAll(elements)

    override fun clear() { constManager.clear() }

    override fun iterator(): MutableIterator<E> = constManager.getMutableIterator()
}
