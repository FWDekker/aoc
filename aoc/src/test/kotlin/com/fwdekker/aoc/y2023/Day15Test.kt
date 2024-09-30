package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day15].
 */
object Day15Test : DayTest(
    ::Day15,
    listOf(
        Triple(resource(2023, 15, sample = 1), Day::part1, 1320),
        Triple(resource(2023, 15, sample = 1), Day::part2, 145),
        Triple(resource(2023, 15), Day::part1, 511343),
        Triple(resource(2023, 15), Day::part2, 294474),
    )
)
