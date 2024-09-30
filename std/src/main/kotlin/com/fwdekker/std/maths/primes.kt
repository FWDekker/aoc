package com.fwdekker.std.maths

import kotlin.math.floor
import kotlin.math.sqrt


/**
 * Returns the greatest common divisor of [a] and [b].
 */
fun gcd(a: Int, b: Int): Int = gcd(a.toLong(), b.toLong()).toIntExact()

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/**
 * Returns the greatest common divisor of all elements in [this].
 */
fun Iterable<Int>.gcd(): Int = toLongs().gcd().toIntExact()

fun Iterable<Long>.gcd(): Long = reduce(::gcd)

/**
 * Returns the least common multiplier of [a] and [b].
 */
fun lcm(a: Int, b: Int): Int = lcm(a.toLong(), b.toLong()).toIntExact()

fun lcm(a: Long, b: Long): Long = a * (b / gcd(a, b))

/**
 * Returns the least common multiple of all elements in [this].
 */
fun Iterable<Int>.lcm(): Int = toLongs().lcm().toIntExact()

fun Iterable<Long>.lcm(): Long = reduce(::lcm)


/**
 * Returns `true` if and only if [this] is a prime number.
 */
fun Int.isPrime(): Boolean = toLong().isPrime()

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
fun Int.factorize(): List<Int> = toLong().factorize().toInts()

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

/**
 * Returns all (positive) natural numbers that divide [this].
 */
fun Int.divisors(): Set<Int> = toLong().divisors().toInts().toSet()

fun Long.divisors(): Set<Long> =
    (listOf(1L) + factorize()).powerSet().filter { it.isNotEmpty() }.map { it.product() }.toSet()


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
