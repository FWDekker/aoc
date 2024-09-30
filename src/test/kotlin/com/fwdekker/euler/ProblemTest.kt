package com.fwdekker.euler

import com.fwdekker.Tags
import com.fwdekker.std.read
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Tests that a given [Problem] has the expected outputs for given file inputs.
 *
 * @param construct the constructor of the [Problem] to test
 * @param parseSolution converts a string representation of a solution to the expected output type [S]
 * @param cases tests to run, expressed as [case]s
 * @param S the type of solutions to this problem
 * @constructor
 */
abstract class ProblemTest<S>(
    construct: (Int?) -> Problem<S>,
    parseSolution: (String) -> S,
    cases: Collection<Int?>
) : FunSpec({
    tags(Tags.EULER)

    withData(nameFn = { "sample $it" }, cases) { sample ->
        val problem = construct(sample)

        val actual = problem.solve()
        val expected = parseSolution(read(problem.resource(solution = true)))

        actual shouldBe expected
    }
})

/**
 * A test case for [ProblemTest], expressed as parameters for the constructor of the relevant [Problem].
 */
fun case(sample: Int? = null): Int? = sample
