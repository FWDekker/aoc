package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day3].
 */
object Day3Test : DayTest(
    ::Day3,
    listOf(
        Triple(resource(2023, 3, sample = 1), Day::part1, 4361),
        Triple(resource(2023, 3, sample = 1), Day::part2, 467835L),
        Triple(resource(2023, 3), Day::part1, 535078),
        Triple(resource(2023, 3), Day::part2, 75312571L),
    )
)
