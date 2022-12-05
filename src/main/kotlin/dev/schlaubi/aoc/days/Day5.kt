package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.Day
import kotlin.io.path.readLines

data class Instruction(val amount: Int, val from: Int, val to: Int) {
    val fromIndex: Int get() = from - 1
    val toIndex: Int get() = to - 1

    companion object {
        // https://regex101.com/r/sa5S0a/1
        val PATTERN = "move\\s+(\\d+)\\s+from\\s+(\\d+)\\s+to\\s+(\\d+)".toRegex()
    }
}

data class Data(val crates: List<List<Char>>, val instructions: List<Instruction>)

object Day5 : Day<Data>() {
    override val number: Int = 5
    override fun prepare(): Data {
        val lines = input.readLines().iterator()

        // Let's just hope 25 rows is enough
        val crates = MutableList(25) { mutableListOf<Char>() }

        tailrec fun CharIterator.findCrates(id: Int = 1) {
            if (!hasNext()) return
            if (next() != '[') { // add char to compartment
                skip(3) // skip empty crate
            } else {
                // add char to compartment
                crates[id - 1].add(next())
                val closingChar = next()
                if (closingChar != ']') {
                    error("Unexpected closing char: $closingChar")
                } else {
                    skip() // skip space
                }
            }
            return findCrates(id + 1)
        }

        while (lines.hasNext()) {
            val line = lines.next()
            // " 1   2   3 "
            if (line[1] == '1') {
                val lastId = line[line.length - 1].digitToInt()
                // Clean up empty lists
                for (index in lastId..crates.lastIndex) {
                    crates.removeAt(crates.lastIndex)
                }
                break // if we are at index line abort
            }
            val iterator = line.iterator()
            iterator.findCrates()
        }

        lines.skip() // skip empty divider

        val instructions =
                lines.asSequence().map {
                    val (amount, from, to) = Instruction.PATTERN.find(it)?.destructured
                            ?: error("Invalid move instruction: $it")

                    Instruction(amount.toInt(), from.toInt(), to.toInt())
                }.toList()

        return Data(crates.map { it.toList() }, instructions)
    }

    override fun task1(prepared: Data): Any = task(prepared, false)

    override fun task2(prepared: Data): Any = task(prepared, true)

    private fun task(prepared: Data, sameOrder: Boolean): String {
        val myGrid = prepared.crates.asMutable()
        myGrid.applyInstruction(prepared.instructions, sameOrder)
        return myGrid.map(MutableList<Char>::first).joinToString("")
    }
}

private fun MutableList<MutableList<Char>>.applyInstruction(instructions: List<Instruction>, sameOrder: Boolean = false) =
        instructions.forEach { applyInstruction(it, sameOrder) }

private fun MutableList<MutableList<Char>>.applyInstruction(instruction: Instruction, sameOrder: Boolean = false) {
    val to = this[instruction.toIndex]
    val from = this[instruction.fromIndex]
    if (sameOrder) {
        to.addAll(0, from.removeFirstN(instruction.amount))
    } else {
        repeat(instruction.amount) { to.add(0, from.removeFirst()) }
    }
}

private fun List<List<Char>>.asMutable() = map(List<Char>::toMutableList).toMutableList()

private fun Iterator<*>.skip(n: Int = 1) = repeat(n) { if (hasNext()) next() }

private fun <T> MutableList<T>.removeFirstN(n: Int = 1) = buildList { repeat(n) { add(this@removeFirstN.removeFirst()) } }