package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.Day.Companion.resource
import com.fwdekker.aoc.DayTest


/**
 * Tests for samples of [Day24].
 */
object Day24SamplesTest : DayTest(
    { resource -> Day24(resource, 7L..27L) },
    listOf(
        Triple(resource(2023, 24, sample = 1), Day::part1, 2L),
        Triple(resource(2023, 24, sample = 1), Day::part2, 47L),
    )
)

/**
 * Tests for [Day24].
 */
object Day24Test : DayTest(
    ::Day24,
    listOf(
        Triple(resource(2023, 24), Day::part1, 16727L),
        Triple(resource(2023, 24), Day::part2, 606772018765659L),
    )
)
