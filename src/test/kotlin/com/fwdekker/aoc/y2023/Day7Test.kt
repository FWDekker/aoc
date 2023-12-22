package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Day.Companion.resource


/**
 * Tests for [Day7].
 */
object Day7Test : DayTest(
    ::Day7,
    listOf(
        Triple(resource(2023, 7, sample = 1), Day::part1, 6440L),
        Triple(resource(2023, 7, sample = 1), Day::part2, 5905L),
        Triple(resource(2023, 7), Day::part1, 248396258L),
        Triple(resource(2023, 7), Day::part2, 246436046L),
    )
)
