package test.nested

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonBenchmark_Descriptor {
  @get:Rule
  val benchmarkRule: BenchmarkRule = BenchmarkRule()

  private val commonBenchmark: CommonBenchmark = CommonBenchmark()

  @Test
  fun benchmark_CommonBenchmark_setUp() {
    commonBenchmark.setUp()
  }

  @Test
  fun benchmark_CommonBenchmark_mathBenchmark() {
    benchmarkRule.measureRepeated {
      commonBenchmark.mathBenchmark()
    }
  }
}
