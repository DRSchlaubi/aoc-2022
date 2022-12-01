package dev.schlaubi.aoc

import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div

/**
 * Implementation of [Day] where both tasks use different inputs.
 */
abstract class SeparateDay : Day<Unit>() {
    override fun prepare() = Unit
    override fun task1(prepared: Unit): Any? = task1()
    override fun task2(prepared: Unit): Any? = task2()

    /**
     * Computes task 1 and returns it's output.
     */
    abstract fun task1(): Any?

    /**
     * Computes task 2 and returns it's output.
     */
    abstract fun task2(): Any?
}

/**
 * Abstract implementation of a daily task.
 */
abstract class Day<T> {
    /**
     * Numeric day value.
     */
    abstract val number: Int

    /**
     * [Path] to `inputs/day_$number/input_1.txt`.
     */
    val input1 get() = input("1", number)

    /**
     * Alias to [input1] (useful for tasks with just one input.
     */
    val input: Path get() = input1

    /**
     * [Path] to `inputs/day_$number/input_2.txt`.
     */
    val input2 get() = input("2", number)

    /**
     * Do some shared calculation between both tasks.
     */
    abstract fun prepare(): T

    /**
     * Computes task 1 and returns it's output.
     *
     * @param prepared value of [prepare]
     */
    abstract fun task1(prepared: T): Any?

    /**
     * Computes task 2 and returns it's output.
     *
     * @param prepared value of [prepare]
     */
    abstract fun task2(prepared: T): Any?
}

private fun input(postfix: String, day: Int) = Path("inputs") / "day_${day.toString().padStart(2, '0')}" / "input_$postfix.txt"
