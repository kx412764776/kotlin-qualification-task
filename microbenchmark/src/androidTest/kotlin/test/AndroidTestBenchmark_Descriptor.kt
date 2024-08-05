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
class AndroidTestBenchmark_Descriptor {
  private val androidTestBenchmark: AndroidTestBenchmark = AndroidTestBenchmark()

  @Before
  fun benchmark_AndroidTestBenchmark_setUp() {
    androidTestBenchmark.setUp()
  }

  @After
  fun benchmark_AndroidTestBenchmark_tearDown() {
    androidTestBenchmark.teardown()
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_AndroidTestBenchmark_sqrtBenchmark() {
    val state = BenchmarkState(warmupCount = 3, repeatCount = 3)
    while (state.keepRunning()) {
      androidTestBenchmark.sqrtBenchmark()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_AndroidTestBenchmark_cosBenchmark() {
    val state = BenchmarkState(warmupCount = 3, repeatCount = 3)
    while (state.keepRunning()) {
      androidTestBenchmark.cosBenchmark()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }
}
