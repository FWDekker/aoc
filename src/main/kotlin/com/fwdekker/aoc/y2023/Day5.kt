package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.longs
import com.fwdekker.aoc.std.readLines
import kotlin.math.max
import kotlin.math.min


class Day5(resource: String = resource(2023, 5)) : Day(resource) {
    // TODO: Parse nicer, using [readSections]
    private val lines = readLines(resource)
    private val seeds = lines[0].substringAfter(": ").longs(' ')
    private val seedRanges = seeds.chunked(2).map { it[0]..<it[0] + it[1] }


    override fun part1(): Long =
        lines
            .drop(1)
            .fold(seeds.associateWith { it }) { results, line ->
                if (!line[0].isDigit()) results.values.associateWith { it }
                else results.mapValues(line.asMapper())
            }
            .minOf { it.value }

    override fun part2(): Long =
        lines
            .drop(1)
            .fold(seedRanges.associateWith { it }) { results, line ->
                if (!line[0].isDigit()) results.values.associateWith { it }
                else results.map(line.asRangeMapper()).reduce(Map<LongRange, LongRange>::plus)
            }
            .minOf { it.value.first }


    private fun String.asMapper(): (Map.Entry<Long, Long>) -> Long {
        val (to, from, length) = longs(' ')
        return { (key, oldTarget) -> if (key - from in 0..<length) key - from + to else oldTarget }
    }

    private fun String.asRangeMapper(): (Map.Entry<LongRange, LongRange>) -> Map<LongRange, LongRange> {
        val (to, from, length) = longs(' ')

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


    // TODO: Move all of the following to the maths library
    private fun LongRange.intersect(that: LongRange): LongRange =
        (max(this.first, that.first)..min(this.last, that.last))
            .let { if (it.isEmpty()) LongRange.EMPTY else it }

    private fun LongRange.diff(that: LongRange): List<LongRange> =
        listOf(this.first..min(this.last, that.first - 1), max(this.first, that.last + 1)..this.last)
            .filterNot { it.isEmpty() }

    private fun LongRange.shift(by: Long): LongRange = this.first + by..this.last + by
}


fun main() = Day5().run()
