package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.readLines
import org.jgrapht.Graph
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph


class Day25(resource: String = resource(2023, 25)) : Day(resource) {
    private val graph: Graph<String, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)


    init {
        readLines(resource)
            .map { it.split(": ") }
            .forEach { (source, destinations) ->
                graph.addVertex(source)
                destinations.split(' ').forEach { destination ->
                    graph.addVertex(destination)
                    graph.addEdge(source, destination)
                }
            }
    }


    override fun part1(): Int = StoerWagnerMinimumCut(graph).minCut().size.let { (graph.vertexSet().size - it) * it }

    override fun part2(): Int = 0
}


fun main() = Day25().run()
