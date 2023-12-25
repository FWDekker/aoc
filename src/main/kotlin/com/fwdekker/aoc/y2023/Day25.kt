package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.asPair
import com.fwdekker.aoc.std.map
import com.fwdekker.aoc.std.product
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.swap


typealias Edge = Pair<String, String>


class Day25(resource: String = resource(2023, 25)) : Day(resource) {
    private val graph: Graph =
        readLines(resource)
            .map { line -> line.split(": ").map { it.split(' ') } }
            .flatMap { line -> line[1].map { Pair(line[0][0], it) } }
            .let { edges -> edges + edges.map { it.swap() } }
            .let { Graph(it.associateWith { 1 }) }


    val graph2 = Graph(
        mapOf(
            "1" to "2" to 2,
            "1" to "5" to 3,
            "2" to "3" to 3,
            "2" to "5" to 2,
            "2" to "6" to 2,
            "3" to "4" to 4,
            "3" to "7" to 2,
            "4" to "7" to 2,
            "4" to "8" to 2,
            "5" to "6" to 3,
            "6" to "7" to 1,
            "7" to "8" to 3,
        ).let { edges -> edges + edges.mapKeys { (uv, _) -> uv.swap() } }
    )


    override fun part1(): Long =
        Graph.minCut(graph).map { it.size }.product()
//    override fun part1(): Long = Graph.minCut(graph2).map { it.size }.product()
    // 20072 is incorrect

    override fun part2(): Long = 3L


    class Graph(val edges: Map<Edge, Int>) {
        val neighborsByNode =
            edges.keys
                .groupBy { it.first }
                .mapValues { (_, edges) -> edges.map { it.second }.toSet() }
                .withDefault { emptySet() }
        val nodes = neighborsByNode.keys


        init {
            require(edges.all { (uv, w) -> edges[uv.swap()] == w })
        }


        fun edgesBetween(nodes1: Set<String>, nodes2: Set<String>): Set<Edge> {
            require(nodes1.intersect(nodes2).isEmpty())

            return nodes1.flatMap { edgesFrom(it) }.filter { it.second in nodes2 }.toSet()
        }

        fun edgeWeight(edge: Edge): Int = edges.getValue(edge)

        fun edgesFrom(node: String): Set<Edge> = neighborsByNode.getValue(node).map { Pair(node, it) }.toSet()

        fun neighbors(of: String): Set<String> = neighborsByNode.getValue(of)

        fun remove(edges: Iterable<Edge>): Graph = Graph(this.edges.minus(edges.toSet()))

        fun combine(a: String, b: String, ab: String): Graph {
            val mapNode = { it: String -> if (it == a || it == b) ab else it }
            val mapEdge = { it: Pair<String, String> -> it.map(mapNode) }

            return edges
                .toList()
                .map { (edge, weight) -> mapEdge(edge) to weight }
                .groupBy { it.first }
                .mapValues { (_, edges) -> edges.sumOf { it.second } }
                .filter { (edge, _) -> edge.first != edge.second }
                .let { Graph(it.toMap()) }
        }

        fun components(): List<Set<String>> {
            val history = mutableSetOf<String>()
            val components = mutableListOf<MutableSet<String>>()

            val queue = ArrayDeque<String>()
            while (true) {
                val node: String
                if (queue.isEmpty()) {
                    val candidates = nodes.minus(history)
                    if (candidates.isEmpty())
                        return components

                    node = candidates.first()
                    components.add(mutableSetOf(node))
                } else {
                    node = queue.removeFirst()
                    components.last().add(node)
                }

                history.add(node)
                queue.addAll(neighborsByNode.getValue(node).minus(history))
            }
        }

        companion object {
            fun minCutPhase(G: Graph, a: String): Pair<Edge, Int> {
                require(a in G.nodes)

                val A = mutableListOf(a)
                while (G.nodes.minus(A.toSet()).isNotEmpty()) {
                    val frontier = A.flatMap { G.neighbors(it) }.filter { it !in A }
                    A.add(frontier.maxBy { f -> G.edgesFrom(f).sumOf { G.edgeWeight(it) } })
                }

                val cut = A.takeLast(2).asPair()
                val cutWeight = G.edgesFrom(A.last()).sumOf { G.edgeWeight(it) }
                return Pair(cut, cutWeight)
            }

            fun minCut(start: Graph): Pair<Set<String>, Set<String>> {
                var G = start
                var (minCut, minCutWeight) = Pair(Pair(emptySet<String>(), emptySet<String>()), Integer.MAX_VALUE)
                val a = G.nodes.random()
                while (G.nodes.size > 1) {
                    val (cut, cutWeight) = minCutPhase(G, a)
                    G = G.combine(cut.first, cut.second, "${cut.first}|${cut.second}")
                    if (cutWeight < minCutWeight)
                        minCut = cut.map { it.split('|').toSet() }
                }

                return minCut
            }
        }
    }
}


fun main() = Day25().run()
