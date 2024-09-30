package com.fwdekker.aoc

import com.fwdekker.Tags
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Tests that a given [Day] has the expected outputs for given file inputs.
 *
 * @param day the constructor of the [Day] to test
 * @param tests tests to run, expressed as an input file, a reference to the part to run, and the expected output
 * @constructor
 */
abstract class DayTest(day: (String) -> Day, tests: Collection<Triple<String, (Day) -> Any, Any>>) : FunSpec({
    tags(Tags.AOC)

    withData(
        nameFn = { (file, part, expected) -> "($file, ${if (part == Day::part1) "part 1" else "part 2"}) = $expected" },
        tests
    ) { (resource, part, expected) -> day(resource).let(part) shouldBe expected }
})
