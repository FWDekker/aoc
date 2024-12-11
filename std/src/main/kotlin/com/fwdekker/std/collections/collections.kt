@file:Suppress("unused")
package com.fwdekker.std.collections

import com.fwdekker.std.maths.min
import com.fwdekker.std.maths.wrapMod
import kotlin.experimental.ExperimentalTypeInference


/**
 * Returns the character at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun String.getMod(index: Int): Char = this[index.wrapMod(length)]

/**
 * Returns the element at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun <T> List<T>.getMod(index: Int): T = this[index.wrapMod(size)]


/**
 * Shorthand for invoking [withIndex] and then [sumOf].
 */
@JvmName("intSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> Int): Int =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

@JvmName("longSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> Long): Long =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

/**
 * Returns the left-folded addition of all contained maps.
 */
fun <K, V> Iterable<Map<K, V>>.sum() = fold(emptyMap<K, V>()) { acc, it -> acc + it }


/**
 * Swaps the first and second entry.
 */
fun <A, B> Pair<A, B>.swap(): Pair<B, A> = Pair(second, first)

/**
 * Returns the first element of each pair.
 */
fun <A, B> Iterable<Pair<A, B>>.firsts(): List<A> = map { it.first }

/**
 * Returns the second element of each pair.
 */
fun <A, B> Iterable<Pair<A, B>>.seconds(): List<B> = map { it.second }

/**
 * Maps both entries using [transform].
 */
fun <A, T> Pair<A, A>.map(transform: (A) -> T): Pair<T, T> = Pair(transform(first), transform(second))

/**
 * Maps all entries using [transform].
 */
fun <A, T> Triple<A, A, A>.map(transform: (A) -> T): Triple<T, T, T> =
    Triple(transform(first), transform(second), transform(third))

/**
 * Maps only the first entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapFirst(transform: (A) -> T): Pair<T, B> = Pair(transform(first), second)

/**
 * Maps the first entry in each pair using [transform].
 */
fun <A, B, T> Iterable<Pair<A, B>>.mapFirsts(transform: (A) -> T): List<Pair<T, B>> = map { it.mapFirst(transform) }

/**
 * Maps only the second entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapSecond(transform: (B) -> T): Pair<A, T> = Pair(first, transform(second))

/**
 * Maps the second entry in each pair using [transform].
 */
fun <A, B, T> Iterable<Pair<A, B>>.mapSeconds(transform: (B) -> T): List<Pair<A, T>> = map { it.mapSecond(transform) }

/**
 * Returns a pair containing the first two elements.
 */
fun <T> List<T>.asPair(): Pair<T, T> = Pair(this[0], this[1])

/**
 * Returns a pair containing the first three elements.
 */
fun <T> List<T>.asTriple(): Triple<T, T, T> = Triple(this[0], this[1], this[2])

/**
 * Zips [Pair.first] with [Pair.second].
 */
fun <A, B> Pair<Iterable<A>, Iterable<B>>.zipped(): List<Pair<A, B>> = first zip second


/**
 * Repeats [this] sequence [amount] times.
 */
fun <T> Sequence<T>.repeat(amount: Int): Sequence<T> = sequence { for (i in 1..amount) yieldAll(this@repeat) }

fun <T> Iterable<T>.repeat(amount: Int): List<T> = asSequence().repeat(amount).toList()

/**
 * Repeats [this] sequence infinitely many times.
 */
fun <T> Sequence<T>.cyclic(): Sequence<T> = sequence { while (true) yieldAll(this@cyclic) }

fun <T> Iterable<T>.cyclic(): Sequence<T> = asSequence().cyclic()

/**
 * Returns `true` if an element occurs more than once, or `false` if all elements are unique.
 *
 * This method may not terminate if the sequence is infinitely long.
 */
fun <T> Sequence<T>.isDistinct(): Boolean {
    val seen = mutableSetOf<T>()
    return any { !seen.add(it) }
}

fun <T> Iterable<T>.isDistinct(): Boolean = asSequence().isDistinct()


/**
 * Removes the element at [idx], and returns [this] list.
 */
fun <T> List<T>.without(idx: Int): MutableList<T> = toMutableList().also { it.removeAt(idx) }

/**
 * Swaps the elements at [idx1] and [idx2], and returns [this] list.
 */
fun <T> MutableList<T>.swapAt(idx1: Int, idx2: Int): MutableList<T> {
    this[idx1] = this[idx2].also { this[idx2] = this[idx1] }
    return this
}


/**
 * Merges [this] with [that] by selecting elements from both collections in an alternating fashion, starting with
 * [this].
 *
 * If the collections have different sizes, then the unmatched part of the longer collection is simply appended.
 */
fun <T> Collection<T>.unzip(that: Collection<T>): List<T> {
    val common = min(this.size, that.size)
    return this.take(common).zip(that.take(common)).flatMap { it.toList() } + this.drop(common) + that.drop(common)
}


/**
 * Like `iterator`, but without [ConcurrentModificationException]s when elements are appended after the iterator has
 * been created.
 */
fun <T> MutableList<T>.appendableIterator(): Iterator<T> =
    iterator {
        var i = 0
        while (i < size) {
            yield(get(i))
            i++
        }
    }

/**
 * Like `onEach`, but without [ConcurrentModificationException]s when [action] appends elements to the list.
 *
 * [action] now takes one extra argument in front of the classic argument, which is a reference to [this].
 */
fun <T> MutableList<T>.appendOnEach(action: (MutableList<T>, T) -> Unit): MutableList<T> =
    this.also { appendableIterator().forEach { action(this, it) } }

/**
 * Like `fold`, but without [ConcurrentModificationException]s when [operation] appends elements to the list.
 *
 * [operation] now takes one extra argument in front of the two classic arguments, which is a reference to [this].
 */
fun <T, R> MutableList<T>.appendingFold(initial: R, operation: (MutableList<T>, R, T) -> R): R {
    var acc = initial
    appendOnEach { self, it -> acc = operation(self, acc, it) }
    return acc
}


fun <K, V> MutableMap<K, V>.with(key: K, value: V): MutableMap<K, V> = this.also { put(key, value) }

/**
 * Folds the elements of this map.
 */
fun <K, V, R> Map<K, V>.fold(initial: R, operation: (acc: R, Pair<K, V>) -> R): R = toList().fold(initial, operation)
