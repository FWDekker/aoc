package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day23].
 */
object Day23Test : DayTest(
    ::Day23,
    listOf(
        Triple(resource(2023, 23, sample = 1), Day::part1, 94),
        Triple(resource(2023, 23, sample = 1), Day::part2, 154),
        Triple(resource(2023, 23), Day::part1, 2318),
        Triple(resource(2023, 23), Day::part2, 6426),
    )
)
