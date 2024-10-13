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
    private val asList = mutableListOf<T>()
    private val asSet = mutableSetOf<T>()
    private lateinit var max: T


    /**
     * Returns the [index]th element in the [Sequence].
     */
    operator fun get(index: Int): T {
        require(index >= 0) { "Index must be non-negative." }

        while (index >= asList.size) iterate()

        return asList[index]
    }

    /**
     * Returns `true` if and only if the underlying [Sequence] eventually returns [element].
     */
    operator fun contains(element: T): Boolean {
        if (asSet.isEmpty()) iterate()
        while (element > max) iterate()

        return element in asSet
    }


    /**
     * Iterates the sequence to the next element, and updates data structures where necessary.
     */
    private fun iterate() {
        val next = iterator.next()

        asList += next
        asSet += next
        max = next
    }
}

/**
 * Creates a [SearchableSequence] from this [Sequence].
 */
fun <T : Comparable<T>> Sequence<T>.searchable(): SearchableSequence<T> = SearchableSequence(this)
