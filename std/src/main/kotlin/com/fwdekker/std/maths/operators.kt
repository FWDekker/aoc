package com.fwdekker.std.maths

import com.fwdekker.std.collections.map
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.ExperimentalTypeInference


/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Int.wrapMod(mod: Int): Int = ((this % mod) + mod) % mod

fun Long.wrapMod(mod: Int): Int = (((this % mod) + mod) % mod).toInt()

fun Long.wrapMod(mod: Long): Long = ((this % mod) + mod) % mod


/**
 * Takes the component-wise addition of the pairs.
 */
@JvmName("intPairPlus")
operator fun Pair<Int, Int>.plus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("longPairPlus")
operator fun Pair<Long, Long>.plus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("bigIntPairPlus")
operator fun Pair<BigInteger, BigInteger>.plus(that: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("bigDecPairPlus")
operator fun Pair<BigDecimal, BigDecimal>.plus(that: Pair<BigDecimal, BigDecimal>): Pair<BigDecimal, BigDecimal> =
    Pair(this.first + that.first, this.second + that.second)

/**
 * Takes the sum of all elements.
 */
@JvmName("bigIntSum")
fun Sequence<BigInteger>.sum(): BigInteger = fold(BigInteger.ZERO) { acc, it -> acc + it }

@JvmName("bigIntSum")
fun Iterable<BigInteger>.sum(): BigInteger = asSequence().sum()

@JvmName("bigDecSum")
fun Sequence<BigDecimal>.sum(): BigDecimal = fold(BigDecimal.ZERO) { acc, it -> acc + it }

@JvmName("bigDecSum")
fun Iterable<BigDecimal>.sum(): BigDecimal = asSequence().sum()

/**
 * Takes the component-wise sum of all elements.
 */
@JvmName("intPairSum")
fun Sequence<Pair<Int, Int>>.sum(): Pair<Int, Int> = fold(Pair(0, 0)) { acc, it -> acc + it }

@JvmName("intPairSum")
fun Iterable<Pair<Int, Int>>.sum(): Pair<Int, Int> = asSequence().sum()

@JvmName("longPairSum")
fun Sequence<Pair<Long, Long>>.sum(): Pair<Long, Long> = fold(Pair(0L, 0L)) { acc, it -> acc + it }

@JvmName("longPairSum")
fun Iterable<Pair<Long, Long>>.sum(): Pair<Long, Long> = asSequence().sum()

@JvmName("bigIntPairSum")
fun Sequence<Pair<BigInteger, BigInteger>>.sum(): Pair<BigInteger, BigInteger> =
    fold(Pair(BigInteger.ZERO, BigInteger.ZERO)) { acc, it -> acc + it }

@JvmName("bigIntPairSum")
fun Iterable<Pair<BigInteger, BigInteger>>.sum(): Pair<BigInteger, BigInteger> = asSequence().sum()

@JvmName("bigDecPairSum")
fun Sequence<Pair<BigDecimal, BigDecimal>>.sum(): Pair<BigDecimal, BigDecimal> =
    fold(Pair(BigDecimal.ZERO, BigDecimal.ZERO)) { acc, it -> acc + it }

@JvmName("bigDecPairSum")
fun Iterable<Pair<BigDecimal, BigDecimal>>.sum(): Pair<BigDecimal, BigDecimal> = asSequence().sum()


/**
 * Takes the component-wise multiplication of the pairs.
 */
@JvmName("intPairTimes")
operator fun Pair<Int, Int>.times(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("longPairTimes")
operator fun Pair<Long, Long>.times(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("bigIntPairTimes")
operator fun Pair<BigInteger, BigInteger>.times(that: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("bigDecPairTimes")
operator fun Pair<BigDecimal, BigDecimal>.times(that: Pair<BigDecimal, BigDecimal>): Pair<BigDecimal, BigDecimal> =
    Pair(this.first * that.first, this.second * that.second)

/**
 * Takes the product of all elements.
 */
@JvmName("intProduct")
fun Sequence<Int>.product(): Long = toLongs().product()

@JvmName("intProduct")
fun Iterable<Int>.product(): Long = toLongs().product()

@JvmName("longProduct")
fun Sequence<Long>.product(): Long = reduce(Long::times)

@JvmName("longProduct")
fun Iterable<Long>.product(): Long = asSequence().product()

@JvmName("bigIntProduct")
fun Sequence<BigInteger>.product(): BigInteger = reduce(BigInteger::times)

@JvmName("bigIntProduct")
fun Iterable<BigInteger>.product(): BigInteger = asSequence().product()

@JvmName("bigDecProduct")
fun Sequence<BigDecimal>.product(): BigDecimal = reduce(BigDecimal::times)

@JvmName("bigDecProduct")
fun Iterable<BigDecimal>.product(): BigDecimal = asSequence().product()

/**
 * Takes the pair-wise product of all elements.
 */
@JvmName("intProduct")
fun Sequence<Pair<Int, Int>>.product(): Pair<Long, Long> = toLongs().product()

@JvmName("intProduct")
fun Iterable<Pair<Int, Int>>.product(): Pair<Long, Long> = asSequence().product()

@JvmName("longProduct")
fun Sequence<Pair<Long, Long>>.product(): Pair<Long, Long> = fold(Pair(1L, 1L)) { acc, it -> acc * it }

@JvmName("longProduct")
fun Iterable<Pair<Long, Long>>.product(): Pair<Long, Long> = asSequence().product()

@JvmName("bigIntProduct")
fun Sequence<Pair<BigInteger, BigInteger>>.product(): Pair<BigInteger, BigInteger> =
    fold(Pair(BigInteger.ONE, BigInteger.ONE)) { acc, it -> acc * it }

@JvmName("bigIntProduct")
fun Iterable<Pair<BigInteger, BigInteger>>.product(): Pair<BigInteger, BigInteger> = asSequence().product()

@JvmName("bigDecProduct")
fun Sequence<Pair<BigDecimal, BigDecimal>>.product(): Pair<BigDecimal, BigDecimal> =
    fold(Pair(BigDecimal.ONE, BigDecimal.ONE)) { acc, it -> acc * it }

@JvmName("bigDecProduct")
fun Iterable<Pair<BigDecimal, BigDecimal>>.product(): Pair<BigDecimal, BigDecimal> = asSequence().product()

/**
 * Shorthand for invoking [map] and then [product].
 */
@JvmName("intProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> Int): Long = map(transform).product()

@JvmName("intProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Int): Long = map(transform).product()

@JvmName("longProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> Long): Long = map(transform).product()

@JvmName("longProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Long): Long = map(transform).product()

@JvmName("bigIntProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> BigInteger): BigInteger = map(transform).product()

@JvmName("bigIntProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> BigInteger): BigInteger = map(transform).product()

@JvmName("bigDecProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> BigDecimal): BigDecimal = map(transform).product()

@JvmName("bigDecProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> BigDecimal): BigDecimal = map(transform).product()


/**
 * Calculates [this] to the power of some non-negative [exponent].
 */
fun Int.pow(exponent: Int): Int = toBigInteger().pow(exponent).toIntExact()

fun Long.pow(exponent: Int): Long {
    require(exponent >= 0) { "Cannot calculate negative exponent on integer number." }

    return toBigInteger().pow(exponent).toLongExact()
}


/**
 * Returns the minimum of [first] and [second].
 */
fun <T : Comparable<T>> min(first: T, second: T): T = if (first <= second) first else second

/**
 * Returns the maximum of [first] and [second].
 */
fun <T : Comparable<T>> max(first: T, second: T): T = if (first >= second) first else second
