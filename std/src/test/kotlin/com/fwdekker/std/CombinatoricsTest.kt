package com.fwdekker.std

import com.fwdekker.containExactlyInAnyOrderElementsOf
import com.fwdekker.std.maths.powerSet
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.should


/**
 * Unit tests for `combinators.kt`.
 */
object CombinatoricsTest : DescribeSpec({
    describe("powerSet") {
        val all = listOf(
            emptyList(),
            listOf(1),
            listOf(2),
            listOf(3),
            listOf(1, 2),
            listOf(1, 3),
            listOf(2, 3),
            listOf(1, 2, 3),
        )

        withData(
            nameFn = { "all combinations of 1..${it.first}" },
            0 to listOf(emptyList()),
            1 to listOf(emptyList(), listOf(1)),
            2 to listOf(emptyList(), listOf(1), listOf(2), listOf(1, 2)),
            3 to all,
        ) { (input, expected) ->
            (1..input).toList().powerSet() should containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with minimum size ${it.first}" },
            -1 to all,
            0 to all,
            1 to listOf(listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)),
            2 to listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)),
            3 to listOf(listOf(1, 2, 3)),
        ) { (minSize, expected) ->
            listOf(1, 2, 3).powerSet(minSize = minSize) should containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with maximum size ${it.first}" },
            -1 to listOf(emptyList()),
            0 to listOf(emptyList()),
            1 to listOf(emptyList(), listOf(1), listOf(2), listOf(3)),
            2 to listOf(emptyList(), listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            3 to all,
            4 to all,
        ) { (maxSize, expected) ->
            listOf(1, 2, 3).powerSet(maxSize = maxSize) should containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with exact size ${it.first}" },
            -1 to listOf(emptyList()),
            0 to listOf(emptyList()),
            1 to listOf(listOf(1), listOf(2), listOf(3)),
            2 to listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            3 to listOf(listOf(1, 2, 3)),
            4 to emptyList(),
        ) { (size, expected) ->
            listOf(1, 2, 3).powerSet(minSize = size, maxSize = size) should
                containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with size in range ${it.first.first}..${it.first.second}" },
            (-1 to 2) to listOf(emptyList(), listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            (1 to 2) to listOf(listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            (1 to 3) to listOf(
                listOf(1),
                listOf(2),
                listOf(3),
                listOf(1, 2),
                listOf(1, 3),
                listOf(2, 3),
                listOf(1, 2, 3)
            ),
            (2 to 3) to listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)),
            (3 to 4) to listOf(listOf(1, 2, 3)),
        ) { (range, expected) ->
            listOf(1, 2, 3).powerSet(minSize = range.first, maxSize = range.second) should
                containExactlyInAnyOrderElementsOf(expected)
        }
    }
})
