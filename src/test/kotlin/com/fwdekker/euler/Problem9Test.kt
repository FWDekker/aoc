package com.fwdekker.euler


class Problem9Test : ProblemTest<Long>(
    ::Problem9,
    String::toLong,
    listOf(case())
)
