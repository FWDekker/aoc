package com.fwdekker.aoc.std

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.abs
import kotlin.math.pow


/**
 * Converts [this] to an [Int], or throws an exception if over- or underflow would occur.
 */
fun Long.toIntExact(): Int = Math.toIntExact(this)

/**
 * Converts all numbers to [Long]s.
 */
fun Iterable<Number>.longs(): List<Long> = map(Number::toLong)

/**
 * Converts to a pair of [Long]s.
 */
fun Pair<Number, Number>.longs(): Pair<Long, Long> = map(Number::toLong)


/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Int.wrapMod(mod: Int): Int = ((this % mod) + mod) % mod

/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Int.wrapMod(mod: Long): Long = ((this % mod) + mod) % mod

/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Long.wrapMod(mod: Int): Int = (((this % mod) + mod) % mod).toInt()

/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Long.wrapMod(mod: Long): Long = ((this % mod) + mod) % mod


/**
 * Calculates [this] to the power of some non-negative [exponent].
 */
fun Long.pow(exponent: Int): Long {
    require(exponent >= 0) { "Cannot calculate negative exponent on integer number." }

    return toDouble().pow(exponent.toDouble()).toLong()
}


/**
 * Returns the product of multiplying all entries (as [Long]s).
 */
@JvmName("intProduct")
fun Iterable<Int>.product(): Long = longs().product()

/**
 * Returns the product of multiplying all entries.
 */
@JvmName("longProduct")
fun Iterable<Long>.product(): Long = reduce(Long::times)

@JvmName("intProduct")
fun Pair<Int, Int>.product(): Long = first.toLong() * second

@JvmName("longProduct")
fun Pair<Long, Long>.product(): Long = first * second

/**
 * Shorthand for invoking [map] and then [product].
 */
@JvmName("intProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Int): Long = map(transform).product()

/**
 * Shorthand for invoking [map] and then [product].
 */
@JvmName("longProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Long): Long = map(transform).product()


/**
 * Returns the component-wise addition of [this] and [that].
 */
@JvmName("intPlus")
operator fun Pair<Int, Int>.plus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + that.first, this.second + that.second)

/**
 * Returns the component-wise addition of [this] and [that].
 */
@JvmName("longPlus")
operator fun Pair<Long, Long>.plus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first + that.first, this.second + that.second)

/**
 * Returns the component-wise addition of [this] and [that].
 */
@JvmName("intMinus")
operator fun Pair<Int, Int>.minus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first - that.first, this.second - that.second)

/**
 * Returns the component-wise addition of [this] and [that].
 */
@JvmName("longMinus")
operator fun Pair<Long, Long>.minus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first - that.first, this.second - that.second)

/**
 * Returns the product of the respective components with [that].
 */
@JvmName("intTimes")
operator fun Pair<Int, Int>.times(that: Int): Pair<Int, Int> =
    Pair(this.first * that, this.second * that)

/**
 * Returns the component-wise multiplication of [this] and [that].
 */
@JvmName("intTimes")
operator fun Pair<Int, Int>.times(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first * that.first, this.second * that.second)

/**
 * Returns the product of the respective components with [that].
 */
@JvmName("longTimes")
operator fun Pair<Long, Long>.times(that: Long): Pair<Long, Long> =
    Pair(this.first * that, this.second * that)

/**
 * Returns the component-wise multiplication of [this] and [that].
 */
@JvmName("longTimes")
operator fun Pair<Long, Long>.times(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first * that.first, this.second * that.second)

/**
 * Returns the division of the respective components with [that].
 */
@JvmName("intDiv")
operator fun Pair<Int, Int>.div(that: Int): Pair<Int, Int> =
    Pair(this.first / that, this.second / that)

/**
 * Returns the component-wise division of [this] and [that].
 */
@JvmName("intDiv")
operator fun Pair<Int, Int>.div(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first / that.first, this.second / that.second)

/**
 * Returns the division of the respective components with [that].
 */
@JvmName("longDiv")
operator fun Pair<Long, Long>.div(that: Long): Pair<Long, Long> =
    Pair(this.first / that, this.second / that)

/**
 * Returns the component-wise division of [this] and [that].
 */
@JvmName("longDiv")
operator fun Pair<Long, Long>.div(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first / that.first, this.second / that.second)


/**
 * Returns the greatest common divisor of [a] and [b].
 */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/**
 * @see gcd
 */
fun Iterable<Long>.gcd(): Long = reduce(::gcd)


/**
 * Returns the least common multiplier of [a] and [b].
 */
fun lcm(a: Long, b: Long): Long = a * (b / gcd(a, b))

/**
 * @see lcm
 */
fun Iterable<Long>.lcm(): Long = reduce(::lcm)


/**
 * Returns the L1 norm.
 */
@JvmName("intNorm1")
fun Iterable<Int>.norm1(): Int = sumOf { abs(it) }

/**
 * Returns the L1 norm.
 */
@JvmName("longNorm1")
fun Iterable<Long>.norm1(): Long = sumOf { abs(it) }

/**
 * Invokes [zip] on [this] and [that], but requires that the sizes of [this] and [that] are equal.
 */
private fun <S, T> Collection<S>.matchedZip(that: Collection<T>): List<Pair<S, T>> {
    require(this.size == that.size) { "Expected equal sizes, but sizes were ${this.size} and ${that.size}." }

    return this zip that
}

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("intDistance")
fun Collection<Int>.distance(that: Collection<Int>, norm: (Iterable<Int>) -> Int = { it.norm1() }): Int =
    norm(this.matchedZip(that).map { (a, b) -> a - b })

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("intDistance")
fun Pair<Int, Int>.distance(that: Pair<Int, Int>, norm: (Iterable<Int>) -> Int = { it.norm1() }): Int =
    norm((this - that).toList())

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("longDistance")
fun Collection<Long>.distance(that: Collection<Long>, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long =
    norm(this.matchedZip(that).map { (a, b) -> a - b })

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("longDistance")
fun Pair<Long, Long>.distance(that: Pair<Long, Long>, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long =
    norm((this - that).toList())


/**
 * Splits this range into two parts signifying which elements are less than or equal to [value].
 *
 * The first part contains all elements less than or equal to [value], and the second part contains all elements
 * strictly greater than [value].
 */
fun IntRange.splitLEQ(value: Int): Pair<IntRange, IntRange> =
    when {
        isEmpty() -> Pair(IntRange.EMPTY, IntRange.EMPTY)
        value > last -> Pair(this, IntRange.EMPTY)
        value <= first -> Pair(IntRange.EMPTY, this)
        else -> Pair(first..<value, value..last)
    }

/**
 * Splits this range into two parts signifying which elements are greater than or equal to [value].
 *
 * The first part contains all elements greater than or equal to [value], and the second part contains all elements
 * strictly less than [value].
 */
fun IntRange.splitGEQ(value: Int): Pair<IntRange, IntRange> =
    splitLEQ(value + 1).let { Pair(it.second, it.first) }
