package ivy.di.benchmark

import ivy.di.Di
import ivy.di.benchmark.fixtures.android.*
import kotlinx.benchmark.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.openjdk.jmh.annotations.Level
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
class DiComparisonBenchmark {

  private val diGetIterations = 100

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
  fun androidCommonIvyDi() {
    Di.init(AndroidCommonModuleIvyDi)
    repeat(diGetIterations) {
      Di.get<ArticlesViewModel>()
      Di.get<AuthorViewModel>()
      Di.get<App>()
      Di.get<AppHolder>()
      Di.get<AppAppHolder>()
    }
  }

  @Benchmark
  fun androidCommonKoin() {
    startKoin {
      modules(AndroidCommonModuleKoin)
    }
    repeat(diGetIterations) {
      getKoin().get<ArticlesViewModel>()
      getKoin().get<AuthorViewModel>()
      getKoin().get<App>()
      getKoin().get<AppHolder>()
      getKoin().get<AppAppHolder>()
    }
  }
}