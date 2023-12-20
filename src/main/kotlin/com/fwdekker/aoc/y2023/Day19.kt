package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.map
import com.fwdekker.aoc.std.readSections
import com.fwdekker.aoc.std.splitGEQ
import com.fwdekker.aoc.std.splitLEQ


fun main() {
    val lineSets = readSections("/y2023/Day19.txt")
    val workflows = lineSets[0].map { Workflow.fromString(it) }.associateBy { it.name }
    val parts = lineSets[1].map { partFromString(it) }

    // Part 1
    println("Part one: ${workflows.filter(parts).sumOf { it.value() }}")

    // Part 2
    println("Part two: ${workflows.filter(listOf("xmas".associateWith { 1..4000 })).sumOf { it.combos() }}")
}


private typealias Part = Map<Char, IntRange>

private fun Part.value(): Int = values.sumOf { it.sum() }

private fun Part.combos(): Long = values.map { it.count() }.fold(1L, Long::times)

private fun partFromString(string: String): Part =
    string
        .removeSurrounding("{", "}")
        .split(',')
        .map { it.split('=') }
        .associate { it[0][0] to it[1].toInt() }
        .mapValues { (_, it) -> it..it }


private data class Rule(val filter: (Part) -> Pair<Part, Part>, val target: String) {
    companion object {
        fun fromString(string: String): Rule {
            if (':' !in string)
                return Rule({ Pair(it, emptyMap()) }, string)

            val (filter, target) = string.split(':')
            val (category, threshold) = filter.split('<', '>').let { Pair(it[0][0], it[1].toInt()) }

            return Rule(
                { part ->
                    part.getValue(category)
                        .let { if (filter[1] == '<') it.splitLEQ(threshold) else it.splitGEQ(threshold) }
                        .map { part + mapOf(category to it) }
                },
                target
            )
        }
    }
}

private data class Workflow(val name: String, val rules: List<Rule>, val fallback: String) {
    fun process(part: Part): List<Pair<Part, String>> =
        rules
            .fold(Pair(emptyList<Pair<Part, String>>(), part)) { (forwarded, part), rule ->
                val (included, excluded) = rule.filter(part)
                Pair(forwarded + Pair(included, rule.target), excluded)
            }
            .let { (forwarded, rejected) -> forwarded + Pair(rejected, fallback) }
            .filter { it.first.combos() > 0L }


    companion object {
        fun fromString(string: String): Workflow =
            Workflow(
                name = string.takeWhile { it != '{' },
                rules = string
                    .dropWhile { it != '{' }
                    .removeSurrounding("{", "}")
                    .split(',')
                    .dropLast(1)
                    .map { Rule.fromString(it) },
                fallback = string.takeLastWhile { it != ',' }.dropLast(1),
            )
    }
}

private fun Map<String, Workflow>.filter(parts: List<Part>, start: String = "in"): List<Part> {
    val queue = ArrayDeque(parts.map { Pair(it, start) })

    return queue.fold(listOf()) { accepted, (part, workflow) ->
        getValue(workflow).process(part)
            .filter { it.second != "R" }
            .partition { it.second == "A" }
            .also { (_, forwarded) -> queue.addAll(forwarded) }
            .let { (newlyAccepted, _) -> accepted + newlyAccepted.map { it.first } }
    }
}
