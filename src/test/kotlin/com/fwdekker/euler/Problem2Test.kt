package com.fwdekker.euler


/**
 * Tests for [Problem2].
 */
class Problem2Test : ProblemTest<Long>(
    ::Problem2,
    listOf(Pair(Problem.resource(2), 0L))
)
