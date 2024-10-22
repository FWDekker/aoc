package com.fwdekker.std.maths

import com.fwdekker.std.collections.swapAt
import com.fwdekker.std.foldSelf
import java.math.BigInteger


/**
 * Returns the (falling) factorial of [this] number (to [that]).
 *
 * The factorial of `n` (denoted `n!`) is `n * (n - 1) * (n - 2) * ... * 1`.
 *
 * The falling factorial of `n` to `k` is `n! / (n - k)!`, which is `n * (n - 1) * (n - 2) * ... * (n - (k - 1))`. If we
 * have `n = k` (or `n - 1 = k`), then this evaluates to simply `n!`.
 *
 * If either [this] or [that] is non-positive, the output is one.
 */
fun Int.factorial(that: Int = this): BigInteger = toBigInteger().factorial(that.toBigInteger())

fun Long.factorial(that: Long = this): BigInteger = toBigInteger().factorial(that.toBigInteger())

fun BigInteger.factorial(that: BigInteger = this): BigInteger = factorial(this, that)

private tailrec fun factorial(number: BigInteger, steps: BigInteger, acc: BigInteger = BigInteger.ONE): BigInteger =
    if (number < BigInteger.ZERO) BigInteger.ONE
    else if (number == BigInteger.ZERO || steps <= BigInteger.ZERO) acc
    else factorial(number - BigInteger.ONE, steps.dec(), acc * number)

/**
 * Returns the binomial coefficient of [this] and [k].
 */
fun Int.choose(k: Int): BigInteger = toBigInteger().choose(k.toBigInteger())

fun Long.choose(k: Long): BigInteger = toBigInteger().choose(k.toBigInteger())

fun BigInteger.choose(k: BigInteger): BigInteger {
    require(this >= BigInteger.ZERO && k >= BigInteger.ZERO) { "Both inputs must be non-negative." }

    val overlap = min(BigInteger.ZERO, k * BigInteger.TWO - this)

    return if (k > this) BigInteger.ZERO
    else if (k == this) BigInteger.ONE
    else this.factorial(k - overlap) / (k - overlap).factorial()
}


/**
 * Returns all possible combinations of elements in [this] and [that].
 */
fun <T, U> Iterable<T>.cartesian(that: Iterable<U>): Sequence<Pair<T, U>> =
    asSequence().flatMap { a -> that.map { b -> Pair(a, b) } }

/**
 * Returns all possible ways of choosing exactly [amount] elements from [this], with repetition.
 */
fun <T> List<T>.combinations(amount: Int): Sequence<List<T>> {
    require(amount >= 0) { "Cannot choose $amount elements from a list." }

    return if (amount == 0) emptySequence()
    else asSequence()
        .let { base ->
            base.map { listOf(it) }.foldSelf(amount - 1) { all -> all.flatMap { combo -> base.map { combo + it } } }
        }
}

fun <T> Collection<T>.combinations(amount: Int): Sequence<List<T>> = toList().combinations(amount)

/**
 * Returns all possible permutations of [this] collection's elements.
 *
 * Uses the Steinhaus–Johnson–Trotter algorithm, with Even's speedup.
 */
fun <T> List<T>.permutations(): Sequence<List<T>> =
    if (isEmpty()) emptySequence()
    else sequence {
        yield(toList())

        val permutation = indices.toMutableList()
        val directions = indices.map { if (it == 0) 0 else -1 }.toMutableList()

        while (true) {
            val swapFrom = indices.filter { directions[it] != 0 }.maxByOrNull { permutation[it] } ?: return@sequence
            val swapTo = swapFrom + directions[swapFrom]
            val swapAfter = swapTo + directions[swapFrom]
            val swapValue = permutation[swapFrom]

            permutation.swapAt(swapFrom, swapTo)
            directions.swapAt(swapFrom, swapTo)
            if (swapTo == 0 || swapTo == size - 1 || permutation[swapAfter] > swapValue) directions[swapTo] = 0
            indices.filter { permutation[it] > swapValue }.forEach { directions[it] = if (it < swapTo) 1 else -1 }

            yield(permutation.map { this@permutations[it] })
        }
    }

fun <T> Collection<T>.permutations(): Sequence<List<T>> = toList().permutations()

/**
 * Returns all (not necessarily proper) subsets of [this].
 */
fun <T> List<T>.powerSet(minSize: Int = 0, maxSize: Int = this.size): Sequence<List<T>> {
    require(minSize <= maxSize) { "Minimum size must be no more than maximum size." }
    require(minSize >= 0) { "Minimum size must be non-negative." }
    require(maxSize <= size) { "Maximum size must be at most collection size." }

    return BigInteger.ZERO.rangeUntil(BigInteger.TWO.pow(size))
        .asSequence()
        .map { n ->
            n.toString(2).padStart(size, '0')
                .withIndex()
                .filter { (_, bit) -> bit == '1' }
                .map { (idx, _) -> this[idx] }
        }
        .filter { it.size in minSize..maxSize }
}

fun <T> Collection<T>.powerSet(minSize: Int = 0, maxSize: Int = this.size): Sequence<List<T>> =
    toList().powerSet(minSize, maxSize)
