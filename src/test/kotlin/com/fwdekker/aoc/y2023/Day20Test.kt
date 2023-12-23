package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Day.Companion.resource


/**
 * Tests for [Day20].
 */
object Day20Test : DayTest(
    ::Day20,
    listOf(
        Triple(resource(2023, 20, sample = 1), Day::part1, 32000000L),
        Triple(resource(2023, 20, sample = 2), Day::part1, 11687500L),
        Triple(resource(2023, 20), Day::part1, 825167435L),
        Triple(resource(2023, 20), Day::part2, 225514321828633L),
    )
)
