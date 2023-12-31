package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Day.Companion.resource


/**
 * Tests for [Day22].
 */
object Day22Test : DayTest(
    ::Day22,
    listOf(
        Triple(resource(2023, 22, sample = 1), Day::part1, 5),
        Triple(resource(2023, 22, sample = 1), Day::part2, 7),
        Triple(resource(2023, 22), Day::part1, 507),
        Triple(resource(2023, 22), Day::part2, 51733),
    )
)
