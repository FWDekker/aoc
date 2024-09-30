package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day4].
 */
object Day4Test : DayTest(
    ::Day4,
    listOf(
        Triple(resource(2023, 4, sample = 1), Day::part1, 13),
        Triple(resource(2023, 4, sample = 1), Day::part2, 30),
        Triple(resource(2023, 4), Day::part1, 20407),
        Triple(resource(2023, 4), Day::part2, 23806951),
    )
)
