package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day10].
 */
object Day10Test : DayTest(
    ::Day10,
    listOf(
        Triple(Day.resource(2024, 10, sample = 1), Day::part1, 36),
        Triple(Day.resource(2024, 10, sample = 1), Day::part2, 81),
        Triple(Day.resource(2024, 10), Day::part1, 607),
        Triple(Day.resource(2024, 10), Day::part2, 1384),
    )
)
