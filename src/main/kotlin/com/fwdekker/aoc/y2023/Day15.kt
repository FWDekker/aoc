package com.fwdekker.aoc.y2023


fun main() {
    val ops = readResource("/y2023/Day15.txt").trim().split(',')

    // Part 1
    println("Part one: ${ops.sumOf { it.hash() }}")

    // Part 2
    // Kotlin's maps preserve insertion order by default
    println("Part two: ${ops.fold(List<Box>(256) { emptyMap() }) { boxes, op -> boxes.run(op) }.power()}")
}


private typealias Box = Map<String, Int>

private fun String.hash(): Int = fold(0) { hash, char -> ((hash + char.code) * 17) % 256 }

private fun List<Box>.run(op: String): List<Box> {
    val (label, focalLength) = op.split('-', '=').let { Pair(it[0], it[1].toIntOrNull() ?: 0) }
    val hash = label.hash()

    return replace(hash, this[hash].let { if ('-' in op) it.withoutKey(label) else it.replace(label, focalLength) })
}

private fun List<Box>.power(): Int =
    indices.sumOf { box -> (box + 1) * this[box].values.indexed().sumOf { (slot, focal) -> (slot + 1) * focal } }


fun <T> List<T>.replace(index: Int, element: T): List<T> =
    toMutableList().also { it[index] = element }

fun <K, V> Map<K, V>.replace(key: K, value: V): Map<K, V> =
    toMutableMap().also { it[key] = value }

fun <K, V> Map<K, V>.withoutKey(key: K): Map<K, V> =
    filterKeys { it != key }

fun <T> Collection<T>.indexed(): List<Pair<Int, T>> =
    toList().mapIndexed { idx, it -> Pair(idx, it) }
