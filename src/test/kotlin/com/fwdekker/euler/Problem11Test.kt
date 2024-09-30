package com.fwdekker.euler


class Problem11Test : ProblemTest<Long>(
    ::Problem11,
    String::toLong,
    listOf(case())
)
