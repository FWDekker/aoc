package com.fwdekker.std.maths


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

/**
 * Returns an infinite sequence of all natural numbers, starting at 0.
 */
fun naturalNumbers(): Sequence<Long> = generateSequence(0L) { it + 1L }

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
