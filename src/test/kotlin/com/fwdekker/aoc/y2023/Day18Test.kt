package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Day.Companion.resource


/**
 * Tests for [Day18].
 */
object Day18Test : DayTest(
    ::Day18,
    listOf(
        Triple(resource(2023, 18, sample = 1), Day::part1, 62L),
        Triple(resource(2023, 18, sample = 1), Day::part2, 952408144115L),
        Triple(resource(2023, 18), Day::part1, 40714L),
        Triple(resource(2023, 18), Day::part2, 129849166997110L),
    )
)
