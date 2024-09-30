package com.fwdekker.euler


class Problem10Test : ProblemTest<Long>(
    ::Problem10,
    String::toLong,
    listOf(case())
)
