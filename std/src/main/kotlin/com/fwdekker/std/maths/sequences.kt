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
 * Returns an infinite sequence of all natural numbers, starting at 0.
 */
fun naturalNumbers(): Sequence<Long> = generateSequence(0L) { it.inc() }

fun naturalNumbersBig(): Sequence<BigInteger> = generateSequence(BigInteger.ZERO) { it.inc()  }


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
