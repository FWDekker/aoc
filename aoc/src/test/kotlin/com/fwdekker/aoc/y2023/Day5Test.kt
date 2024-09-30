package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day5].
 */
object Day5Test : DayTest(
    ::Day5,
    listOf(
        Triple(resource(2023, 5, sample = 1), Day::part1, 35L),
        Triple(resource(2023, 5, sample = 1), Day::part2, 46L),
        Triple(resource(2023, 5), Day::part1, 579439039L),
        Triple(resource(2023, 5), Day::part2, 7873084L),
    )
)
