package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day25Test : ChallengeTest(
    ::Day25,
    listOf(
        case(part = 1, sample = 1) to 3,
//        case(part = 2, sample = 1) to TODO(),
        case(part = 1) to 3344,
//        case(part = 2) to TODO(),
    )
)
