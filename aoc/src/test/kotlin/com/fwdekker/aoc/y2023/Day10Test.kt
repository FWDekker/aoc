package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day10].
 */
object Day10Test : DayTest(
    ::Day10,
    listOf(
        Triple(resource(2023, 10, sample = 1), Day::part1, 4),
        Triple(resource(2023, 10, sample = 2), Day::part1, 8),
        Triple(resource(2023, 10, sample = 3), Day::part2, 4),
        Triple(resource(2023, 10, sample = 4), Day::part2, 4),
        Triple(resource(2023, 10, sample = 5), Day::part2, 8),
        Triple(resource(2023, 10, sample = 6), Day::part2, 10),
        Triple(resource(2023, 10), Day::part1, 6815),
        Triple(resource(2023, 10), Day::part2, 269),
    )
)
