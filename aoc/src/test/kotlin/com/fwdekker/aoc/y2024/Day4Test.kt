package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day4].
 */
object Day4Test : DayTest(
    ::Day4,
    listOf(
        Triple(Day.resource(2024, 4, sample = 1), Day::part1, 4),
        Triple(Day.resource(2024, 4, sample = 2), Day::part1, 18),
        Triple(Day.resource(2024, 4, sample = 2), Day::part2, 9),
        Triple(Day.resource(2024, 4), Day::part1, 2603),
        Triple(Day.resource(2024, 4), Day::part2, 1965),
    )
)
