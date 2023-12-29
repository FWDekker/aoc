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


    override fun part1(): Long = Graph.minCut(graph).map { it.size }.product()
//    override fun part1(): Long = Graph.minCut(graph2).map { it.size }.product()

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

        companion object {
            fun minCutPhase(G: Graph): Pair<Edge, Int> {
                val start = G.nodes.first()

                val foundSet = mutableSetOf(start)
                val foundSetEdges = G.edges.keys.filter { it.first == start }.toMutableSet()
                var cutWeight: Int? = null

                while (foundSet.size != G.nodes.size) {
                    val (maxNextVertex, maxWeight) =
                        G.nodes.minus(foundSet)
                            .map { next -> Pair(next, G.edges.keys.minus(foundSetEdges).filter { it.first == next }.sumOf { G.edges[it]!! }) }
                            .maxBy { it.second }

                    foundSet.add(maxNextVertex)
                    foundSetEdges.addAll(G.edges.keys.filter { it.first == maxNextVertex })
                    foundSetEdges.removeIf { (u, v) -> u in foundSet && v in foundSet }
                    cutWeight = maxWeight
                }

                return Pair(foundSet.toList().takeLast(2).asPair(), cutWeight!!)
            }

            fun minCut(start: Graph): Pair<Set<String>, Set<String>> {
                var g = start

                val currentPartition = mutableSetOf<String>()
                var currentBestPartition: MutableSet<String> = mutableSetOf()
                var currentBestCut: Pair<Edge, Int>? = null
                while (g.nodes.size > 1) {
                    val cutOfThePhase = minCutPhase(g)
                    if (currentBestCut == null || cutOfThePhase.second < currentBestCut.second) {
                        currentBestCut = cutOfThePhase
                        currentBestPartition = currentPartition.toMutableSet()
                        currentBestPartition.addAll(cutOfThePhase.first.second.split('|'))
                    }
                    currentPartition.addAll(cutOfThePhase.first.second.split('|'))

                    g = g.combine(
                        cutOfThePhase.first.first, cutOfThePhase.first.second,
                        "${cutOfThePhase.first.first}|${cutOfThePhase.first.second}"
                    )
                }

                val dinges = currentBestCut!!.first.second.split('|').toSet()
                return Pair(dinges, start.nodes.minus(dinges))
            }
        }
    }
}


fun main() = Day25().run()
