import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.publish)
}

kotlin {
  jvm()
  androidTarget {
    publishLibraryVariants("release")
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_1_8)
    }
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  linuxX64()
  js(IR) {
    browser()
    nodejs()
  }
  @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
  wasmJs {
    browser()
    nodejs()
    d8()
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        //put your multiplatform dependencies here
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.bundles.test)
      }
    }
  }
}

android {
  namespace = "com.ivy_apps.di"
  compileSdk = libs.versions.android.compileSdk.get().toInt()
  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
  }
}
