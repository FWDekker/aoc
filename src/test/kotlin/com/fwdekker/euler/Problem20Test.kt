package com.fwdekker.euler

import com.fwdekker.std.readInt


/**
 * Tests for [Problem20].
 */
class Problem20Test : ProblemTest<Int>(
    ::Problem20,
    ::readInt,
    listOf(
        ProblemTestCase(problem = 20, sample = 1),
        ProblemTestCase(problem = 20),
    )
)
