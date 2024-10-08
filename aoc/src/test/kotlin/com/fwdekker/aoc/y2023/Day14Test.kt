package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day14].
 */
object Day14Test : DayTest(
    ::Day14,
    listOf(
        Triple(resource(2023, 14, sample = 1), Day::part1, 136),
        Triple(resource(2023, 14, sample = 1), Day::part2, 64),
        Triple(resource(2023, 14), Day::part1, 103614),
        Triple(resource(2023, 14), Day::part2, 83790),
    )
)
