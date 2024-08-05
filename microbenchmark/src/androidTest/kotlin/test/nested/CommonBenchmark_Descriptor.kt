package test.nested

import androidx.benchmark.BenchmarkState
import androidx.benchmark.ExperimentalBenchmarkStateApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlin.OptIn
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonBenchmark_Descriptor {
  private val commonBenchmark: CommonBenchmark = CommonBenchmark()

  @Before
  fun benchmark_CommonBenchmark_setUp() {
    commonBenchmark.setUp()
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_CommonBenchmark_mathBenchmark() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      commonBenchmark.mathBenchmark()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }
}
