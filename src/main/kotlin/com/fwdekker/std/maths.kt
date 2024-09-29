package com.fwdekker.std

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


/**
 * Converts [this] to an [Int], or throws an exception if over- or underflow would occur.
 */
fun Long.toIntExact(): Int = Math.toIntExact(this)

/**
 * Converts all numbers to [Int]s.
 */
fun Iterable<Number>.toInts(): List<Int> = map(Number::toInt)

/**
 * Converts all numbers to [Long]s.
 */
fun Iterable<Number>.toLongs(): List<Long> = map(Number::toLong)

/**
 * Converts to a pair of [Int]s.
 */
fun Pair<Number, Number>.toInts(): Pair<Int, Int> = map(Number::toInt)

/**
 * Converts to a pair of [Long]s.
 */
fun Pair<Number, Number>.toLongs(): Pair<Long, Long> = map(Number::toLong)


/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Int.wrapMod(mod: Int): Int = ((this % mod) + mod) % mod

fun Int.wrapMod(mod: Long): Long = ((this % mod) + mod) % mod

fun Long.wrapMod(mod: Int): Int = (((this % mod) + mod) % mod).toInt()

fun Long.wrapMod(mod: Long): Long = ((this % mod) + mod) % mod


/**
 * Calculates [this] to the power of some non-negative [exponent].
 */
fun Long.pow(exponent: Int): Long {
    require(exponent >= 0) { "Cannot calculate negative exponent on integer number." }

    return toDouble().pow(exponent.toDouble()).toLong()
}


/**
 * Takes the sum of all elements.
 */
@JvmName("bigIntSum")
fun Iterable<BigInteger>.sum(): BigInteger = fold(BigInteger.ZERO) { acc, it -> acc + it }

@JvmName("bigDecSum")
fun Iterable<BigDecimal>.sum(): BigDecimal = fold(BigDecimal.ZERO) { acc, it -> acc + it }

/**
 * Takes the product of all elements.
 */
@JvmName("intProduct")
fun Iterable<Int>.product(): Long = toLongs().product()

@JvmName("longProduct")
fun Iterable<Long>.product(): Long = reduce(Long::times)

@JvmName("bigIntProduct")
fun Iterable<BigInteger>.product(): BigInteger = reduce(BigInteger::times)

@JvmName("bigDecProduct")
fun Iterable<BigDecimal>.product(): BigDecimal = reduce(BigDecimal::times)

/**
 * Takes the product of both elements in the [Pair].
 */
@JvmName("intProduct")
fun Pair<Int, Int>.product(): Long = first.toLong() * second

@JvmName("longProduct")
fun Pair<Long, Long>.product(): Long = first * second

@JvmName("bigIntProduct")
fun Pair<BigInteger, BigInteger>.product(): BigInteger = first * second

@JvmName("bigDecProduct")
fun Pair<BigDecimal, BigDecimal>.product(): BigDecimal = first * second

/**
 * Shorthand for invoking [map] and then [product].
 */
@JvmName("intProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Int): Long = map(transform).product()

@JvmName("longProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Long): Long = map(transform).product()

@JvmName("bigIntProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> BigInteger): BigInteger = map(transform).product()

@JvmName("bigDecProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> BigDecimal): BigDecimal = map(transform).product()


/**
 * Returns the component-wise addition of [this] and [that].
 */
@JvmName("intPlus")
operator fun Pair<Int, Int>.plus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("longPlus")
operator fun Pair<Long, Long>.plus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first + that.first, this.second + that.second)

/**
 * Returns the component-wise addition of [this] and [that].
 */
@JvmName("intMinus")
operator fun Pair<Int, Int>.minus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first - that.first, this.second - that.second)

@JvmName("longMinus")
operator fun Pair<Long, Long>.minus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first - that.first, this.second - that.second)

/**
 * Returns the product of the respective components with [that].
 */
@JvmName("intTimes")
operator fun Pair<Int, Int>.times(that: Int): Pair<Int, Int> =
    Pair(this.first * that, this.second * that)

@JvmName("longTimes")
operator fun Pair<Long, Long>.times(that: Long): Pair<Long, Long> =
    Pair(this.first * that, this.second * that)

/**
 * Returns the component-wise multiplication of [this] and [that].
 */
@JvmName("intTimes")
operator fun Pair<Int, Int>.times(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("longTimes")
operator fun Pair<Long, Long>.times(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first * that.first, this.second * that.second)

/**
 * Returns the division of the respective components with [that].
 */
@JvmName("intDiv")
operator fun Pair<Int, Int>.div(that: Int): Pair<Int, Int> =
    Pair(this.first / that, this.second / that)

@JvmName("longDiv")
operator fun Pair<Long, Long>.div(that: Long): Pair<Long, Long> =
    Pair(this.first / that, this.second / that)

/**
 * Returns the component-wise division of [this] and [that].
 */
@JvmName("intDiv")
operator fun Pair<Int, Int>.div(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first / that.first, this.second / that.second)

@JvmName("longDiv")
operator fun Pair<Long, Long>.div(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first / that.first, this.second / that.second)


/**
 * Returns the factorial of [this] number.
 */
fun BigInteger.factorial(): BigInteger = factorial(this)

private tailrec fun factorial(number: BigInteger, acc: BigInteger = BigInteger.ONE): BigInteger =
    if (number <= BigInteger.ONE) acc
    else factorial(number - BigInteger.ONE, acc * number)


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
 * Splits this range into two parts signifying which elements are less than or equal to [value].
 *
 * The first part contains all elements less than or equal to [value], and the second part contains all elements
 * strictly greater than [value].
 */
fun LongRange.splitLEQ(value: Long): Pair<LongRange, LongRange> =
    when {
        isEmpty() -> Pair(LongRange.EMPTY, LongRange.EMPTY)
        value > last -> Pair(this, LongRange.EMPTY)
        value <= first -> Pair(LongRange.EMPTY, this)
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

/**
 * Splits this range into two parts signifying which elements are greater than or equal to [value].
 *
 * The first part contains all elements greater than or equal to [value], and the second part contains all elements
 * strictly less than [value].
 */
fun LongRange.splitGEQ(value: Long): Pair<LongRange, LongRange> =
    splitLEQ(value + 1).let { Pair(it.second, it.first) }

/**
 * Returns the range of values that lies in both [this] and [that].
 */
fun IntRange.overlap(that: IntRange): IntRange =
    (max(this.first, that.first)..min(this.last, that.last))
        .let { if (it.isEmpty()) IntRange.EMPTY else it }

/**
 * Returns the range of values that lies in both [this] and [that].
 */
fun LongRange.overlap(that: LongRange): LongRange =
    (max(this.first, that.first)..min(this.last, that.last))
        .let { if (it.isEmpty()) LongRange.EMPTY else it }

/**
 * Returns `true` if and only if [this] and [that] share at least one common value.
 */
fun IntRange.overlaps(that: IntRange): Boolean =
    !(max(this.first, that.first)..min(this.last, that.last)).isEmpty()

/**
 * Returns `true` if and only if [this] and [that] share at least one common value.
 */
fun LongRange.overlaps(that: LongRange): Boolean =
    !(max(this.first, that.first)..min(this.last, that.last)).isEmpty()

/**
 * Returns zero, one, or two ranges that together consist of all values in [this] that are not in [that].
 */
fun IntRange.without(that: IntRange): List<IntRange> =
    listOf(this.first..min(this.last, that.first - 1), max(this.first, that.last + 1)..this.last)
        .filterNot { it.isEmpty() }

/**
 * Returns zero, one, or two ranges that together consist of all values in [this] that are not in [that].
 */
fun LongRange.without(that: LongRange): List<LongRange> =
    listOf(this.first..min(this.last, that.first - 1), max(this.first, that.last + 1)..this.last)
        .filterNot { it.isEmpty() }

/**
 * Increases the start and end of the range by [by].
 */
fun IntRange.shift(by: Int): IntRange = this.first + by..this.last + by

/**
 * Increases the start and end of the range by [by].
 */
fun LongRange.shift(by: Long): LongRange = this.first + by..this.last + by


/**
 * Returns an infinite sequence of Fibonacci numbers, starting with 1, 1, 2.
 */
fun fibonacci(): Sequence<Long> =
    sequence {
        var a = 1L
        var b = 1L

        while (true) {
            yield(a)
            a = b.also { b += a }
        }
    }

/**
 * Returns an infinite sequence of all natural numbers, starting at 0.
 */
fun naturalNumbers(): Sequence<Long> = generateSequence(0L) { it + 1L }

/**
 * Returns an infinite sequence of all triangle numbers, starting at 1.
 *
 * The i-th triangle number is 1 + 2 + ... + i.
 */
fun triangleNumbers(): Sequence<Long> =
    sequence {
        var sum = 0L
        var inc = 1L
        while (true) {
            sum += inc
            inc++
            yield(sum)
        }
    }
