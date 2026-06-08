package xyz.lbres.kotlinutils.utils.either

/**
 * Class that contains exactly one of the two possible types, where the set value can be updated.
 */
class MutableEither<S, T> private constructor(left: S?, right: T?, isLeft: Boolean) :
    Either<S, T>(left, right, isLeft) {
    // mutable backing properties
    private var _left: S? = left
    private var _right: T? = right
    private var _isLeft: Boolean = isLeft

    /**
     * Left value, `null` if [isLeft] is `false`.
     * Changing this will automatically set [right] to `null` and set [isLeft] to `true`.
     */
    override var left: S?
        get() = _left
        set(value) {
            _isLeft = true
            _left = value
            _right = null
        }

    /**
     * Right value, `null` if [isRight] is `false`.
     * Changing this will automatically set [left] to `null` and set [isRight] to `true`.
     */
    override var right: T?
        get() = _right
        set(value) {
            _isLeft = false
            _right = value
            _left = null
        }

    /**
     * If the left value is set
     */
    override val isLeft: Boolean
        get() = _isLeft

    // left constructor
    constructor(value: S) : this(value, null, isLeft = true)

    // constructor to use for casting only
    constructor(either: Either<S, T>) : this(either.left, either.right, either.isLeft)

    /**
     * Cast to an instance of a non-mutable [Either] with the same value
     */
    fun toEither(): Either<S, T> = Either(left, right, isLeft)

    companion object {
        // right constructor
        operator fun <S, T> invoke(value: T): MutableEither<S, T> = MutableEither(null, value, isLeft = false)

        /**
         * Create a [MutableEither] with the given left value
         */
        fun <S, T> withLeft(value: S): MutableEither<S, T> = MutableEither(value, null, isLeft = true)

        /**
         * Create a [MutableEither] with the given right value
         */
        fun <S, T> withRight(value: T): MutableEither<S, T> = MutableEither(null, value, isLeft = false)
    }
}
