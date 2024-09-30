package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day8].
 */
object Day8Test : DayTest(
    ::Day8,
    listOf(
        Triple(resource(2023, 8, sample = 1), Day::part1, 2L),
        Triple(resource(2023, 8, sample = 2), Day::part1, 6L),
        Triple(resource(2023, 8, sample = 3), Day::part2, 6L),
        Triple(resource(2023, 8), Day::part1, 19783L),
        Triple(resource(2023, 8), Day::part2, 9177460370549L),
    )
)
