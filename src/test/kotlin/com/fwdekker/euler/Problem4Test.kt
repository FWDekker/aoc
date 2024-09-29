package com.fwdekker.euler

import com.fwdekker.std.readLong


/**
 * Tests for [Problem4].
 */
class Problem4Test : ProblemTest<Long>(
    ::Problem4,
    ::readLong,
    listOf(
        ProblemTestCase(problem = 4, sample = 1),
        ProblemTestCase(problem = 4),
    )
)
