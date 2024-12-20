package com.fwdekker.aoc

import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.get
import com.fwdekker.std.maths.Graph


/**
 * A graph representation of a [Chart] in which `'#'` is a wall, all non-wall cells are nodes, and all adjacent nodes
 * are connected by an edge of weight one.
 */
open class ChartGraph(private val chart: Chart) : Graph<Coords>() {
    override val nodes: Collection<Coords>
        by lazy { chart.allCoords.filter { chart[it] != '#' }.toList() }

    override fun getWeight(start: Coords, end: Coords): Long =
        1L

    override fun getNeighbours(node: Coords): Collection<Coords> =
        node.cardinals.filter { it in chart && chart[it] != '#' }
}

