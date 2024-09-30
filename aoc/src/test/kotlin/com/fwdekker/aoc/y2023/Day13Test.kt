package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource


/**
 * Tests for [Day13].
 */
object Day13Test : DayTest(
    ::Day13,
    listOf(
        Triple(resource(2023, 13, sample = 1), Day::part1, 405),
        Triple(resource(2023, 13, sample = 1), Day::part2, 400),
        Triple(resource(2023, 13), Day::part1, 33728),
        Triple(resource(2023, 13), Day::part2, 28235),
    )
)
