package com.fwdekker.aoc

import com.fwdekker.aoc.std.Day
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Tests that a given [Day] has the expected outputs for given file inputs.
 *
 * @param day creates a new [Day] from a path to a file
 * @param tests tests to run, expressed as an input file, a reference to the part to run, and the expected output
 * @constructor
 */
abstract class DayTest(day: (String) -> Day, tests: Collection<Triple<String, (Day) -> Any, Any>>) : FunSpec({
    withData(
        nameFn = { (file, part, expected) -> "($file, ${if (part == Day::part1) "part 1" else "part 2"}) = $expected" },
        tests
    ) { (resource, part, expected) -> day(resource).let(part) shouldBe expected }
})
