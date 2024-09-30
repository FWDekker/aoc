package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource


/**
 * Tests for [Day16].
 */
object Day16Test : DayTest(
    ::Day16,
    listOf(
        Triple(resource(2023, 16, sample = 1), Day::part1, 46),
        Triple(resource(2023, 16, sample = 1), Day::part2, 51),
        Triple(resource(2023, 16), Day::part1, 7046),
        Triple(resource(2023, 16), Day::part2, 7313),
    )
)
