package com.fwdekker.euler

import com.fwdekker.std.readInt


/**
 * Tests for [Problem1].
 */
class Problem1Test : ProblemTest<Int>(
    ::Problem1,
    ::readInt,
    listOf(
        ProblemTestCase(problem = 1, sample = 1),
        ProblemTestCase(problem = 1),
    )
)
