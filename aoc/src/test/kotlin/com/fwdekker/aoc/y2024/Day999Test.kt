package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import com.fwdekker.std.case
import io.kotest.core.annotation.Ignored
import io.kotest.core.annotation.Tags


@Ignored
@Tags("2024")
object XDay999Test : ChallengeTest(
    ::Day999,
    listOf(
        case(part = 1, sample = 1) to 0L,
        case(part = 2, sample = 1) to 0L,
        case(part = 1) to 0L,
        case(part = 2) to 0L,
    )
)
