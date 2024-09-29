package com.fwdekker.euler

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Tests that a given [Problem] has the expected outputs for given file inputs.
 *
 * @param construct the constructor of the [Problem] to test
 * @param parseSolution parses a path to a solution file into the actually expected output
 * @param cases tests to run, expressed as [ProblemTestCase]s
 * @constructor
 */
abstract class ProblemTest<S>(
    construct: (String) -> Problem<S>,
    parseSolution: (String) -> S,
    cases: Collection<ProblemTestCase>
) : FunSpec({
    withData(
        nameFn = { (file, expected) -> "($file) = $expected" },
        cases
    ) { case ->
        val actual = construct(Problem.resource(case.problem, case.sample)).solve()
        val expected = parseSolution(Problem.resource(case.problem, case.sample, solution = true))

        actual shouldBe expected
    }
})

/**
 * A test case for [ProblemTest], expressed as parameters for [Problem.resource].
 */
data class ProblemTestCase(val problem: Int, val sample: Int? = null)
