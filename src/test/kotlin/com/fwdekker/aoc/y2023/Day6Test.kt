package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Day.Companion.resource


/**
 * Tests for [Day6].
 */
object Day6Test : DayTest(
    ::Day6,
    listOf(
        Triple(resource(2023, 6, sample = 1), Day::part1, 288L),
        Triple(resource(2023, 6, sample = 1), Day::part2, 71503L),
        Triple(resource(2023, 6), Day::part1, 114400L),
        Triple(resource(2023, 6), Day::part2, 21039729L),
    )
)
