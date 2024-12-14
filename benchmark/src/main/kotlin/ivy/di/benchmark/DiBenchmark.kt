package ivy.di.benchmark

import ivy.di.Di
import kotlinx.benchmark.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.openjdk.jmh.annotations.Level
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
class DiComparisonBenchmark {

  @TearDown(Level.Invocation)
  fun cleanup() {
    stopKoin() // Clean up Koin
    Di.reset() // Clean up Ivy DI
  }

  @Benchmark
  fun startIvyDi() {
    Di.appScope {}
  }

  @Benchmark
  fun startKoinDi() {
    startKoin {
      modules(emptyList())
    }
  }
}