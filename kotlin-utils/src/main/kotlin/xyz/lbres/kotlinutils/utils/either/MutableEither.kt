package xyz.lbres.kotlinutils.utils.either

class MutableEither<S, T> private constructor(left: S?, right: T?, isLeft: Boolean) : Either<S, T>(left, right, isLeft) {
    private var _left: S? = left
    private var _right: T? = right
    private var _isLeft: Boolean = isLeft

    override var left: S?
        get() = if (_isLeft) _left else null
        set(value) {
            _isLeft = true
            _left = value
        }

    override var right: T?
        get() = if (_isLeft) null else _right
        set(value) {
            _isLeft = false
            _right = value
        }

    override val isLeft: Boolean
        get() = _isLeft

    constructor(value: S) : this(value, null, isLeft = true)

    internal constructor(either: Either<S, T>) : this(either.left, either.right, either.isLeft)

    fun toEither(): Either<S, T> = Either(left, right, isLeft)

    companion object {
        operator fun <S, T> invoke(value: T): MutableEither<S, T> = MutableEither(null, value, isLeft = false)

        fun <S, T> withLeft(value: S): MutableEither<S, T> = MutableEither(value, null, isLeft = true)

        fun <S, T> withRight(value: T): MutableEither<S, T> = MutableEither(null, value, isLeft = false)
    }
}
