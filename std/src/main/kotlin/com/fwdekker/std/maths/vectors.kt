package com.fwdekker.std.maths

import kotlin.math.abs


/**
 * Returns the L1 norm.
 */
@JvmName("intNorm1")
fun Iterable<Int>.norm1(): Int = sumOf { abs(it) }

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

@JvmName("intDistance")
fun Pair<Int, Int>.distance(that: Pair<Int, Int>, norm: (Iterable<Int>) -> Int = { it.norm1() }): Int =
    norm(listOf(this.first - that.first, this.second - that.second))

@JvmName("longDistance")
fun Collection<Long>.distance(that: Collection<Long>, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long =
    norm(this.matchedZip(that).map { (a, b) -> a - b })

@JvmName("longDistance")
fun Pair<Long, Long>.distance(that: Pair<Long, Long>, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long =
    norm(listOf(this.first - that.first, this.second - that.second))
