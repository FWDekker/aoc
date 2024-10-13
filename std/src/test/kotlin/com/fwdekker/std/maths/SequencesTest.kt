package com.fwdekker.std.maths

import com.fwdekker.containExactlyInSameOrderElementsOf
import com.fwdekker.std.toBigIntegers
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.be
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.resource.resourceAsString
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.math.BigInteger
import kotlin.reflect.jvm.kotlinFunction


/**
 * Unit tests for `sequences.kt`.
 */
object SequencesTest : DescribeSpec({
    withData(
        nameFn = { it.name },
        Method("naturalNumbers"),
        Method("fibonacciNumbers"),
        Method("triangleNumbers"),
    ) { method ->
        withData(
            nameFn = { it.suffix },
            Variant("Int", { it <= Int.MAX_VALUE.toBigInteger() }, { it.toInt() }),
            Variant("Long", { it <= Long.MAX_VALUE.toBigInteger() }, { it.toLong() }),
            Variant("BigInt", { true }, { it }),
        ) { variant ->
            val expected = resourceAsString("/maths/${method.name}.txt")
                .toBigIntegers('\n')
                .filter(variant.filter)
                .map(variant.parse)
            val actual = method.find(variant).invoke().take(expected.size).toList()

            actual should containExactlyInSameOrderElementsOf(expected)
        }
    }


    describe("SearchableSequence") {
        describe("get") {
            it("throws an exception for negative indices") {
                shouldThrow<IllegalArgumentException> { naturalNumbersInt().searchable()[-1] }
            }

            it("returns the 0th element") {
                naturalNumbersInt().searchable()[0] shouldBe 0
            }

            it("returns the 1st element") {
                naturalNumbersInt().searchable()[1] shouldBe 1
            }

            it("returns the 1st element again") {
                naturalNumbersInt().searchable()[1]
                naturalNumbersInt().searchable()[1] shouldBe 1
            }

            it("does not traverse elements at previously seen indices") {
                var iterations = 0
                val searchable = naturalNumbersInt().map { iterations++; it }.searchable()

                searchable[5]
                iterations = 0

                searchable[3]
                iterations shouldBe 0
            }

            it("traverses elements only up to the requested index") {
                var iterations = 0
                val searchable = naturalNumbersInt().map { iterations++; it }.searchable()

                searchable[3]
                iterations = 0

                searchable[5]
                iterations shouldBe 2
            }
        }

        describe("contains") {
            it("does not contain values below the first value") {
                naturalNumbersInt().searchable().contains(-1).shouldBeFalse()
            }

            it("contains the first value in the sequence") {
                naturalNumbersInt().searchable().contains(0).shouldBeTrue()
            }

            it("contains the second value in the sequence") {
                naturalNumbersInt().searchable().contains(1).shouldBeTrue()
            }

            it("contains the first value in the sequence after searching for the second") {
                val searchable = naturalNumbersInt().searchable()

                searchable.contains(1)
                searchable.contains(0).shouldBeTrue()
            }

            it("traverses each element in the sequence only once when checking for multiple elements") {
                var iterations = 0
                val searchable = naturalNumbersInt().map { iterations++; it }.searchable()

                searchable.contains(6)
                searchable.contains(9)
                searchable.contains(3)

                iterations should be(10)
            }
        }
    }
}) {
    /**
     * A method that generates [Sequence]s. A method can have several [Variant]s.
     *
     * @property name The base name of the method.
     */
    data class Method(val name: String) {
        /**
         * Finds the actual [kotlin.reflect.KFunction] corresponding to the given [variant] of this [Method], and
         * returns a lambda for invoking it directly with default parameters.
         */
        @Suppress("UNCHECKED_CAST")
        fun <N : Number> find(variant: Variant<N>): () -> Sequence<N> =
            this::class.java.classLoader.loadClass("com.fwdekker.std.maths.SequencesKt")
                .methods.find { it.name == "${this.name}${variant.suffix}" }!!
                .kotlinFunction!!
                .let { { it.callBy(emptyMap()) as Sequence<N> } }
    }

    /**
     * A type-specific variant of a [Method].
     *
     * @property suffix The suffix appended to the [Method.name].
     * @property filter Returns `true` for expected outputs that can be generated by this variant.
     * @property parse Transforms expected outputs into the type generated by this variant.
     */
    data class Variant<N : Number>(
        val suffix: String,
        val filter: (BigInteger) -> Boolean,
        val parse: (BigInteger) -> N
    )
}
