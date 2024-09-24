package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.DayTest
import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource


/**
 * Tests for [Day21].
 */
object Day21Test : DayTest(
    ::Day21,
    listOf(
        Triple(resource(2023, 21), Day::part1, 3733),
        Triple(resource(2023, 21), Day::part2, 617729401414635L),
    )
)
