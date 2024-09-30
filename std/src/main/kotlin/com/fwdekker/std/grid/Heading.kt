package com.fwdekker.std.grid


/**
 * A location and a direction.
 */
data class Heading(val coords: Coords, val direction: Direction) {
    /**
     * Utility constructor for by-passing the [Coords] constructor.
     */
    constructor(row: Int, col: Int, direction: Direction) : this(Coords(row, col), direction)


    /**
     * Turns and moves to [direction].
     */
    fun go(direction: Direction): Heading = Heading(coords.move(direction), direction)

    /**
     * Turns and moves to the result of applying [transform] to [direction].
     */
    fun go(transform: Direction.() -> Direction): Heading = go(transform(direction))

    /**
     * Turns and moves to each of the [directions].
     */
    fun go(vararg directions: Direction): List<Heading> = directions.map { go(it) }

    /**
     * Turns and moves to each result of applying the [transforms] to [direction].
     */
    fun go(vararg transforms: Direction.() -> Direction): List<Heading> = transforms.map { go(it) }
}
