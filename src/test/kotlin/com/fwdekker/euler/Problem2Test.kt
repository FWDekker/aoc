package com.fwdekker.euler

import com.fwdekker.std.readLong


/**
 * Tests for [Problem2].
 */
class Problem2Test : ProblemTest<Long>(
    ::Problem2,
    ::readLong,
    listOf(ProblemTestCase(problem = 2))
)
