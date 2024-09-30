package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day


/**
 * Tests for [Day1].
 */
object Day1Test : DayTest(
    ::Day1,
    listOf(
        Triple(Day.resource(2023, 1, sample = 1), Day::part1, 142),
        Triple(Day.resource(2023, 1, sample = 2), Day::part2, 281),
        Triple(Day.resource(2023, 1), Day::part1, 56042),
        Triple(Day.resource(2023, 1), Day::part2, 55358),
    )
)
