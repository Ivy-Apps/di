package ivy.di.benchmark

import kotlinx.benchmark.*
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
class DiBenchmark {
  private val size = 100
  private val list = ArrayList<Int>()

  @Setup
  fun prepare() {
    for (i in 0..<size) {
      list.add(i)
    }
  }

  @TearDown
  fun cleanup() {
    list.clear()
  }

  @Benchmark
  fun benchmarkMethod(): Int {
    return list.sum()
  }
}