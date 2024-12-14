plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.kotlin.allOpen)
  alias(libs.plugins.kotlin.benchmark)
}

allOpen {
  annotation("org.openjdk.jmh.annotations.State")
}

benchmark {
  targets {
    register("main")
  }
}

benchmark {
  targets {
    register("jvm")
  }
  configurations {
    named("main") {
      warmups = 2
      iterations = 5
      iterationTime = 10
      iterationTimeUnit = "s"

      reportFormat = "csv"
      outputTimeUnit = "ms"
    }
  }
}

dependencies {
  implementation(libs.kotlin.benchmark)
  implementation(project(":di"))
  implementation(libs.koin.core)
}