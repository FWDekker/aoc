package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource


/**
 * Tests for [Day25].
 */
object Day25Test : DayTest(
    ::Day25,
    listOf(
        Triple(resource(2023, 25, sample = 1), Day::part1, 54),
        Triple(resource(2023, 25), Day::part1, 606062),
    )
)
