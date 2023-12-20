package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day20.txt").lines().filter(String::isNotBlank)
    val machine = Machine.fromStrings(lines)

    // Part 1
    val (low, high) = (1..1000).fold(Pair(0L, 0L)) { counts, _ -> counts + machine.pressButton().countSignals() }
    println("Part one: ${low * high}")

    // Part 2
    val joiner = machine.modules.values.single { it.targets == listOf("rx") }
    println("Part two: ${joiner.predecessors.map { machine.findPeriod(it) }.reduce(::lcm)}")
}


private data class Signal(val src: String, val dst: String, val isHigh: Boolean)

private fun Collection<Signal>.countSignals(): Pair<Int, Int> =
    partition { it.isHigh }.let { (high, low) -> Pair(low.size, high.size) }


private class Machine(modules: Collection<Module>) {
    val modules: Map<String, Module> = modules.associateBy { it.name }


    fun pressButton(): Collection<Signal> =
        ArrayDeque(listOf(Signal(src = "button", dst = "broadcaster", isHigh = false)))
            .also { queue -> queue.onEach { queue += modules[it.dst]?.trigger(it.src, it.isHigh) ?: emptyList() } }
            .toList()

    fun findPeriod(moduleName: String): Long =
        (1..10000)
            .map { pressButton().count { it.src == moduleName } }
            .let { counts ->
                val max = counts.max()
                counts.withIndex().filter { it.value == max }
            }
            .let { it[1].index - it[0].index }
            .toLong()


    companion object {
        fun fromStrings(strings: List<String>): Machine =
            strings
                .map { Module.fromString(it) }
                .also { modules -> modules.onEach { it.loadPredecessors(modules) } }
                .let { Machine(it) }
    }
}


private abstract class Module(val name: String, val targets: Collection<String>) {
    var predecessors: List<String> = listOf()
        protected set


    fun loadPredecessors(modules: Collection<Module>) {
        predecessors = modules.filter { this.name in it.targets }.map { it.name }.sorted()
    }

    abstract fun trigger(source: String, inputIsHigh: Boolean): Collection<Signal>


    companion object {
        fun fromString(string: String): Module {
            val parts = string.split(" -> ")
            val targets = parts[1].split(", ")

            return when (parts[0][0]) {
                'b' -> Broadcaster(targets)
                '%' -> FlipFlop(parts[0].drop(1), targets)
                '&' -> Conjunction(parts[0].drop(1), targets)
                else -> error("Unknown module type '${parts[0][0]}'.")
            }
        }
    }
}

private class Broadcaster(targets: Collection<String>) : Module("broadcaster", targets) {
    override fun trigger(source: String, inputIsHigh: Boolean): Collection<Signal> =
        targets.map { Signal(name, it, inputIsHigh) }
}

private class FlipFlop(name: String, targets: Collection<String>) : Module(name, targets) {
    private var isHigh = false


    override fun trigger(source: String, inputIsHigh: Boolean): Collection<Signal> {
        if (inputIsHigh) {
            return emptyList()
        } else {
            isHigh = !isHigh
            return targets.map { Signal(name, it, isHigh) }
        }
    }
}

private class Conjunction(name: String, targets: Collection<String>) : Module(name, targets) {
    private val history = mutableMapOf<String, Boolean>()


    override fun trigger(source: String, inputIsHigh: Boolean): Collection<Signal> {
        history[source] = inputIsHigh

        val send = predecessors.any { history[it] != true }
        return targets.map { Signal(name, it, send) }
    }
}


private operator fun Pair<Long, Long>.plus(that: Pair<Number, Number>): Pair<Long, Long> =
    Pair(this.first + that.first.toLong(), this.second + that.second.toLong())
