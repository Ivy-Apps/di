package ivy.di.benchmark

import ivy.di.Di
import ivy.di.benchmark.fixtures.*
import ivy.di.benchmark.fixtures.modules.*
import ivy.di.benchmark.fixtures.modules.ivy.AndroidGraphIvyDi
import ivy.di.benchmark.fixtures.modules.ivy.BeGraphIvyDi
import ivy.di.benchmark.fixtures.modules.ivy.CommonGraphIvyDi
import ivy.di.benchmark.fixtures.modules.ivy.ComplexGraphIvyDi
import ivy.di.benchmark.fixtures.modules.koin.AndroidGraphKoin
import ivy.di.benchmark.fixtures.modules.koin.BeGraphKoin
import ivy.di.benchmark.fixtures.modules.koin.CommonGraphKoin
import ivy.di.benchmark.fixtures.modules.koin.ComplexGraphKoin
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
    Di.init(CommonGraphIvyDi, AndroidGraphIvyDi)
    repeat(smallGraphGets) {
      Di.get<App>()
    }
  }

  @Benchmark
  fun smallGraphKoin() {
    startKoin {
      modules(CommonGraphKoin, AndroidGraphKoin)
    }
    repeat(smallGraphGets) {
      getKoin().get<App>()
    }
  }

  @Benchmark
  fun mediumGraphIvyDI() {
    Di.init(CommonGraphIvyDi, AndroidGraphIvyDi, BeGraphIvyDi)
    repeat(mediumGraphGets) {
      Di.get<App>()
      Di.get<ServerApp>()
    }
  }

  @Benchmark
  fun mediumGraphKoin() {
    startKoin {
      modules(CommonGraphKoin, AndroidGraphKoin, BeGraphKoin)
    }
    repeat(mediumGraphGets) {
      getKoin().get<App>()
      getKoin().get<ServerApp>()
    }
  }

  @Benchmark
  fun complexGraphIvyDI() {
    Di.init(
      CommonGraphIvyDi,
      AndroidGraphIvyDi,
      BeGraphIvyDi,
      ComplexGraphIvyDi,
    )
    repeat(complexGraphGets) {
      Di.get<MultiHolderFinal>()
    }
  }

  @Benchmark
  fun complexGraphKoin() {
    startKoin {
      modules(
        CommonGraphKoin,
        AndroidGraphKoin,
        BeGraphKoin,
        ComplexGraphKoin,
      )
    }
    repeat(complexGraphGets) {
      getKoin().get<MultiHolderFinal>()
    }
  }
}

fun main() {
  println("Testing correctness...")
  DiComparisonBenchmark().apply {
    cleanup()
    smallGraphIvyDI()
    smallGraphKoin()

    cleanup()
    mediumGraphIvyDI()
    mediumGraphKoin()

    cleanup()
    complexGraphIvyDI()
    complexGraphKoin()
  }
  println("Correctness ensured.")
}