package com.fwdekker.aoc.std

import kotlin.math.abs


/**
 * Converts all numbers to [Long]s.
 */
fun Collection<Number>.longs(): List<Long> = map(Number::toLong)

/**
 * Converts to a pair of [Long]s.
 */
fun Pair<Number, Number>.longs(): Pair<Long, Long> = map(Number::toLong)


/**
 * Returns the product of multiplying all entries (as [Long]s).
 */
@JvmName("intProduct")
fun Collection<Int>.product(): Long = longs().product()

/**
 * Returns the product of multiplying all entries.
 */
@JvmName("longProduct")
fun Collection<Long>.product(): Long = reduce(Long::times)


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
 * Returns the greatest common divisor of [a] and [b].
 */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/**
 * @see gcd
 */
fun Collection<Long>.gcd(): Long = reduce(::gcd)


/**
 * Returns the least common multiplier of [a] and [b].
 */
fun lcm(a: Long, b: Long): Long = a * (b / gcd(a, b))

/**
 * @see lcm
 */
fun Collection<Long>.lcm(): Long = reduce(::lcm)


/**
 * Returns the L1 norm.
 */
@JvmName("intNorm1")
fun Collection<Int>.norm1(): Int = sumOf { abs(it) }

/**
 * Returns the L1 norm.
 */
@JvmName("longNorm1")
fun Collection<Long>.norm1(): Long = sumOf { abs(it) }

/**
 * Invokes [zip] on [this] and [that], but requires that the sizes of [this] and [that] are equal.
 */
private fun <S, T> Collection<S>.matchedZip(that: Collection<T>): Collection<Pair<S, T>> {
    require(this.size == that.size) { "Expected equal sizes, but sizes were ${this.size} and ${that.size}." }

    return this.zip(that)
}

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("intDistance")
fun Collection<Int>.distance(that: Collection<Int>, norm: (Collection<Int>) -> Int = { it.norm1() }): Int =
    norm(this.matchedZip(that).map { (a, b) -> a - b })

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("intDistance")
fun Pair<Int, Int>.distance(that: Pair<Int, Int>, norm: (Collection<Int>) -> Int = { it.norm1() }): Int =
    norm((this - that).toList())

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("longDistance")
fun Collection<Long>.distance(that: Collection<Long>, norm: (Collection<Long>) -> Long = { it.norm1() }): Long =
    norm(this.matchedZip(that).map { (a, b) -> a - b })

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("longDistance")
fun Pair<Long, Long>.distance(that: Pair<Long, Long>, norm: (Collection<Long>) -> Long = { it.norm1() }): Long =
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
