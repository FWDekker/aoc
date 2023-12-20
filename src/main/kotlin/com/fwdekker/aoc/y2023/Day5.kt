package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.readLines
import kotlin.math.max
import kotlin.math.min


fun main() {
    val lines = readLines("/y2023/Day5.txt")

    // Part 1
    val seeds = lines[0].split(": ")[1].parseLongs()
    lines.drop(1)
        .fold(seeds.associateWith { it }) { results, line ->
            if (!line[0].isDigit()) results.values.associateWith { it }
            else results.mapValues(line.asMapper())
        }
        .also { println("Part one: ${it.values.min()}") }

    // Part 2
    val seedRanges = seeds.chunked(2).map { it[0]..<it[0] + it[1] }
    lines.drop(1)
        .fold(seedRanges.associateWith { it }) { results, line ->
            if (!line[0].isDigit()) results.values.associateWith { it }
            else results.map(line.asRangeMapper()).reduce(Map<LongRange, LongRange>::plus)
        }
        .also { println("Part two: ${it.values.minOf(LongRange::first)}") }
}


private fun String.asMapper(): (Map.Entry<Long, Long>) -> Long {
    val (to, from, length) = this.parseLongs()

    return { (key, oldTarget) -> if (key - from in 0..<length) key - from + to else oldTarget }
}

private fun String.asRangeMapper(): (Map.Entry<LongRange, LongRange>) -> Map<LongRange, LongRange> {
    val (to, from, length) = this.parseLongs()

    return { (range, oldTarget) ->
        if (range.first != oldTarget.first) {
            mapOf(range to oldTarget)
        } else {
            val fromRange = (from..<from + length)
            val applicable = range.intersect(fromRange)
            val inapplicable = range.diff(fromRange)

            inapplicable.associateWith { it }.toMutableMap()
                .also { if (!applicable.isEmpty()) it[applicable] = applicable.shift(to - from) }
        }
    }
}


private fun String.parseLongs(): List<Long> = this.split(' ').map { it.toLong() }

private fun LongRange.intersect(that: LongRange): LongRange =
    (max(this.first, that.first)..min(this.last, that.last))
        .let { if (it.isEmpty()) LongRange.EMPTY else it }

private fun LongRange.diff(that: LongRange): List<LongRange> =
    listOf(this.first..min(this.last, that.first - 1), max(this.first, that.last + 1)..this.last)
        .filterNot { it.isEmpty() }

private fun LongRange.shift(by: Long): LongRange = this.first + by..this.last + by
