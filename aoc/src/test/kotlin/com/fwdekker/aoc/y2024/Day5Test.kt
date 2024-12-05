package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day5].
 */
object Day5Test : DayTest(
    ::Day5,
    listOf(
        Triple(Day.resource(2024, 5, sample = 1), Day::part1, 143),
        Triple(Day.resource(2024, 5, sample = 1), Day::part2, 123),
        Triple(Day.resource(2024, 5), Day::part1, 6051),
        Triple(Day.resource(2024, 5), Day::part2, 5093),
    )
)
