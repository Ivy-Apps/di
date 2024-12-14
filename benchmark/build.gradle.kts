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

dependencies {
  implementation(libs.kotlin.benchmark)
}