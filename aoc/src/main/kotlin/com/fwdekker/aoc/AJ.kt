package com.fwdekker.aoc

import com.fwdekker.std.collections.getMod
import com.fwdekker.std.maths.permutations
import com.fwdekker.std.toDigits
import com.fwdekker.std.toInts
import kotlin.system.exitProcess


object AJ {
    private val alphabet: Map<Char, String> =
        ('A'..'Z')
            .mapIndexed { idx, char -> char to (idx + 1) }
            .toMap()
            .mapValues { (_, idx) -> if (idx <= 2) "0$idx" else "$idx" }
    private val reverseAlphabet: Map<String, Char> = alphabet.reverseMap()


    private fun encode(text: String): List<Int> =
        text.filter { it != ' ' }.map { it.uppercaseChar() }.joinToString("") { alphabet.getValue(it) }.toDigits()

    private fun encodeKey2(key2: List<Int>): Map<Int, Char> {
        val map = mutableMapOf<Int, Char>()

        var keyIndex = 0
        var value = 0
        for (char in 'A'..'J') {
            value = (value + key2.getMod(keyIndex++)) % 10
            while (value in map.keys) value = (value + 1) % 10
            map[value] = char
        }

        return map
    }

    private fun decode(text: List<Int>): String =
        sequence {
            var ptr = 0
            while (ptr in text.indices) {
                yield(
                    if ((text[ptr] * 10 + text[ptr + 1]) > 26) reverseAlphabet["${text[ptr]}"]!!.also { ptr += 1 }
                    else reverseAlphabet["${text[ptr]}${text[ptr + 1]}"]!!.also { ptr += 2 }
                )
            }
        }.joinToString("")


    fun encrypt(message: String, key1: String, key2: String): String =
        encrypt(message, encode(key1), encodeKey2(encode(key2)))

    fun encrypt(message: String, key1: List<Int>, key2: Map<Int, Char>): String =
        encode(message)
            .mapIndexed { idx, digit -> (10 + digit - key1.getMod(idx)) % 10 }
            .joinToString("") { char -> key2.getValue(char).toString() }

    fun decrypt(ciphertext: String, key1: String, key2: String): String =
        decrypt(ciphertext, encode(key1), encodeKey2(encode(key2)).reverseMap())

    fun decrypt(ciphertext: String, key1: List<Int>, key2: Map<Char, Int>): String =
        ciphertext
            .map { key2.getValue(it) }
            .mapIndexed { idx, digit -> (digit + key1.getMod(idx)) % 10 }
            .let { decode(it) }
}

private fun <K, V> Map<K, V>.reverseMap(): Map<V, K> = map { it.value to it.key }.toMap()


private fun test() {
    val key1 = "Appel"
    val key2 = "Peer"
    val plaintext = "Dit is een geheim bericht"
    val ciphertext = AJ.encrypt(plaintext, key1, key2)
    val plaintext_ = AJ.decrypt(ciphertext, key1, key2)

    println("$plaintext -> $ciphertext -> $plaintext_")
}


fun main() {
    test()

    val ciphertext = "HDAHDIHHGAGJEBEHFAFAHIBGDBFF"
    (0..9).permutations().first { permutation ->
        val key1 = "0,1,1,6,1,6,5,1,2".toInts(',')
        val key2 = ('A'..'J').mapIndexed { idx, char -> char to permutation[idx] }.toMap()

        try {
            "een" in AJ.decrypt(ciphertext, key1, key2)
        } catch (e: Exception) {
            false
        }
    }.let { println(it) }
}
