package ivy.di.benchmark

import ivy.di.Di
import ivy.di.benchmark.fixtures.*
import ivy.di.benchmark.fixtures.modules.*
import ivy.di.benchmark.fixtures.modules.ivy.AndroidGraphIvyDi
import ivy.di.benchmark.fixtures.modules.koin.AndroidGraphKoin
import kotlinx.benchmark.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.openjdk.jmh.annotations.Level
import java.util.concurrent.TimeUnit

@Suppress("unused")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
class DiComparisonBenchmark {

  private val smallGraphGets = 20
  private val mediumGraphGets = 50
  private val complexGraphGets = 200

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
  fun startKoin() {
    startKoin {
      modules(emptyList())
    }
  }

  @Benchmark
  fun smallGraphIvyDI() {
    Di.init(AndroidGraphIvyDi)
    repeat(smallGraphGets) {
      Di.get<App>()
    }
  }

  @Benchmark
  fun smallGraphKoin() {
    startKoin {
      modules(AndroidGraphKoin)
    }
    repeat(smallGraphGets) {
      getKoin().get<App>()
    }
  }
}