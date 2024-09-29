package com.fwdekker.euler

import com.fwdekker.std.readInt


/**
 * Tests for [Problem19].
 */
class Problem19Test : ProblemTest<Int>(
    { Problem19() },
    ::readInt,
    listOf(ProblemTestCase(problem = 19))
)
