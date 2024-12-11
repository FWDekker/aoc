package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day11].
 */
object Day11Test : DayTest(
    ::Day11,
    listOf(
        Triple(Day.resource(2024, 11, sample = 1), Day::part1, 7L),
        Triple(Day.resource(2024, 11, sample = 2), Day::part1, 55312L),
        Triple(Day.resource(2024, 11), Day::part1, 216996L),
        Triple(Day.resource(2024, 11), Day::part2, 257335372288947L),
    )
)
