package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day12.txt").lines().filter(String::isNotBlank)

    val rows = lines.map { it.split(' ') }.map { Pair(it[0], it[1].split(',').map(String::toInt)) }

    // Part 1
    println("Part one: ${rows.sumOf { combosOf(it.first, it.second) }}")

    // Part 2
    println("Part two: ${rows.sumOf { combosOf(it.first.repeat(5, "?"), it.second.repeat(5)) }}")
}


private fun String.repeat(amount: Int, delimiter: String) = List(amount) { this }.joinToString(separator = delimiter)

private fun <T> List<T>.repeat(amount: Int) = List(amount) { this }.flatten()


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

private fun combosOf(record: String, groups: List<Int>): Long =
    groups
        .fold(mapOf(0 to 1L)) { accumulator, group ->
            accumulator
                .map { (offset, combos) -> combosOf(record, offset, group).associateWith { combos } }
                .fold(emptyMap(), Map<Int, Long>::plusAtKeys)
                .toMap()
        }
        .filterKeys { '#' !in record.drop(it) }
        .values.sum()
