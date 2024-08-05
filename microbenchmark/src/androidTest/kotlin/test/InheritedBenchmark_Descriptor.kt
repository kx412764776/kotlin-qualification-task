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
class InheritedBenchmark_Descriptor {
  private val inheritedBenchmark: InheritedBenchmark = InheritedBenchmark()

  @Before
  fun benchmark_InheritedBenchmark_setUp() {
    inheritedBenchmark.inheritedSetup()
  }

  @After
  fun benchmark_InheritedBenchmark_tearDown() {
    inheritedBenchmark.inheritedTeardown()
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_InheritedBenchmark_inheritedBenchmark() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 10)
    while (state.keepRunning()) {
      inheritedBenchmark.inheritedBenchmark()
    }
    val measurementResult = state.getMeasurementTimeNs()
    measurementResult.forEachIndexed { index, time ->
      println("Iteration ${index + 1}: $time ns")
    }
  }
}
