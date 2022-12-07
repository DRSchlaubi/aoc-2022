package dev.schlaubi.aoc

/**
 * Splits a list each time [delimiter] occurs.
 */
fun <T> List<T>.splitBy(delimiter: T): List<List<T>> = splitWhen { it == delimiter }

/**
 * Splits a list of Strings each time a blank entry occurs.
 */
fun <T : CharSequence> List<T>.splitByBlank(): List<List<T>> = splitWhen(CharSequence::isBlank)

/**
 * Splits a list each time [predicate] returns `true`.
 */
inline fun <T> List<T>.splitWhen(crossinline predicate: (T) -> Boolean): List<List<T>> {
    return asSequence()
            .withIndex()
            .filter { (index, value) -> predicate(value) || index == 0 || index == size - 1 } // Just getting the delimiters with their index; Include 0 and last -- so to not ignore it while pairing later on
            .zipWithNext() // zip the IndexValue with the adjacent one so to later remove continuous delimiters; Example: Indices : 0,1,2,5,7 -> (0,1),(1,2),(2,5),(5,7)
            .filter { pair -> pair.first.index + 1 != pair.second.index } // Getting rid of continuous delimiters; Example: (".",".") will be removed, where "." is the delimiter
            .map { pair ->
                val startIndex = if (predicate(pair.first.value)) pair.first.index + 1 else pair.first.index // Trying to not consider delimiters
                val endIndex = if (!predicate(pair.second.value) && pair.second.index == size - 1) pair.second.index + 1 else pair.second.index // subList() endIndex is exclusive
                subList(startIndex, endIndex) // Adding the relevant sub-list
            }.toList()
}

/**
 * Skips the next [n] elements of the iterator.
 */
fun Iterator<*>.skip(n: Int = 1) = repeat(n) { if (hasNext()) next() }
