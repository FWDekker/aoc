package com.fwdekker.euler

import com.fwdekker.std.readLong


/**
 * Tests for [Problem5].
 */
class Problem5Test : ProblemTest<Long>(
    ::Problem5,
    ::readLong,
    listOf(
        ProblemTestCase(problem = 5, sample = 1),
        ProblemTestCase(problem = 5),
    )
)
