package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource


/**
 * Tests for [Day19].
 */
object Day19Test : DayTest(
    ::Day19,
    listOf(
        Triple(resource(2023, 19, sample = 1), Day::part1, 19114),
        Triple(resource(2023, 19, sample = 1), Day::part2, 167409079868000L),
        Triple(resource(2023, 19), Day::part1, 446517),
        Triple(resource(2023, 19), Day::part2, 130090458884662L),
    )
)
