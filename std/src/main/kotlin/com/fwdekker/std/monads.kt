package com.fwdekker.std


/**
 * Monadically invokes [transform] for [repeats] times.
 */
fun <T> T.foldSelf(repeats: Int, transform: (T) -> T): T =
    (0..<repeats).fold(this) { acc, _ -> transform(acc) }

/**
 * Monadically invokes [transform] for [repeats] times, additionally given the iteration number.
 */
fun <T> T.foldSelfIndexed(repeats: Int, transform: (Int, T) -> T): T =
    (0..<repeats).fold(this) { acc, idx -> transform(idx, acc) }

/**
 * Monadically invokes [transform] for [repeats] times and returns all intermediate results.
 */
fun <T> T.runningFoldSelf(repeats: Int, transform: (T) -> T): Sequence<T> =
    (1..repeats).asSequence().runningFold(this) { temp, _ -> transform(temp) }
