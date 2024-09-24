package com.fwdekker

import io.kotest.matchers.be
import io.kotest.matchers.should


/**
 * Asserts that the given collection contains exactly the same elements and in the same order as [expected].
 */
fun <T> Collection<T>.shouldContainExactlyElementsOf(expected: Collection<T>): Collection<T> {
    this.toList() should be(expected.toList())
    return this
}

/**
 * Asserts that the given collection contains exactly the same elements and in the same order as [expected].
 */
fun <T> Sequence<T>.shouldContainExactlyElementsOf(expected: Collection<T>): Sequence<T> {
    this.toList() should be(expected.toList())
    return this
}
