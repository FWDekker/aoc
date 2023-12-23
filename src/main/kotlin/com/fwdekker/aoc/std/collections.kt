package com.fwdekker.aoc.std

import kotlin.experimental.ExperimentalTypeInference


/**
 * Returns the element at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun <T> List<T>.getMod(index: Number): T = this[index.toLong().wrapMod(size)]

/**
 * Returns the character at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun String.getMod(index: Number): Char = this[index.toLong().wrapMod(length)]


/**
 * Shorthand for invoking [withIndex] and then [sumOf].
 */
@JvmName("intSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> Int): Int =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

/**
 * Shorthand for invoking [withIndex] and then [sumOf].
 */
@JvmName("longSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> Long): Long =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

/**
 * Takes the component-wise sum of all elements.
 */
@JvmName("intPairSum")
fun Iterable<Pair<Int, Int>>.sum(): Pair<Int, Int> = fold(Pair(0, 0)) { acc, it -> acc + it }

/**
 * Takes the component-wise sum of all elements.
 */
@JvmName("longPairSum")
fun Iterable<Pair<Long, Long>>.sum(): Pair<Long, Long> = fold(Pair(0L, 0L)) { acc, it -> acc + it }


/**
 * Repeats [this] sequence [amount] times.
 */
fun <T> Sequence<T>.repeat(amount: Int): Sequence<T> = sequence { for (i in 1..amount) yieldAll(this@repeat) }

/**
 * Repeats [this] iterable [amount] times.
 */
fun <T> Iterable<T>.repeat(amount: Int): List<T> = asSequence().repeat(amount).toList()


/**
 * Repeats [this] sequence infinitely many times.
 */
fun <T> Sequence<T>.cyclic(): Sequence<T> = sequence { while (true) yieldAll(this@cyclic) }

/**
 * Returns a (lazy) [Sequence] which repeats [this] infinitely many times.
 */
fun <T> Iterable<T>.cyclic(): Sequence<T> = asSequence().cyclic()


/**
 * Returns all possible combinations of elements in [this] and [that].
 */
fun <T, U> Iterable<T>.cartesian(that: Iterable<U>): List<Pair<T, U>> =
    this.flatMap { a -> that.map { b -> Pair(a, b) } }


/**
 * Swaps the first and second entry.
 */
fun <A, B> Pair<A, B>.swap(): Pair<B, A> = Pair(second, first)

/**
 * Maps both entries using [transform].
 */
fun <A, T> Pair<A, A>.map(transform: (A) -> T): Pair<T, T> = Pair(transform(first), transform(second))

/**
 * Maps only the first entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapFirst(transform: (A) -> T): Pair<T, B> = Pair(transform(first), second)

/**
 * Maps the first entry in each pair using [transform].
 */
fun <A, B, T> Iterable<Pair<A, B>>.mapFirsts(transform: (A) -> T): List<Pair<T, B>> = map { it.mapFirst(transform) }

/**
 * Maps only the second entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapSecond(transform: (B) -> T): Pair<A, T> = Pair(first, transform(second))

/**
 * Maps the second entry in each pair using [transform].
 */
fun <A, B, T> Iterable<Pair<A, B>>.mapSeconds(transform: (B) -> T): List<Pair<A, T>> = map { it.mapSecond(transform) }

/**
 * Returns a pair containing the first two elements.
 */
fun <T> List<T>.asPair(): Pair<T, T> = Pair(this[0], this[1])

/**
 * Zips [first] with [second].
 */
fun <A, B> Pair<Iterable<A>, Iterable<B>>.zipped(): List<Pair<A, B>> = first zip second
