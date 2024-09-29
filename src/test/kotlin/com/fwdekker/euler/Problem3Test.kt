package com.fwdekker.euler

import com.fwdekker.std.readLong


/**
 * Tests for [Problem3].
 */
class Problem3Test : ProblemTest<Long>(
    ::Problem3,
    ::readLong,
    listOf(
        ProblemTestCase(problem = 3, sample = 1),
        ProblemTestCase(problem = 3),
    )
)
