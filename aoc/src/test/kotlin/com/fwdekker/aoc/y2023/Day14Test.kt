package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import com.fwdekker.std.case
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day14Test : ChallengeTest(
    ::Day14,
    listOf(
        case(part = 1, sample = 1) to 136,
        case(part = 2, sample = 1) to 64,
        case(part = 1) to 103614,
        case(part = 2) to 83790,
    )
)
