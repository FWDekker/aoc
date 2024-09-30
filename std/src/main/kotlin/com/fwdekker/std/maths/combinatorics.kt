package com.fwdekker.std.maths

import java.math.BigInteger


/**
 * Returns the factorial of [this] number.
 */
fun BigInteger.factorial(): BigInteger = factorial(this)

private tailrec fun factorial(number: BigInteger, acc: BigInteger = BigInteger.ONE): BigInteger =
    if (number <= BigInteger.ONE) acc
    else factorial(number - BigInteger.ONE, acc * number)


/**
 * Returns all possible combinations of elements in [this] and [that].
 */
fun <T, U> Iterable<T>.cartesian(that: Iterable<U>): List<Pair<T, U>> =
    this.flatMap { a -> that.map { b -> Pair(a, b) } }

/**
 * Returns all (not necessarily proper) subsets of [this].
 */
fun <T> Collection<T>.powerSet(): Collection<Collection<T>> = powerSet(this)

private tailrec fun <T> powerSet(rem: Collection<T>, acc: List<List<T>> = listOf(emptyList())): List<List<T>> =
    when {
        rem.isEmpty() -> acc
        else -> powerSet(rem.drop(1), acc + acc.map { it + rem.first() })
    }
