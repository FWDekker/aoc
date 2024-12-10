@file:Suppress("unused")
package com.fwdekker.std.maths

import java.util.PriorityQueue


/**
 * Calculates shortest-path related metrics starting from [start] in a graph specified by [getNeighbours] and
 * [getWeight].
 *
 * The function [getWeight] is assumed to always return strictly positive values.
 */
fun <T> dijkstra(start: T, getNeighbours: (T) -> Collection<T>, getWeight: (T, T) -> Long): DijkstraResult<T> {
    val distances = mutableMapOf<T, Long>()
    val previous = mutableMapOf<T, T?>().withDefault { null }
    val priorityQueue = PriorityQueue<Pair<T, Long>>(compareBy { it.second })

    distances[start] = 0L
    priorityQueue.add(start to 0L)
    while (priorityQueue.isNotEmpty()) {
        val (node, currentDistance) = priorityQueue.poll()

        getNeighbours(node).forEach { neighbour ->
            val newDistance = currentDistance + getWeight(node, neighbour)

            if (newDistance < (distances[neighbour] ?: Long.MAX_VALUE)) {
                distances[neighbour] = newDistance
                previous[neighbour] = node
                priorityQueue.add(neighbour to newDistance)
            }
        }
    }

    return DijkstraResult(start, getNeighbours, getWeight, distances, previous)
}


/**
 * The shortest-path results for node [start] in a graph specified by [getNeighbours] and [getWeight], given the found
 * [distances] and [previous] nodes.
 */
class DijkstraResult<T>(
    val start: T,
    val getNeighbours: (T) -> Collection<T>,
    val getWeight: (T, T) -> Long,
    private val distances: Map<T, Long>,
    private val previous: Map<T, T?>
) {
    /**
     * Cached map of already calculated shortest paths.
     */
    private val cachedShortestPaths = mutableMapOf<T, List<T>?>().withDefault { calcShortestPathTo(it) }


    /**
     * Returns the length of a shortest path from [start] to [end].
     */
    fun distanceTo(end: T): Long = distances.getValue(end)

    /**
     * Returns for each other node reachable from [start] the length of a shortest path from [start] to that node.
     */
    fun allDistances(): Map<T, Long> = distances.toMap()

    /**
     * Returns a shortest path from [start] to [end] as a sequence of nodes.
     *
     * Tip: Follow up this method with [zipWithNext] to get the path as a sequence of edges.
     */
    fun shortestPathTo(end: T): List<T>? = cachedShortestPaths.getValue(end)

    /**
     * Returns for each other node reachable from [start] a shortest path from [start] to that node as a sequence of
     * nodes.
     */
    fun allShortestPaths(): Map<T, List<T>> =
        distances.keys
            .map { it to cachedShortestPaths.getValue(it) }
            .filter { it.second != null }
            .associate { it.first to it.second!! }


    /**
     * Calculates the shortest path from [start] to [end], and appends [append] to the output; or returns `null` if
     * [end] is not reachable from [start].
     *
     * You should not give a value for the [append] list unless you know what you're doing.
     */
    private tailrec fun calcShortestPathTo(end: T, append: List<T> = emptyList()): List<T>? {
        val prev = previous[end]

        return when {
            append.isEmpty() && prev == null -> null
            prev == null -> listOf(end) + append
            else -> calcShortestPathTo(prev, listOf(end) + append)
        }
    }
}
