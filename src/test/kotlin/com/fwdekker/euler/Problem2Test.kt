package com.fwdekker.euler


class Problem2Test : ProblemTest<Long>(
    ::Problem2,
    String::toLong,
    listOf(case())
)
