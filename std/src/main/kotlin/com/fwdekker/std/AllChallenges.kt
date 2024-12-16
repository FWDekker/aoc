package com.fwdekker.std

import com.fwdekker.std.maths.naturalNumbersInt
import com.fwdekker.std.maths.sumOf
import kotlin.time.Duration


/**
 * Runs all [Challenge] instances matching a particular pattern.
 *
 * @property prefix The prefix that each class to run must start with.
 * @property nickname The word to use to describe an instance of a challenge that matches the pattern.
 * @property goodTime A threshold of time such that challenges that finish below this threshold are considered "good",
 * described as a time that can be parsed by [Duration.parse].
 */
class AllChallenges(
    private val prefix: String,
    private val nickname: String = "Problem",
    private val goodTime: String = "1s",
) {
    private val loader = AllChallenges::class.java.classLoader


    fun run() = displayPerformance(findResults())


    private fun findChallenges(): Sequence<Challenge> =
        naturalNumbersInt(start = 1)
            .map {
                try {
                    loader.loadClass("$prefix$it").getConstructor().newInstance()
                } catch (_: ClassNotFoundException) {
                    null
                }
            }
            .takeWhile { it != null }
            .filterIsInstance<Challenge>()

    private fun findResults(): Map<IndexedValue<Challenge>, ChallengeResults> =
        findChallenges()
            .also { println("# $nickname") }
            .map { challenge -> Pair(challenge, challenge.timeParts()) }
            .mapIndexed { idx, (challenge, outputs) ->
                challenge to outputs
                    .also { print("$nickname ${idx + 1}. ") }
                    .onEach { (name, value, duration) -> print("$name: $value (${duration.ms} ms). ") }
                    .toList()
                    .also { print("Total time: ${it.sumOf { (_, _, duration) -> duration.ms }} ms.\n") }
            }
            .mapIndexed { idx, (challenge, timedOutputs) ->
                IndexedValue(idx + 1, challenge) to
                    ChallengeResults(timedOutputs, timedOutputs.sumOf { it.duration })
            }
            .toMap()

    private fun displayPerformance(challenges: Map<IndexedValue<Challenge>, ChallengeResults>) {
        val fastCount = challenges.count { (_, results) -> results.totalDuration < Duration.parse(this.goodTime) }
        val fastRatio = String.format("%.2f", fastCount.toDouble() * 100 / challenges.size)

        val formatChallenge = { idx: Int, duration: Duration -> "$nickname $idx (${duration.ms} ms)" }

        println(
            "\n" +
                "# Performance\n" +
                "> ${nickname}s solved within ${this.goodTime}:\n" +
                "$fastCount/${challenges.size} (= $fastRatio%)\n" +
                "> ${nickname}s in decreasing order of initialisation time:\n" +
                challenges
                    .map { (challenge, results) -> challenge.index to results.parts[0].duration }
                    .sortedByDescending { (_, time) -> time }
                    .joinToString(", ") { (number, time) -> formatChallenge(number, time) } +
                "\n" +
                "> ${nickname}s in decreasing order of total runtime:\n" +
                challenges
                    .map { (challenge, results) -> challenge.index to results.totalDuration }
                    .sortedByDescending { (_, time) -> time }
                    .joinToString(", ") { (number, time) -> formatChallenge(number, time) }
        )
    }


    private data class ChallengeResults(val parts: List<PartResult>, val totalDuration: Duration)
}
