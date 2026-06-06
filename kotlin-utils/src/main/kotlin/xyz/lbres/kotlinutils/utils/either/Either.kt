package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions

open class Either<S, T> internal constructor(open val left: S?, open val right: T?, open val isLeft: Boolean) {
    val isRight: Boolean
        get() = !isLeft

    constructor(value: S) : this(value, null, isLeft = true)

    override fun equals(other: Any?): Boolean {
        println(other)
        return other is Either<*, *> && currentValue() == other.currentValue()
    }

    @Suppress(Suppressions.COULD_BE_PRIVATE)
    fun equalsValue(other: Any?): Boolean {
        println("${currentValue()}, $other, ${other is Either<*, *>}")
        if (other is Either<*, *>) {
            return equals(other)
        }
        return other == currentValue()
    }

    infix fun eqV(other: Any?): Boolean = equalsValue(other)

    protected fun currentValue(): Any? {
        val current: Any? = if (isLeft) left else right
        if (current is Either<*, *>) {
            return current.currentValue()
        }
        return current
    }

    fun isNull(): Boolean = left == null && right == null

    fun toMutableEither(): MutableEither<S, T> = MutableEither(this)

    override fun toString(): String {
        val current: Any? = if (isLeft) left else right
        return "Either($current)"
    }

    override fun hashCode(): Int {
        val hashCode = currentValue().hashCode()
        return 31 * hashCode + Either::class.java.name.hashCode()
    }

    companion object {
        operator fun <S, T> invoke(value: T): Either<S, T> = Either(null, value, isLeft = false)

        fun <S, T> withLeft(value: S): Either<S, T> = Either(value, null, isLeft = true)

        fun <S, T> withRight(value: T): Either<S, T> = Either(null, value, isLeft = false)
    }
}
