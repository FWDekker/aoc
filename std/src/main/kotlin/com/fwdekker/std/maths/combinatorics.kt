package com.fwdekker.std.maths

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

fun BigInteger.choose(k: BigInteger): BigInteger = this.factorial(k) / k.factorial()


/**
 * Returns all possible combinations of elements in [this] and [that].
 */
fun <T, U> Iterable<T>.cartesian(that: Iterable<U>): List<Pair<T, U>> =
    this.flatMap { a -> that.map { b -> Pair(a, b) } }

/**
 * Returns all (not necessarily proper) subsets of [this].
 */
fun <T> Collection<T>.powerSet(includeEmpty: Boolean = true): Collection<Collection<T>> =
    powerSet(this).let { if (includeEmpty) it else it.drop(1) }

private tailrec fun <T> powerSet(rem: Collection<T>, acc: List<List<T>> = listOf(emptyList())): List<List<T>> =
    when {
        rem.isEmpty() -> acc
        else -> powerSet(rem.drop(1), acc + acc.map { it + rem.first() })
    }
