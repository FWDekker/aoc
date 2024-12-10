package com.fwdekker.std.maths

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe


/**
 * Unit tests for `graphs.kt`.
 */
object GraphsTest : DescribeSpec({
    val calcLinearGraph = { dijkstra(0, { if (it < 9) listOf(it + 1) else emptyList() }, { _, _ -> 1L }) }
    val calcSimpleGraph = {
        val adjacency = mapOf(
            0 to mapOf(1 to 2L, 2 to 4L),
            1 to mapOf(2 to 1L),
            2 to mapOf(0 to 1L, 3 to 4L),
            3 to mapOf(4 to 5L),
            4 to mapOf()
        )

        dijkstra(0, { adjacency.getValue(it).keys.toList() }, { u, v -> adjacency.getValue(u).getValue(v) })
    }


    describe("dijkstra") {
        describe("distances") {
            it("returns the distance to the starting node as zero") {
                val dists = calcLinearGraph()

                dists.distanceTo(0) shouldBe 0
            }

            it("returns the distances in a linear graph") {
                val dists = calcLinearGraph()

                dists.distanceTo(3) shouldBe 3
                dists.distanceTo(9) shouldBe 9
            }

            it("returns the distances to all nodes in a linear graph") {
                val dists = calcLinearGraph()

                dists.allDistances().keys shouldContainExactly 0..9
                dists.allDistances() shouldContainExactly (0..9).associateWith { it.toLong() }
            }

            it("returns the shortest distance to the specified node in a simple graph") {
                val dists = calcSimpleGraph()

                dists.distanceTo(2) shouldBe 3L
            }

            it("returns all shortest distances for a simple graph") {
                val dists = calcSimpleGraph()

                dists.allDistances() shouldContainExactly mapOf(0 to 0, 1 to 2L, 2 to 3L, 3 to 7L, 4 to 12L)
            }
        }

        describe("paths") {
            it("does not return a path for the starting node itself in a linear graph") {
                val dists = calcLinearGraph()

                dists.shortestPathTo(0) shouldBe null
            }

            it("returns the only path to a node in a linear graph") {
                val dists = calcLinearGraph()

                dists.shortestPathTo(4) shouldBe listOf(0, 1, 2, 3, 4)
            }

            it("returns the only path to each node (except the starting node) in a linear graph") {
                val dists = calcLinearGraph()

                dists.allShortestPaths() shouldContainExactly (1..9).associateWith { (0..it).toList() }
            }

            it("returns the shortest path to the specified node in a simple graph") {
                val dists = calcSimpleGraph()

                dists.shortestPathTo(2) shouldBe listOf(0, 1, 2)
            }

            it("returns all shortest paths for a simple graph") {
                val dists = calcSimpleGraph()

                dists.allShortestPaths() shouldContainExactly
                    mapOf(1 to listOf(0, 1), 2 to listOf(0, 1, 2), 3 to listOf(0, 1, 2, 3), 4 to listOf(0, 1, 2, 3, 4))
            }
        }
    }
})
