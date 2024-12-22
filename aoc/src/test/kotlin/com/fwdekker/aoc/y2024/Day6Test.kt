package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day6Test : ChallengeTest(
    ::Day6,
    listOf(
        case(part = 1, sample = 1) to 41,
        case(part = 2, sample = 1) to 6,
        case(part = 1) to 5080,
        case(part = 2) to 1919,
    )
)
