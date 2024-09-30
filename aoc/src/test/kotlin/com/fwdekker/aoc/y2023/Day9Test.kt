package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource


/**
 * Tests for [Day9].
 */
object Day9Test : DayTest(
    ::Day9,
    listOf(
        Triple(resource(2023, 9, sample = 1), Day::part1, 114),
        Triple(resource(2023, 9, sample = 1), Day::part2, 2),
        Triple(resource(2023, 9), Day::part1, 1939607039),
        Triple(resource(2023, 9), Day::part2, 1041),
    )
)
