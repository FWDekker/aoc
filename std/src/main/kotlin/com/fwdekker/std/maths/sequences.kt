package com.fwdekker.std.maths

import java.math.BigInteger


/**
 * Returns an infinite sequence of Fibonacci numbers, starting with 1, 1, 2.
 */
fun fibonacci(): Sequence<Long> =
    sequence {
        var a = 1L
        var b = 1L

        while (true) {
            yield(a)
            a = b.also { b += a }
        }
    }

fun fibonacciBig(): Sequence<BigInteger> =
    sequence {
        var a = BigInteger.ONE
        var b = BigInteger.ONE

        while (true) {
            yield(a)
            a = b.also { b += a }
        }
    }


/**
 * Returns an infinite sequence of all natural numbers, [start]ing from the given number.
 */
fun naturalNumbers(start: Long = 0L): Sequence<Long> = generateSequence(start) { it.inc() }

fun naturalNumbersInt(start: Int = 0): Sequence<Int> = generateSequence(start) { it.inc() }

fun naturalNumbersBig(start: Long = 0L): Sequence<BigInteger> = generateSequence(start.toBigInteger()) { it.inc() }


/**
 * Returns an infinite sequence of all triangle numbers, starting at 1.
 *
 * The i-th triangle number is 1 + 2 + ... + i.
 */
fun triangleNumbers(): Sequence<Long> =
    sequence {
        var sum = 0L
        var inc = 1L
        while (true) {
            sum += inc
            inc++
            yield(sum)
        }
    }

fun triangleNumbersBig(): Sequence<BigInteger> =
    sequence {
        var sum = BigInteger.ZERO
        var inc = BigInteger.ONE
        while (true) {
            sum += inc
            inc++
            yield(sum)
        }
    }
