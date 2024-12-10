@file:Suppress("unused")
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
     * Faces towards [direction] without moving.
     */
    fun face(direction: Direction): Heading = go(direction, distance = 0)

    /**
     * Faces towards the result of applying [transform] to [direction] without moving.
     */
    fun face(transform: Direction.() -> Direction): Heading = go(transform, distance = 0)

    /**
     * Turns to [direction] and sets [distance] steps.
     */
    fun go(direction: Direction = this.direction, distance: Int = 1): Heading =
        Heading(coords.move(direction, distance), direction)

    /**
     * Turns to the result of applying [transform] to [direction] and sets one step.
     */
    fun go(transform: Direction.() -> Direction): Heading =
        go(transform, 1)

    /**
     * Turns to the result of applying [transform] to [direction] and sets [distance] steps.
     */
    fun go(transform: Direction.() -> Direction, distance: Int): Heading =
        go(transform(direction), distance)
}
