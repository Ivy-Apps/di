package ivy.di

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import ivy.di.Di.register
import ivy.di.Di.singleton
import kotlin.test.BeforeTest
import kotlin.test.Test

private var initialized = false

class Counter(var x: Int) {
    init {
        initialized = true
    }
}

class CountManager(private val counter: Lazy<Counter>) {
    fun increment() {
        counter.value.x++
    }
}

class LazyTest {

    @BeforeTest
    fun setup() {
        Di.reset()
        initialized = false
    }

    @Test
    fun lazyInitialization() {
        // Given
        Di.appScope {
            register { Counter(x = 42) }
        }

        // When
        val lazyCounter = Di.getLazy<Counter>()

        // Initially the lazy counter isn't initialized
        initialized shouldBe false

        // When the lazy value is accessed
        val counter = lazyCounter.value

        // Then
        initialized shouldBe true
        counter.x shouldBe 42
    }

    @Test
    fun lazySingletonInitialization() {
        // Given
        Di.appScope {
            singleton { Counter(x = 0) }
        }

        // When
        val lazyCounter = Di.getLazy<Counter>()

        // Initially the lazy counter isn't initialized
        initialized shouldBe false

        // When the lazy value is accessed and modified
        lazyCounter.value.x shouldBe 0
        lazyCounter.value.x = 42

        // Then
        initialized shouldBe true
        Di.getLazy<Counter>().value.x shouldBe 42
    }

    @Test
    fun missingFactory() {
        // When-Then
        shouldThrow<DependencyInjectionError> {
            Di.getLazy<Counter>()
        }
    }

    @Test
    fun realWorldScenario() {
        // Given
        Di.appScope {
            singleton { Counter(x = 0) }
            register { CountManager(Di.getLazy()) }
        }
        val manager = Di.get<CountManager>()

        // Initially the lazy counter isn't initialized
        initialized shouldBe false

        // When
        manager.increment()

        // Then
        initialized shouldBe true
        Di.get<Counter>().x shouldBe 1
    }
}