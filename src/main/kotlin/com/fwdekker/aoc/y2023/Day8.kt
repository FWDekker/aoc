package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day8.txt").lines().filter(String::isNotBlank)

    val steps = lines.first().toList()
    val map = lines.drop(1)
        .map { it.split(" = ") }
        .associate { Pair(it[0], it[1].removeSurrounding("(", ")").split(", ")) }
        .mapValues { (_, it) -> mapOf('L' to it[0], 'R' to it[1]) }

    // Part 1
    println("Part one: ${map.distance(steps, from = "AAA", to = { it == "ZZZ" })}")

    // Part 2
    map.keys.filter { it.endsWith('A') }
        .map { ghost -> map.distance(steps, from = ghost, to = { it.endsWith('Z') }) }
        .also { println("Part two: ${it.reduce(::lcm)}") }
}


private fun <T> Collection<T>.cyclic() = sequence { while (true) yieldAll(this@cyclic) }

private fun <T, R> Sequence<T>.foldUntil(initial: R, condition: (R, T) -> Boolean, operation: (R, T) -> R): R {
    var current = initial

    val iterator = iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (condition(current, next))
            break

        current = operation(current, next)
    }

    return current
}


private fun Map<String, Map<Char, String>>.distance(steps: List<Char>, from: String, to: (String) -> Boolean): Long =
    steps.cyclic()
        .foldUntil(Pair(0L, from), { it, _ -> to(it.second) }) { (steps, location), instruction ->
            Pair(steps + 1L, this[location]!![instruction]!!)
        }
        .first

private tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

private fun lcm(a: Long, b: Long): Long = a * (b / gcd(a, b))
