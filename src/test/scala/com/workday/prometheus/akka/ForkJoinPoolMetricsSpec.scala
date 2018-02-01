/*
 * =========================================================================================
 * Copyright © 2017,2018 Workday, Inc.
 * Copyright © 2013-2017 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */
package com.workday.prometheus.akka

class ForkJoinPoolMetricsSpec extends BaseSpec {

  override def beforeAll(): Unit = {
    super.beforeAll()
    AkkaMetricRegistry.clear()
  }

  "ForkJoinPoolMetrics" should {
    "support java forkjoinpool" in {
      val name = "ForkJoinPoolMetricsSpec-java-pool"
      val pool = new java.util.concurrent.ForkJoinPool
      try {
        ForkJoinPoolMetrics.add(name, pool.asInstanceOf[ForkJoinPoolLike])
        DispatcherMetricsSpec.findDispatcherRecorder(name) should not be(empty)
      } finally {
        pool.shutdownNow()
      }
    }

    "support scala forkjoinpool" in {
      val name = "ForkJoinPoolMetricsSpec-scala-pool"
      val pool = new scala.concurrent.forkjoin.ForkJoinPool
      try {
        ForkJoinPoolMetrics.add(name, pool.asInstanceOf[ForkJoinPoolLike])
        DispatcherMetricsSpec.findDispatcherRecorder(name) should not be(empty)
      } finally {
        pool.shutdownNow()
      }
    }
  }
}
