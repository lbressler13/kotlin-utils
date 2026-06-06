package xyz.lbres.kotlinutils.utils.either

class MutableEither<S, T> private constructor(left: S?, right: T?, isLeft: Boolean) : Either<S, T>(left, right, isLeft) {
    private var _left: S? = left
    private var _right: T? = right
    private var _isLeft: Boolean = isLeft

    /**
     * Left value, `null` if [isLeft] is `false`
     */
    override var left: S?
        get() = if (_isLeft) _left else null
        set(value) {
            _isLeft = true
            _left = value
        }

    /**
     * Right value, `null` if [isRight] is `false`
     */
    override var right: T?
        get() = if (_isLeft) null else _right
        set(value) {
            _isLeft = false
            _right = value
        }

    /**
     * If the left value is set
     */
    override val isLeft: Boolean
        get() = _isLeft

    constructor(value: S) : this(value, null, isLeft = true)

    /**
     * Constructor to use for casting only
     */
    internal constructor(either: Either<S, T>) : this(either.left, either.right, either.isLeft)

    /**
     * Cast to an instance of a non-mutable [Either] with the same value
     */
    fun toEither(): Either<S, T> = Either(left, right, isLeft)

    companion object {
        operator fun <S, T> invoke(value: T): MutableEither<S, T> = MutableEither(null, value, isLeft = false)

        /**
         * Create a [MutableEither] using the left value
         */
        fun <S, T> withLeft(value: S): MutableEither<S, T> = MutableEither(value, null, isLeft = true)

        /**
         * Create a [MutableEither] using the right value
         */
        fun <S, T> withRight(value: T): MutableEither<S, T> = MutableEither(null, value, isLeft = false)
    }
}
