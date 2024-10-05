package com.fwdekker.std.maths

import com.fwdekker.containExactlyElementsOf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.be
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


/**
 * Unit tests for `primes.kt`.
 */
object PrimesTest : DescribeSpec({
    val knownPrimes =
        listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
            .toLongs()


    describe("gcd") {
        withData(
            nameFn = { (inputs, expected) -> "(${inputs.first}, ${inputs.second}) -> $expected" },
            // Returns 0 if both are 0
            Pair(0L, 0L) to 0L,
            // Returns the non-0 value
            Pair(0L, 1L) to 1L,
            Pair(11L, 0L) to 11L,
            // Returns absolute non-0 value
            Pair(0L, -7L) to 7L,
            Pair(-4L, 0L) to 4L,
            // Returns 1 if either is 1
            Pair(1L, 8L) to 1L,
            Pair(9L, 1L) to 1L,
            // Returns that number if both are the same
            Pair(18L, 18L) to 18L,
            // Returns as if both are positive
            Pair(-14L, 14L) to 14L,
            Pair(-85L, -75L) to 5L,
            // Returns 1 if they are coprime
            Pair(23L, 17L) to 1L,
            // Returns a shared prime divisor
            Pair(6L, 9L) to 3L,
            // Returns a shared composite divisor
            Pair(105L, 70L) to 35L,
        ) { (inputs, expected) ->
            gcd(inputs.first, inputs.second) shouldBe expected
        }

        withData(
            nameFn = { (inputs, expected) -> "(${inputs}) -> $expected" },
            // Returns 0 for empty list
            emptyList<Long>() to 0L,
            // Returns 0 if all are 0
            listOf(0L, 0L, 0L) to 0L,
            // Returns the only non-0 value
            listOf(0L, 9L, 0L) to 9L,
            // Returns the gcd
            listOf(9L, -9L, 12L, 27L) to 3L,
        ) { (inputs, expected) ->
            inputs.gcd() shouldBe expected
        }
    }

    describe("lcm") {
        it("cannot compute the lcm of 0 and 0") {
            shouldThrow<IllegalArgumentException> { lcm(0L, 0L) }
        }

        withData(
            nameFn = { (inputs, expected) -> "(${inputs.first}, ${inputs.second}) -> $expected" },
            // Returns 0 if either is 0
            Pair(0L, 9L) to 0L,
            Pair(-5L, 0L) to 0L,
            // Returns the other value if either is 1
            Pair(1L, 8L) to 8L,
            Pair(9L, 1L) to 9L,
            // Returns that number if both are the same
            Pair(75L, 75L) to 75L,
            // Returns as if both are positive
            Pair(66L, -66L) to 66L,
            Pair(-28L, -42L) to 84L,
            // Returns the higher number if one's factors are a subset of the other
            Pair(9L, 27L) to 27L,
            // Returns the product if they are coprime
            Pair(11L, 7L) to 77L,
            // Returns the product taking into account the shared factors
            Pair(90L, 50L) to 450L,
        ) { (inputs, expected) ->
            lcm(inputs.first, inputs.second) shouldBe expected
        }

        it("cannot compute the lcm of only 0s") {
            shouldThrow<IllegalArgumentException> { listOf(0L, 0L, 0L).lcm() }
        }

        it("cannot compute the lcm of an empty list") {
            shouldThrow<IllegalArgumentException> { emptyList<Long>().lcm() }
        }

        withData(
            nameFn = { (inputs, expected) -> "(${inputs}) -> $expected" },
            // Returns the zero if there is at least one non-zero value
            listOf(0L, 0L, 9L, 0L) to 0L,
            // Returns the lcm of the non-zero numbers
            listOf(0L, 0L, 7L, 0L, 3L, 0L) to 0L,
        ) { (inputs, expected) ->
            inputs.lcm() shouldBe expected
        }
    }


    describe("factorize") {
        withData(
            nameFn = { "prime factors of ${it.first} are ${it.second}" },
            -8L to emptyList(),
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
        ) { (value, expected) ->
            value.factorize() should containExactlyElementsOf(expected)
        }
    }

    describe("factorizeGroups") {
        withData(
            nameFn = { "prime factors of ${it.first} are ${it.second}" },
            -8L to emptyMap(),
            0L to emptyMap(),
            1L to emptyMap(),
            2L to mapOf(2L to 1),
            3L to mapOf(3L to 1),
            4L to mapOf(2L to 2),
            5L to mapOf(5L to 1),
            6L to mapOf(2L to 1, 3L to 1),
            9L to mapOf(3L to 2),
            18L to mapOf(2L to 1, 3L to 2),
            81L to mapOf(3L to 4),
        ) { (value, expected) ->
            value.factorizeGroups().toList() should containExactlyElementsOf(expected.toList())
        }
    }

    describe("divisors") {
        withData(
            nameFn = { "proper divisors of ${it.first} are ${it.second}" },
            -8L to emptySet(),
            0L to emptySet(),
            1L to emptySet(),
            2L to setOf(1L),
            3L to setOf(1L),
            4L to setOf(1L, 2L),
            5L to setOf(1L),
            6L to setOf(1L, 2L, 3L),
            9L to setOf(1L, 3L),
            18L to setOf(1L, 2L, 3L, 6L, 9L),
            81L to setOf(1L, 3L, 9L, 27L),
        ) { (value, expected) ->
            value.divisors() should containExactlyElementsOf(expected)
            value.divisorsCount() should be(expected.size)
            value.divisorsSum() should be(expected.sum())
        }
    }


    describe("isPrime") {
        it("correctly classifies all integers from -1 to 100 into primes and composites") {
            (-1L..100L).filter { it.isPrime() } should containExactlyElementsOf(knownPrimes)
        }
    }

    describe("primes") {
        it("returns the first 25 primes") {
            primes().take(25).toList() should containExactlyElementsOf(knownPrimes)
        }
    }
})
