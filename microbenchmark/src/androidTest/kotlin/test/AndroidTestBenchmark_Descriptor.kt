package test

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTestBenchmark_Descriptor {
  @get:Rule
  val benchmarkRule: BenchmarkRule = BenchmarkRule()

  private val androidTestBenchmark: AndroidTestBenchmark = AndroidTestBenchmark()

  @Test
  fun benchmark_AndroidTestBenchmark_setUp() {
    androidTestBenchmark.setUp()
  }

  @Test
  fun benchmark_AndroidTestBenchmark_teardown() {
    androidTestBenchmark.teardown()
  }

  @Test
  fun benchmark_AndroidTestBenchmark_sqrtBenchmark() {
    benchmarkRule.measureRepeated {
      androidTestBenchmark.sqrtBenchmark()
    }
  }

  @Test
  fun benchmark_AndroidTestBenchmark_cosBenchmark() {
    benchmarkRule.measureRepeated {
      androidTestBenchmark.cosBenchmark()
    }
  }
}
