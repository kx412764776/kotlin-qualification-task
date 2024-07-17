package test

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InheritedBenchmark_Descriptor {
  @get:Rule
  val benchmarkRule: BenchmarkRule = BenchmarkRule()

  private val inheritedBenchmark: InheritedBenchmark = InheritedBenchmark()

  @Test
  fun benchmark_InheritedBenchmark_inheritedSetup() {
    inheritedBenchmark.inheritedSetup()
  }

  @Test
  fun benchmark_InheritedBenchmark_inheritedTeardown() {
    inheritedBenchmark.inheritedTeardown()
  }

  @Test
  fun benchmark_InheritedBenchmark_inheritedBenchmark() {
    benchmarkRule.measureRepeated {
      inheritedBenchmark.inheritedBenchmark()
    }
  }
}
