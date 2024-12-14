package ivy.di.benchmark

import kotlinx.benchmark.*

@State(Scope.Benchmark)
class MyBenchmark {
  private val size = 10
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