package com.fwdekker.aoc.std


/**
 * Returns the element at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun <T> List<T>.getMod(index: Int): T = this[((index % size) + size) % size]


/**
 * Repeats [this] sequence [amount] times.
 */
fun <T> Sequence<T>.repeat(amount: Int): Sequence<T> = sequence { for (i in 1..amount) yieldAll(this@repeat) }

/**
 * Repeats [this] collection [amount] times.
 */
fun <T> Collection<T>.repeat(amount: Int): List<T> = asSequence().repeat(amount).toList()


/**
 * Repeats [this] sequence infinitely many times.
 */
fun <T> Sequence<T>.cyclic(): Sequence<T> = sequence { while (true) yieldAll(this@cyclic) }

/**
 * Returns a (lazy) [Sequence] which repeats [this] infinitely many times.
 */
fun <T> Collection<T>.cyclic(): Sequence<T> = asSequence().cyclic()


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
fun <A, B, T> Collection<Pair<A, B>>.mapFirsts(transform: (A) -> T): List<Pair<T, B>> = map { it.mapFirst(transform) }

/**
 * Maps only the second entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapSecond(transform: (B) -> T): Pair<A, T> = Pair(first, transform(second))

/**
 * Maps the second entry in each pair using [transform].
 */
fun <A, B, T> Collection<Pair<A, B>>.mapSeconds(transform: (B) -> T): List<Pair<A, T>> = map { it.mapSecond(transform) }

/**
 * Returns a pair containing the first two elements.
 */
fun <T> List<T>.asPair(): Pair<T, T> = Pair(this[0], this[1])
