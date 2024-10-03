package com.fwdekker

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should


/**
 * Matcher asserting that the given collection contains exactly the same elements and in the same order as [expected].
 */
fun <T> containExactlyElementsOf(expected: Collection<T>) =
    Matcher<Collection<T>> { actual ->
        MatcherResult(
            actual.size == expected.size && actual.zip(expected).all { (a, b) -> a == b },
            { "List contained elements $actual but expected $expected." },
            { "Both lists contained the same elements $actual." },
        )
    }

/**
 * Asserts that the given collection contains exactly the same elements and in the same order as [expected].
 */
fun <T> Collection<T>.shouldContainExactlyElementsOf(expected: Collection<T>): Collection<T> {
    this should containExactlyElementsOf(expected)
    return this
}

/**
 * Matcher asserting that the given collection contains exactly the same elements and in the same order as [expected].
 */
fun <T> containExactlyInAnyOrderElementsOf(expected: Collection<T>) =
    Matcher<Collection<T>> { actual ->
        val actualGroups = actual.groupBy { it }
        val expectedGroups = expected.groupBy { it }

        MatcherResult(
            actual.size == expected.size && actualGroups.all { (a, b) -> expectedGroups[a] == b },
            { "List contained elements $actual but expected $expected." },
            { "Both lists contained the same elements $actual." },
        )
    }

/**
 * Asserts that the given collection contains exactly the same elements and in the same order as [expected].
 */
fun <T> Collection<T>.shouldContainExactlyInAnyOrderElementsOf(expected: Collection<T>): Collection<T> {
    this should containExactlyInAnyOrderElementsOf(expected)
    return this
}
