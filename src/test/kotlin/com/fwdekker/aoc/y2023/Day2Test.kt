package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day


/**
 * Tests for [Day2].
 */
object Day2Test : DayTest(
    ::Day2,
    listOf(
        Triple(Day.resource(2023, 2, sample = 1), Day::part1, 8),
        Triple(Day.resource(2023, 2, sample = 1), Day::part2, 2286),
        Triple(Day.resource(2023, 2), Day::part1, 2449),
        Triple(Day.resource(2023, 2), Day::part2, 63981),
    )
)
