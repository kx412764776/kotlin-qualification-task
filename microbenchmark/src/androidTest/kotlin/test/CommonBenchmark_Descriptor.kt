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
class CommonBenchmark_Descriptor {
  private val commonBenchmark: CommonBenchmark = CommonBenchmark()

  @Before
  fun benchmark_CommonBenchmark_setUp() {
    commonBenchmark.setUp()
  }

  @After
  fun benchmark_CommonBenchmark_tearDown() {
    commonBenchmark.teardown()
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_CommonBenchmark_exception() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      commonBenchmark.exception()
    }
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_CommonBenchmark_mathBenchmark() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      commonBenchmark.mathBenchmark()
    }
  }

  @Test
  @OptIn(ExperimentalBenchmarkStateApi::class)
  fun benchmark_CommonBenchmark_longBenchmark() {
    val state = BenchmarkState(warmupCount = 5, repeatCount = 3)
    while (state.keepRunning()) {
      commonBenchmark.longBenchmark()
    }
  }
}
