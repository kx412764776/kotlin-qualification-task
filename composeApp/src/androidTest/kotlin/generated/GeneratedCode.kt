package generated

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import test.AndroidTestBenchmark
import test.CommonBenchmark
import test.InheritedBenchmark
import test.ParamBenchmark

@RunWith(AndroidJUnit4::class)
class GeneratedCode {

  @get:Rule
  val benchmarkRule = BenchmarkRule()

  val test_AndroidTestBenchmark: AndroidTestBenchmark = AndroidTestBenchmark()

  val test_CommonBenchmark: CommonBenchmark = CommonBenchmark()

  val test_InheritedBenchmark: InheritedBenchmark = InheritedBenchmark()

  val test_ParamBenchmark: ParamBenchmark = ParamBenchmark()

  val test_nested_CommonBenchmark: test.nested.CommonBenchmark = test.nested.CommonBenchmark()

  @Test
  fun benchmarkExecutor_test_AndroidTestBenchmark() {
    benchmarkRule.measureRepeated {
      test_AndroidTestBenchmark.sqrtBenchmark()
      test_AndroidTestBenchmark.cosBenchmark()
    }
  }

  @Test
  fun benchmarkExecutor_test_CommonBenchmark() {
    test_CommonBenchmark.teardown()
    test_CommonBenchmark.exception()
    test_CommonBenchmark.mathBenchmark()
    test_CommonBenchmark.longBenchmark()
//    test_CommonBenchmark.longBlackholeBenchmark()
  }

  @Test
  fun benchmarkExecutor_test_InheritedBenchmark() {
    test_InheritedBenchmark.inheritedSetup()
    test_InheritedBenchmark.inheritedTeardown()
    test_InheritedBenchmark.inheritedBenchmark()
  }

  @Test
  fun benchmarkExecutor_test_ParamBenchmark() {
    test_ParamBenchmark.mathBenchmark()
    test_ParamBenchmark.otherBenchmark()
    test_ParamBenchmark.textContentCheck()
  }

  @Test
  fun benchmarkExecutor_test_nested_CommonBenchmark() {
    test_nested_CommonBenchmark.mathBenchmark()
  }
}
