package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.collections.neverNull
import com.fwdekker.std.collections.swap


// See https://adventofcode.com/2024/day/23
class Day23(sample: Int? = null) : Day(year = 2024, day = 23, sample = sample) {
    private val edges = input.lines()
        .flatMap { edge -> edge.split('-').asPair().let { listOf(it, it.swap()) } }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.toSet() }
        .neverNull()


    override fun part1() =
        edges.keys
            .filter { u -> u[0] == 't' }
            .flatMap { u ->
                edges[u]
                    .flatMap { v ->
                        edges[v]
                            .filter { w -> u in edges[w] }
                            .map { w -> listOf(u, v, w).sorted().asTriple() }
                    }
            }
            .distinct()
            .size

    override fun part2() = find(emptySet()).sorted().joinToString(",")


    private val findCache = mutableMapOf<Set<String>, Set<String>>()

    private fun find(nodes: Set<String>): Set<String> =
        findCache.getOrPut(nodes) {
            val mutuals = nodes.fold(edges.keys) { acc, node -> acc.intersect(edges[node] + node) }
            if (mutuals.isEmpty()) return nodes
            if (mutuals.intersect(nodes).size < nodes.size) return emptySet()
            if (mutuals.size == nodes.size) return nodes

            mutuals
                .map { mutual -> nodes + mutual }
                .distinct()
                .let { if (nodes in it) it.minusElement(nodes) else it }
                .map { find(it) }
                .maxByOrNull { it.size }
                ?: nodes
        }
}


fun main() = Day23().run()
