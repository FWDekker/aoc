package com.fwdekker.std

import com.fwdekker.Tags
import com.fwdekker.shouldContainExactlyElementsOf
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData


/**
 * Unit tests for `primes.kt`.
 */
object PrimesTest : DescribeSpec({
    tags(Tags.STD)

    val knownPrimes =
        listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97).toLongs()


    describe("primes") {
        it("returns the first 25 primes") {
            primes().take(25).shouldContainExactlyElementsOf(knownPrimes)
        }
    }

    describe("isPrime") {
        it("correctly classifies all integers from -1 to 100 into primes and composites") {
            (-1L..100L).filter { it.isPrime() }.shouldContainExactlyElementsOf(knownPrimes)
        }
    }

    describe("factorize") {
        withData(
            nameFn = { "${it.first} factorizes as ${it.second}" },
            -1L to emptyList(),
            0L to emptyList(),
            1L to emptyList(),
            2L to listOf(2L),
            3L to listOf(3L),
            4L to listOf(2L, 2L),
            5L to listOf(5),
            6L to listOf(2L, 3L),
            9L to listOf(3L, 3L),
            18L to listOf(2L, 3L, 3L),
            81L to listOf(3L, 3L, 3L, 3L),
        ) { (value, factors) ->
            value.factorize().shouldContainExactlyElementsOf(factors)
        }
    }
})
