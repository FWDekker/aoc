package com.fwdekker.std

import kotlin.math.floor
import kotlin.math.sqrt


/**
 * Returns an infinite sequence of prime numbers.
 */
fun primes(): Sequence<Long> =
    sequence {
        yield(2L)
        yieldAll(
            generateSequence(3L) {
                var at = it + 2
                while (!at.isPrime()) at += 2
                at
            }
        )
    }


/**
 * Returns `true` if and only if [this] is a prime number.
 */
fun Long.isPrime(): Boolean {
    if (this < 2L) return false
    if (this == 2L) return true
    if (this % 2L == 0L) return false

    val max = floor(sqrt(this.toDouble())).toLong()
    for (factor in 3L..max step 2)
        if (this % factor == 0L) return false

    return true
}

/**
 * Returns the prime factors of [this].
 */
fun Long.factorize(): List<Long> =
    sequence {
        var remaining = this@factorize
        primes()
            .takeWhile { remaining > 1L }
            .forEach {
                while (remaining % it == 0L) {
                    yield(it)
                    remaining /= it
                }
            }
    }.toList()
