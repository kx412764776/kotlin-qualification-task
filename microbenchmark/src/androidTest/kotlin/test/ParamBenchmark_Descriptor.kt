package test

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ParamBenchmark_Descriptor {
  @get:Rule
  val benchmarkRule: BenchmarkRule = BenchmarkRule()

  private val paramBenchmark: ParamBenchmark = ParamBenchmark()

  @Test
  fun benchmark_ParamBenchmark_mathBenchmark() {
    benchmarkRule.measureRepeated {
      paramBenchmark.mathBenchmark()
    }
  }

  @Test
  fun benchmark_ParamBenchmark_otherBenchmark() {
    benchmarkRule.measureRepeated {
      paramBenchmark.otherBenchmark()
    }
  }

  @Test
  fun benchmark_ParamBenchmark_textContentCheck() {
    benchmarkRule.measureRepeated {
      paramBenchmark.textContentCheck()
    }
  }
}
