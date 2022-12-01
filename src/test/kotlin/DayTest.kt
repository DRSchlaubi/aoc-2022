import dev.schlaubi.aoc.Day
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DayTest<T>(val day: Day<T>) {
    abstract val test1Result: Any
    abstract val test2Result: Any

    var prepared: T? = null

    @BeforeAll
    fun beforeAll() {
        prepared = day.prepare()
    }

    @Test
    fun `test task 1`() {
        day.task1(prepared!!)
    }

    @Test
    fun `test task 2`() {
        day.task2(prepared!!)
    }
}