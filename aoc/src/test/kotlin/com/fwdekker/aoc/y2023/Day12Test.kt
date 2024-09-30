package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day12].
 */
object Day12Test : DayTest(
    ::Day12,
    listOf(
        Triple(resource(2023, 12, sample = 1), Day::part1, 21L),
        Triple(resource(2023, 12, sample = 1), Day::part2, 525152L),
        Triple(resource(2023, 12), Day::part1, 7674L),
        Triple(resource(2023, 12), Day::part2, 4443895258186L),
    )
)
