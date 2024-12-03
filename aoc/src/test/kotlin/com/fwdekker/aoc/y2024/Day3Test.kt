package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day3].
 */
object Day3Test : DayTest(
    ::Day3,
    listOf(
        Triple(Day.resource(2024, 3, sample = 1), Day::part1, 161),
        Triple(Day.resource(2024, 3, sample = 2), Day::part2, 48),
        Triple(Day.resource(2024, 3), Day::part1, 169021493),
        Triple(Day.resource(2024, 3), Day::part2, 111762583),
    )
)
