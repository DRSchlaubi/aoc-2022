package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.SeparateDay
import kotlin.io.path.readLines

object Day2 : SeparateDay() {
    override val number: Int = 2

    override fun task1(): Any {
        return input.readLines()
                .map {
                    val (opponent, you) = it.split(" ")
                    GameItem.fromLetter(opponent[0]) to GameItem.fromLetter(you[0])
                }.score()
    }

    override fun task2(): Any {
        return input.readLines()
                .map {
                    val (opponent, winType) = it.split(" ")
                    val opponentItem = GameItem.fromLetter(opponent[0])
                    val item = when (winType[0]) {
                        'X' -> GameItem.items.first { item -> opponentItem.beats(item) }
                        'Y' -> opponentItem
                        'Z' -> GameItem.items.first { item -> item.beats(opponentItem) }
                        else -> error("Invalid letter: $winType")
                    }
                    opponentItem to item
                }.score()
    }

    private fun List<Pair<GameItem, GameItem>>.score() = sumOf { (opponent, you) ->
        val winnerScore = when {
            opponent == you -> 3
            you.beats(opponent) -> 6
            else -> 0
        }

        winnerScore + you.score
    }

}

sealed interface GameItem {
    val score: Int
    fun beats(other: GameItem): Boolean

    object Rock : GameItem {
        override val score: Int = 1
        override fun beats(other: GameItem): Boolean = other == Scissors
    }

    object Paper : GameItem {
        override val score: Int = 2
        override fun beats(other: GameItem): Boolean = other == Rock
    }

    object Scissors : GameItem {
        override val score: Int = 3
        override fun beats(other: GameItem): Boolean = other == Paper
    }

    companion object {
        val items = listOf(Rock, Paper, Scissors)
        fun fromLetter(letter: Char) = when (letter) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors
            else -> error("Unknown letter: $letter")
        }
    }
}