package test

import androidx.benchmark.BenchmarkState
import androidx.benchmark.ExperimentalBenchmarkStateApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlin.OptIn
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ParamBenchmark_Descriptor {
  private val paramBenchmark: ParamBenchmark = ParamBenchmark()

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_ParamBenchmark_mathBenchmark() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      paramBenchmark.mathBenchmark()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_ParamBenchmark_otherBenchmark() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      paramBenchmark.otherBenchmark()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_ParamBenchmark_textContentCheck() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      paramBenchmark.textContentCheck()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }
}
