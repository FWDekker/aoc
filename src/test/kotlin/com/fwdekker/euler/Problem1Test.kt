package com.fwdekker.euler


/**
 * Tests for [Problem1].
 */
class Problem1Test : ProblemTest<Int>(
    ::Problem1,
    listOf(
        Pair(Problem.resource(1, sample = 1), 0),
        Pair(Problem.resource(1), 0),
    )
)
