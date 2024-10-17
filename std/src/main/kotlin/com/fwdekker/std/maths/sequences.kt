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
 * Allows efficiently iterating and searching through an infinite [Sequence] of non-decreasing values by caching
 * intermediate values.
 */
class CachedSequence<T : Comparable<T>>(base: Sequence<T>) : Sequence<T> {
    private val iterator = base.iterator()
    private val asList = mutableListOf<T>()
    private val asSet = mutableSetOf<T>()
    private lateinit var max: T


    /**
     * Returns the [index]th element in the [Sequence].
     */
    operator fun get(index: Int): T {
        require(index >= 0) { "Index must be non-negative." }

        while (index >= asList.size) step()

        return asList[index]
    }

    /**
     * Returns `true` if and only if the underlying [Sequence] eventually returns [element].
     */
    operator fun contains(element: T): Boolean {
        if (asSet.isEmpty()) step()
        while (element > max) step()

        return element in asSet
    }

    /**
     * Iterates through the underlying sequence, using cached results where possible.
     */
    override fun iterator(): Iterator<T> =
        iterator {
            asList.forEach { yield(it) }
            while (true) yield(step())
        }


    /**
     * Steps the sequence to the next element, and updates data structures where necessary.
     */
    private fun step(): T {
        val next = iterator.next()

        asList += next
        asSet += next
        max = next
        return next
    }
}

/**
 * Creates a [CachedSequence] from this [Sequence].
 */
fun <T : Comparable<T>> Sequence<T>.cached(): CachedSequence<T> = CachedSequence(this)
