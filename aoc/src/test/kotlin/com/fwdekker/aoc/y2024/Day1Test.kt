package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day1].
 */
object Day1Test : DayTest(
    ::Day1,
    listOf(
        Triple(Day.resource(2024, 1, sample = 1), Day::part1, 11),
        Triple(Day.resource(2024, 1, sample = 1), Day::part2, 31),
        Triple(Day.resource(2024, 1), Day::part1, 1590491),
        Triple(Day.resource(2024, 1), Day::part2, 22588371),
    )
)
