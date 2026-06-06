package xyz.lbres.kotlinutils.utils.either

/**
 * Class that contains exactly one of the two possible types
 */
open class Either<S, T> internal constructor(left: S?, right: T?, isLeft: Boolean) {
    /**
     * Left value, `null` if [isLeft] is `false`
     */
    open val left: S? = left

    /**
     * Right value, `null` if [isRight] is `false`
     */
    open val right: T? = right

    /**
     * If the left value is set
     */
    open val isLeft: Boolean = isLeft

    /**
     * If the right value is set
     */
    val isRight: Boolean
        get() = !isLeft

    constructor(value: S) : this(value, null, isLeft = true)

    override fun equals(other: Any?): Boolean {
        return other is Either<*, *> && currentValue() == other.currentValue()
    }

    /**
     * If the set value matches a given value.
     * If [other] is an instance of [Either], it will check the set value of [other].
     */
    fun equalsValue(other: Any?): Boolean {
        if (other is Either<*, *>) {
            return equals(other)
        }
        return other == currentValue()
    }

    /**
     * If the set value matches a given value.
     * If [other] is an instance of [Either], it will check the set value of [other].
     */
    infix fun eqV(other: Any?): Boolean = equalsValue(other)

    /**
     * Get the current set value, including the value of a nested [Either]
     */
    protected fun currentValue(): Any? {
        val current: Any? = if (isLeft) left else right
        if (current is Either<*, *>) {
            return current.currentValue()
        }
        return current
    }

    /**
     * If the set value is `null`
     */
    fun isNull(): Boolean = left == null && right == null

    /**
     * Cast to an instance of [MutableEither] with the same value
     */
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

        /**
         * Create an [Either] with the given left value
         */
        fun <S, T> withLeft(value: S): Either<S, T> = Either(value, null, isLeft = true)

        /**
         * Create an [Either] with the given right value
         */
        fun <S, T> withRight(value: T): Either<S, T> = Either(null, value, isLeft = false)
    }
}
