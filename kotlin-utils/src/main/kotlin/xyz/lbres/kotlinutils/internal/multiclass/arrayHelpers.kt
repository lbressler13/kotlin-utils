package xyz.lbres.kotlinutils.internal.multiclass

import kotlin.reflect.KClass

internal inline fun <S, reified T> genericArrayOfValue(type: KClass<Any>, size: Int, value: T): Any {
    return when (type) {
        Array::class -> Array(size) { value }
        BooleanArray::class -> BooleanArray(size) { value as Boolean }
        CharArray::class -> CharArray(size) { value as Char }
        DoubleArray::class -> DoubleArray(size) { value as Double }
        FloatArray::class -> FloatArray(size) { value as Float }
        IntArray::class -> IntArray(size) { value as Int }
        LongArray::class -> LongArray(size) { value as Long }
        ShortArray::class -> ShortArray(size) { value as Short }
        else -> throw Exception("Invalid type parameter: $type")
    }
}
