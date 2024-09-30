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
 * Returns the prime factors of [this] in a map with the number of occurrences of each factor.
 */
fun Int.factorizeGroups(): Map<Int, Int> = toLong().factorizeGroups().mapKeys { (k, _) -> k.toIntExact() }

fun Long.factorizeGroups(): Map<Long, Int> {
    val factors = mutableMapOf<Long, Int>()

    var remaining = this
    primes()
        .takeWhile { remaining > 1L }
        .forEach { factor ->
            while (remaining % factor == 0L) {
                factors.merge(factor, 1, Int::plus)
                remaining /= factor
            }
        }

    return factors
}

/**
 * Returns all (positive) natural numbers that divide [this], excluding [this].
 */
fun Int.divisors(): Set<Int> = toLong().divisors().toInts().toSet()

fun Long.divisors(): Set<Long> =
    (listOf(1L) + factorize()).powerSet(includeEmpty = false).map { it.product() }.toSet().minus(this)

/**
 * Returns the number of [divisors]. More efficient than first calling [divisors] and then `size`!
 */
fun Int.divisorsCount(): Int = toLong().divisorsCount().toIntExact()

fun Long.divisorsCount(): Long = factorizeGroups().values.productOf { it + 1 } - 1

/**
 * Returns the sum of [divisors]. More efficient than first calling [divisors] and then `sum`!
 */
fun Int.divisorsSum(): Int = toLong().divisorsSum().toIntExact()

fun Long.divisorsSum(): Long {
    // TODO: Clean up this method! Not really my code tbh :(
    if (this == 1L) return 0L

    val end = sqrt(toDouble()).toLong()

    var prod = 1L
    var n = this

    (2L..end).forEach { k ->
        var p = 1L
        while (n % k == 0L) {
            p = p * k + 1L
            n /= k
        }
        prod *= p
    }
    if (n > 1L)
        prod *= n + 1L

    return prod - this
}


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
