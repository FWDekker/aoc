package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Day.Companion.resource


/**
 * Tests for [Day17].
 */
object Day17Test : DayTest(
    ::Day17,
    listOf(
        Triple(resource(2023, 17, sample = 1), Day::part1, 102),
        Triple(resource(2023, 17, sample = 1), Day::part2, 94),
        Triple(resource(2023, 17, sample = 2), Day::part2, 71),
        Triple(resource(2023, 17), Day::part1, 814),
        Triple(resource(2023, 17), Day::part2, 974),
    )
)
