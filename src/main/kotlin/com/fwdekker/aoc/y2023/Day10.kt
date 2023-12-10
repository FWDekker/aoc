package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day10.txt").lines().filter(String::isNotBlank)

    val maze = Maze(lines.map { line -> line.map { it } })
    val start = maze.pipes.indexOfFirst { 'S' in it }.let { Coords(it, maze.pipes[it].indexOf('S')) }
    val cycle = listOf('N', 'E', 'S', 'W')
        .map { State(maze, start, it).also(State::scurry) }
        .first { it.isCycle() }

    // Part 1
    println("Part one: ${cycle.distance / 2}")

    // Part 2
    println("Part one: ${cycle.getEnclosedSpace()}")
}


private data class Coords(val x: Int, val y: Int) {
    fun north() = Coords(x - 1, y)
    fun east() = Coords(x, y + 1)
    fun south() = Coords(x + 1, y)
    fun west() = Coords(x, y - 1)
    fun neighbors() = listOf(north(), east(), south(), west())
}

private data class Maze(val pipes: List<List<Char>>) {
    init {
        require(pipes.isNotEmpty())
        require(pipes.all { it.size == pipes.first().size })
    }


    fun contains(coords: Coords): Boolean = coords.x in pipes.indices && coords.y in pipes[0].indices

    fun get(coords: Coords): Char = pipes[coords.x][coords.y]
}

private data class State(
    val maze: Maze,
    val start: Coords,
    var looking: Char,
    var location: Coords? = start,
    var distance: Int = 0,
    private var leftTurns: Int = 0,
    private var rightTurns: Int = 0,
    private val path: MutableSet<Coords> = mutableSetOf(start),
    private val leftPipes: MutableSet<Coords> = mutableSetOf(),
    private val rightPipes: MutableSet<Coords> = mutableSetOf(),
) {
    fun isCycle(): Boolean = location == start && distance > 0

    fun isDone(): Boolean = isCycle() || location == null


    fun scurry() {
        while (!isDone()) step()
    }

    private fun step() {
        require(!isDone()) { "Cannot step after being done!" }

        val current = location ?: return

        if (!maze.contains(current)) {
            location = null
            return
        }

        val pipe = maze.get(current)
        when (looking) {
            'N' -> when (pipe) {
                'S' -> {
                    location = current.north()
                    looking = 'N'
                }

                '|' -> {
                    location = current.north()
                    looking = 'N'
                    leftPipes.add(current.west())
                    rightPipes.add(current.east())
                }

                '7' -> {
                    location = current.west()
                    looking = 'W'
                    leftTurns++
                    rightPipes.add(current.east())
                    rightPipes.add(current.north())
                }

                'F' -> {
                    location = current.east()
                    looking = 'E'
                    rightTurns++
                    leftPipes.add(current.west())
                    leftPipes.add(current.north())
                }

                else -> location = null
            }

            'E' -> when (pipe) {
                'S' -> {
                    location = current.east()
                    looking = 'E'
                }

                '-' -> {
                    location = current.east()
                    looking = 'E'
                    leftPipes.add(current.north())
                    rightPipes.add(current.south())
                }

                'J' -> {
                    location = current.north()
                    looking = 'N'
                    leftTurns++
                    rightPipes.add(current.south())
                    rightPipes.add(current.east())
                }

                '7' -> {
                    location = current.south()
                    looking = 'S'
                    rightTurns++
                    leftPipes.add(current.north())
                    leftPipes.add(current.east())
                }

                else -> location = null
            }

            'S' -> when (pipe) {
                'S' -> {
                    location = current.south()
                    looking = 'S'
                }

                '|' -> {
                    location = current.south()
                    looking = 'S'
                    leftPipes.add(current.east())
                    rightPipes.add(current.west())
                }

                'J' -> {
                    location = current.west()
                    looking = 'W'
                    rightTurns++
                    leftPipes.add(current.east())
                    leftPipes.add(current.south())
                }

                'L' -> {
                    location = current.east()
                    looking = 'E'
                    leftTurns++
                    rightPipes.add(current.west())
                    rightPipes.add(current.south())
                }

                else -> location = null
            }

            'W' -> when (pipe) {
                'S' -> {
                    location = current.west()
                    looking = 'W'
                }

                '-' -> {
                    location = current.west()
                    looking = 'W'
                    leftPipes.add(current.south())
                    rightPipes.add(current.north())
                }

                'L' -> {
                    location = current.north()
                    looking = 'N'
                    rightTurns++
                    leftPipes.add(current.south())
                    leftPipes.add(current.west())
                }

                'F' -> {
                    location = current.south()
                    looking = 'S'
                    leftTurns++
                    rightPipes.add(current.north())
                    rightPipes.add(current.west())
                }

                else -> location = null
            }

            else -> error("Unknown looking direction $looking.")
        }

        distance++
        location?.also { path.add(it) }
    }


    fun getEnclosedSpace(): Int {
        require(isDone()) { "Cannot find enclosed space before scurrying." }
        require(isCycle()) { "Cannot find enclosed space if no cycle was scurried around." }

        val encloses = (if (leftTurns > rightTurns) leftPipes else rightPipes).toMutableSet()

        encloses.removeAll(path)
        encloses.retainAll { maze.contains(it) }

        var enclosed = 0
        while (encloses.size > enclosed) {
            enclosed = encloses.size
            encloses.addAll(encloses.flatMap { it.neighbors() }.toSet().subtract(path))
            encloses.retainAll { maze.contains(it) }
        }

        return encloses.size
    }
}
