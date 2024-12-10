@file:Suppress("unused")
package com.fwdekker.std.maths

import com.fwdekker.std.collections.sum
import java.util.ArrayDeque
import java.util.Deque
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


/**
 * A graph.
 */
@ExperimentalStdlibApi
abstract class Graph<N> {
    /**
     * The collection of all nodes in the graph.
     */
    abstract val nodes: Collection<N>

    /**
     * Returns the collection of all neighbours of the given [node].
     */
    abstract fun getNeighbours(node: N): Collection<N>

    /**
     * Returns the (strictly positive) weight of the edge from [start] to [end].
     */
    abstract fun getWeight(start: N, end: N): Long


    private val cachedDistances = mutableMapOf<Pair<N, N>, Long?>()
    private val cachedShortestPaths = mutableMapOf<Pair<N, N>, List<N>?>()


    /**
     * Traverses all nodes in breadth-first order.
     */
    fun breadthFirst(start: N): Sequence<N> = dequeTraversal(start, Deque<N>::removeFirst)

    /**
     * Traverses all nodes in depth-first order.
     */
    fun depthFirst(start: N): Sequence<N> = dequeTraversal(start, Deque<N>::removeLast)

    private fun dequeTraversal(start: N, next: Deque<N>.() -> N): Sequence<N> =
        sequence {
            val seen = mutableSetOf<N>()
            val queue = ArrayDeque<N>()

            queue += start
            while (queue.isNotEmpty()) {
                val node = next(queue)
                yield(node)

                seen += node
                queue += getNeighbours(node).filter { it !in seen }
            }
        }


    /**
     * Returns the length of the shortest path from [start] to [end], or `null` if there is no such path.
     */
    fun distance(start: N, end: N): Long? =
        cachedDistances.getOrPut(start to end) { dijkstra(start, end).first[end] }

    /**
     * Returns the [distance] from [start] to each of the [nodes].
     */
    fun distances(start: N): Map<N, Long?> =
        if (nodes.all { (start to it) in cachedDistances }) {
            nodes.associateWith { cachedDistances[start to it] }
        } else {
            val (distances, _) = dijkstra(start)
            nodes.associateWith { distances[it] }
                .also { dists -> cachedDistances += dists.mapKeys { start to it.key } }
        }

    /**
     * Returns the [distance] between each pair of [nodes].
     */
    fun distances(): Map<Pair<N, N>, Long?> =
        nodes.map { start -> distances(start).map { (start to it.key) to it.value }.toMap() }.sum()

    /**
     * Returns the shortest path from [start] to [end], or `null` if there is no such path.
     */
    fun shortestPath(start: N, end: N): List<N>? =
        cachedShortestPaths.getOrPut(start to end) {
            val (distances, paths) = dijkstra(start, end)
            cachedDistances[start to end] = distances[end]
            paths[end]
        }

    /**
     * Returns the [shortestPath] from [start] to each of the [nodes].
     */
    fun shortestPaths(start: N): Map<N, List<N>?> =
        if (nodes.all { (start to it) in cachedShortestPaths }) {
            nodes.associateWith { cachedShortestPaths[start to it] }
        } else {
            val (distances, paths) = dijkstra(start)
            nodes.associateWith { distances[it] }
                .also { cachedDistances += it.mapKeys { (key, _) -> start to key } }
            nodes.associateWith { paths[it] }
                .also { cachedShortestPaths += it.mapKeys { (key, _) -> start to key } }
        }

    /**
     * Returns the [shortestPath] between each pair of [nodes].
     */
    fun shortestPaths(): Map<Pair<N, N>, List<N>?> =
        nodes.map { start -> shortestPaths(start).map { (start to it.key) to it.value }.toMap() }.sum()

    /**
     * Runs Dijkstra's algorithm on the graph, starting at [start].
     *
     * The returned value has two parts: a map from each node to its distance from [start], and a map from each node to
     * the found shortest path.
     *
     * If [end] is given, the algorithm terminates once the shortest path to [end] is found, and the returned maps
     * contain only the desired key.
     */
    private fun dijkstra(start: N, end: N? = null): Pair<Map<N, Long>, Map<N, List<N>>> {
        val distances = mutableMapOf<N, Long>()
        val previous = mutableMapOf<N, N?>().withDefault { null }
        val priorityQueue = PriorityQueue<N>(compareBy { distances.getValue(it) })

        distances[start] = 0L
        priorityQueue.add(start)
        while (priorityQueue.isNotEmpty()) {
            val node = priorityQueue.poll()
            if (end != null && node == end)
                return Pair(mapOf(end to distances.getValue(end)), mapOf(end to spanningPath(previous, start, end)!!))

            val distance = distances.getValue(node)
            getNeighbours(node).forEach { neighbour ->
                val newDistance = distance + getWeight(node, neighbour)
                if (newDistance < (distances[neighbour] ?: Long.MAX_VALUE)) {
                    distances[neighbour] = newDistance
                    previous[neighbour] = node
                    priorityQueue.add(neighbour)
                }
            }
        }

        return distances to distances.keys.associateWith { spanningPath(previous, start, it)!! }
    }

    /**
     * Given a spanning tree rooted at [start] described by [edges] mapping nodes to their predecessors, returns the
     * path from [start] to [end], or `null` if [end] is not reachable from [start].
     *
     * You do not have to set [append].
     *
     * @see dijkstra
     */
    tailrec fun spanningPath(edges: Map<N, N?>, start: N, end: N, append: List<N> = emptyList()): List<N>? {
        val node = edges[end]
        return when {
            append.isEmpty() && node == null -> null
            node == null -> listOf(end) + append
            else -> spanningPath(edges, start, node, listOf(end) + append)
        }
    }
}
