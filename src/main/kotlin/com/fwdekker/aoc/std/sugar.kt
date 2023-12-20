package com.fwdekker.aoc.std


/**
 * Monadically invokes [transform] for [repeats] times.
 */
fun <T> T.foldSelf(repeats: Int, transform: (T) -> T): T =
    (1..repeats).fold(this) { temp, _ -> transform(temp) }
