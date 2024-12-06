package com.fwdekker.std


/**
 * Monadically invokes [transform] for [repeats] times.
 */
fun <T> T.foldSelf(repeats: Int, transform: (T) -> T): T =
    (1..repeats).fold(this) { temp, _ -> transform(temp) }

/**
 * Monadically invokes [transform] for [repeats] times and returns all intermediate results.
 */
fun <T> T.runningFoldSelf(repeats: Int, transform: (T) -> T): List<T> =
    (1..repeats).runningFold(this) { temp, _ -> transform(temp) }
