package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.asPair
import com.fwdekker.std.toInts
import com.fwdekker.std.mapSeconds
import com.fwdekker.std.readLines
import com.fwdekker.std.repeat


class Day12(resource: String = resource(2023, 12)) : Day() {
    private val lines = readLines(resource)
    private val rows = lines.map { it.split(' ').asPair() }.mapSeconds { it.toInts(',') }


    override fun part1(): Long = rows.sumOf { combosOf(it.first, it.second) }

    override fun part2(): Long = rows.sumOf { combosOf(it.first.repeat(5, "?"), it.second.repeat(5)) }


    private fun <K> Map<K, Long>.plusAtKeys(that: Map<K, Long>): Map<K, Long> =
        toMutableMap().also { that.forEach { (k, v) -> it[k] = (it[k] ?: 0) + v } }

    private fun String.fits(offset: Int, size: Int): Boolean {
        require(size > 0) { "Size must be strictly positive." }

        return (offset + size in 0..this.length) &&
            getOrNull(offset - 1) != '#' &&
            getOrNull(offset + size) != '#' &&
            slice(offset..<offset + size).all { it in "#?" }
    }

    private fun combosOf(record: String, offset: Int, group: Int): List<Int> {
        val end = record.indexOf('#', offset).let { if (it >= 0) it else record.length }
        return (offset..end).filter { record.fits(it, group) }.map { it + group + 1 }
    }

    private fun combosOf(record: String, groups: Collection<Int>): Long =
        groups
            .fold(mapOf(0 to 1L)) { accumulator, group ->
                accumulator
                    .map { (offset, combos) -> combosOf(record, offset, group).associateWith { combos } }
                    .fold(emptyMap<Int, Long>()) { acc, it -> acc.plusAtKeys(it) }
                    .toMap()
            }
            .filterKeys { '#' !in record.drop(it) }
            .values.sum()
}


fun main() = Day12().run()
