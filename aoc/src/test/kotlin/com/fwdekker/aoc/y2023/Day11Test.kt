package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day11].
 */
object Day11Test : DayTest(
    ::Day11,
    listOf(
        Triple(resource(2023, 11, sample = 1), Day::part1, 374L),
        Triple(resource(2023, 11), Day::part1, 9795148L),
        Triple(resource(2023, 11), Day::part2, 650672493820L),
    )
)
