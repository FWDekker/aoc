package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day6].
 */
object Day6Test : DayTest(
    ::Day6,
    listOf(
        Triple(Day.resource(2024, 6, sample = 1), Day::part1, 41),
        Triple(Day.resource(2024, 6, sample = 1), Day::part2, 6),
        Triple(Day.resource(2024, 6), Day::part1, 5080),
        Triple(Day.resource(2024, 6), Day::part2, 1919),
    )
)
