package com.fwdekker.euler

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Tests that a given [Problem] has the expected outputs for given file inputs.
 *
 * @param problem the constructor of the [Problem] to test
 * @param tests tests to run, expressed as an input file and the expected output
 * @constructor
 */
abstract class ProblemTest<S>(problem: (String) -> Problem<S>, tests: Collection<Pair<String, S>>) : FunSpec({
    withData(
        nameFn = { (file, expected) -> "($file) = $expected" },
        tests
    ) { (resource, expected) -> problem(resource).solve() shouldBe expected }
})
