package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day16].
 */
object Day16Test : DayTest(
    ::Day16,
    listOf(
        Triple(Day.resource(2024, 16, sample = 1), Day::part1, 7036L),
        Triple(Day.resource(2024, 16, sample = 2), Day::part1, 11048L),
        Triple(Day.resource(2024, 16, sample = 1), Day::part2, 45),
        Triple(Day.resource(2024, 16, sample = 2), Day::part2, 64),
        Triple(Day.resource(2024, 16), Day::part1, 122492L),
        Triple(Day.resource(2024, 16), Day::part2, 520),
    )
)
