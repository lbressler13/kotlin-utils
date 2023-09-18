package xyz.lbres.kotlinutils.set.multiset

sealed interface ConstMutableMultiSet<E> : ConstMultiSet<E>, MutableMultiSet<E>
