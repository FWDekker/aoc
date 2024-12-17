package com.fwdekker.std


/**
 * Returns the (whitespace-trimmed) contents of resource [path].
 */
fun read(path: String): String =
    {}.javaClass.getResource(path)
        ?.readText()?.trim()
        ?: error("Could not find resource $path.")
