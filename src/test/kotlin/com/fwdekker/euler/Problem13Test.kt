package com.fwdekker.euler


class Problem13Test : ProblemTest<Long>(
    ::Problem13,
    String::toLong,
    listOf(case())
)
