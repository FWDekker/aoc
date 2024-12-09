package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day8].
 */
object Day8Test : DayTest(
    ::Day8,
    listOf(
        Triple(Day.resource(2024, 8, sample = 1), Day::part1, 2),
        Triple(Day.resource(2024, 8, sample = 2), Day::part1, 4),
        Triple(Day.resource(2024, 8, sample = 3), Day::part1, 4),
        Triple(Day.resource(2024, 8, sample = 4), Day::part1, 14),
        Triple(Day.resource(2024, 8, sample = 5), Day::part2, 9),
        Triple(Day.resource(2024, 8, sample = 4), Day::part2, 34),
        Triple(Day.resource(2024, 8), Day::part1, 303),
        Triple(Day.resource(2024, 8), Day::part2, 1045),
    )
)
