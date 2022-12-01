package dev.schlaubi.aoc

fun main(args: Array<String>) {
    val days = args.first().split(",\\s*".toRegex())

    days.forEach {
        val clazz = Class.forName("dev.schlaubi.aoc.days.Day$it").kotlin
        val instance = clazz.objectInstance as Day<*>

        instance.run()
    }
}

private fun <T> Day<T>.run() {
    val data = prepare()
    println(task1(data))
    println(task2(data))
}