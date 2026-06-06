package xyz.lbres.kotlinutils.utils.either

class MutableEither<T, S> private constructor(left: T?, right: S?, isLeft: Boolean) : Either<T, S>(left, right, isLeft) {
    private var _left: T? = left
    private var _right: S? = right
    private var _isLeft: Boolean = isLeft

    override var left: T?
        get() = if (_isLeft) _left else null
        set(value) {
            _isLeft = true
            _left = value
        }

    override var right: S?
        get() = if (_isLeft) null else _right
        set(value) {
            _isLeft = false
            _right = value
        }

    override val isLeft: Boolean
        get() = _isLeft

    constructor(value: T) : this(value, null, isLeft = true)

    companion object {
        operator fun <T, S> invoke(value: S): MutableEither<T, S> = MutableEither(null, value, isLeft = false)

        fun <T, S> withLeft(value: T): Either<T, S> = MutableEither(value, null, isLeft = true)

        fun <T, S> withRight(value: S): Either<T, S> = MutableEither(null, value, isLeft = false)
    }
}
