package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.longs
import com.fwdekker.aoc.std.overlap
import com.fwdekker.aoc.std.readSections
import com.fwdekker.aoc.std.shift
import com.fwdekker.aoc.std.sum
import com.fwdekker.aoc.std.without


class Day5(resource: String = resource(2023, 5)) : Day(resource) {
    private val sections = readSections(resource)

    private val seeds: Collection<Long> = sections[0][0].substringAfter(": ").longs(' ')
    private val seedRanges: Collection<LongRange> = seeds.chunked(2).map { it[0]..<it[0] + it[1] }


    override fun part1(): Long =
        sections.drop(1)
            .fold(seeds) { categories, lines ->
                lines.drop(1)
                    .map { it.asMapper() }
                    .fold(categories.associateWith { it }) { acc, mapper -> acc.mapValues(mapper) }
                    .values
            }
            .min()

    override fun part2(): Long =
        sections.drop(1)
            .fold(seedRanges) { categories, lines ->
                lines.drop(1)
                    .map { it.asRangeMapper() }
                    .fold(categories.associateWith { it }) { acc, mapper -> acc.map(mapper).sum() }
                    .values
            }
            .minOf { it.first }


    private fun String.asMapper(): (Map.Entry<Long, Long>) -> Long {
        val (dstStart, srcStart, length) = longs(' ')
        return { (src, oldDst) -> if (src - srcStart in 0..<length) src - srcStart + dstStart else oldDst }
    }

    private fun String.asRangeMapper(): (Map.Entry<LongRange, LongRange>) -> Map<LongRange, LongRange> {
        val (dstStart, srcStart, length) = longs(' ')

        return { (src, oldDst) ->
            if (src.first != oldDst.first) {
                mapOf(src to oldDst)
            } else {
                val srcRange = (srcStart..<srcStart + length)
                val applicable = src.overlap(srcRange)
                val inapplicable = src.without(srcRange)

                val newDst = inapplicable.associateWith { it }
                if (applicable.isEmpty()) newDst
                else newDst + Pair(applicable, applicable.shift(dstStart - srcStart))
            }
        }
    }
}


fun main() = Day5().run()
