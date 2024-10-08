package com.fwdekker.std.maths

import java.math.BigInteger


/**
 * Returns an infinite sequence of all natural numbers, [start]ing from the given number.
 */
fun naturalNumbersInt(start: Int = 0): Sequence<Int> =
    generateSequence(start) { it.inc() }

fun naturalNumbersLong(start: Long = 0L): Sequence<Long> =
    generateSequence(start) { it.inc() }

fun naturalNumbersBigInt(start: BigInteger = BigInteger.ZERO): Sequence<BigInteger> =
    generateSequence(start) { it.inc() }


/**
 * Returns an infinite sequence of Fibonacci numbers, starting with 1, 1, 2.
 */
fun fibonacciNumbersInt(): Sequence<Int> =
    sequence {
        var a = 1
        var b = 1
        while (true) yield(a).also { a = b.also { b += a } }
    }

fun fibonacciNumbersLong(): Sequence<Long> =
    sequence {
        var a = 1L
        var b = 1L
        while (true) yield(a).also { a = b.also { b += a } }
    }

fun fibonacciNumbersBigInt(): Sequence<BigInteger> =
    sequence {
        var a = BigInteger.ONE
        var b = BigInteger.ONE
        while (true) yield(a).also { a = b.also { b += a } }
    }


/**
 * Returns an infinite sequence of all triangle numbers, starting with 1, 3, 6.
 *
 * The `i`-th triangle number is `1 + 2 + ... + i`.
 */
fun triangleNumbersInt(): Sequence<Int> =
    sequence {
        var sum = 1
        var inc = 2
        while (true) yield(sum.also { sum += inc++ })
    }

fun triangleNumbersLong(): Sequence<Long> =
    sequence {
        var sum = 1L
        var inc = 2L
        while (true) yield(sum.also { sum += inc++ })
    }

fun triangleNumbersBigInt(): Sequence<BigInteger> =
    sequence {
        var sum = BigInteger.ONE
        var inc = BigInteger.TWO
        while (true) yield(sum.also { sum += inc++ })
    }


/**
 * Allows efficiently searching through an infinite [Sequence] of non-decreasing values by caching intermediate values.
 */
class SearchableSequence<T : Comparable<T>>(base: Sequence<T>) {
    private val iterator = base.iterator()
    private val iterated = mutableSetOf<T>()


    /**
     * Returns `true` if and only if the underlying [Sequence] eventually returns [element].
     */
    operator fun contains(element: T): Boolean {
        while (iterated.lastOrNull().let { it == null || it < element })
            iterated += iterator.next()

        return element in iterated
    }
}

/**
 * Creates a [SearchableSequence] from this [Sequence].
 */
fun <T : Comparable<T>> Sequence<T>.searchable(): SearchableSequence<T> = SearchableSequence(this)
