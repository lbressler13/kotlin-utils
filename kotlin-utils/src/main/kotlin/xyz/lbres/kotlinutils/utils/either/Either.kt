package xyz.lbres.kotlinutils.utils.either

import xyz.lbres.kotlinutils.internal.constants.Suppressions

// TODO make this sealed?
open class Either<T, S> protected constructor(open val left: T?, open val right: S?, open val isLeft: Boolean) {
    val isRight: Boolean
        get() = !isLeft

    constructor(value: T) : this(value, null, isLeft = true)

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

    override fun toString(): String {
        val current: Any? = if (isLeft) left else right
        return "Either($current)"
    }

    override fun hashCode(): Int {
        val hashCode = currentValue().hashCode()
        return 31 * hashCode + Either::class.java.name.hashCode()
    }

    companion object {
        operator fun <T, S> invoke(value: S): Either<T, S> = Either(null, value, isLeft = false)

        fun <T, S> withLeft(value: T): Either<T, S> = Either(value, null, isLeft = true)

        fun <T, S> withRight(value: S): Either<T, S> = Either(null, value, isLeft = false)
    }
}
