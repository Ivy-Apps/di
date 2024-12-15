package ivy.di.benchmark.fixtures.modules.ivy

import ivy.di.Di
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import ivy.di.benchmark.fixtures.AppAppHolder
import ivy.di.benchmark.fixtures.AppHolder

object ComplexGraphIvyDi : Di.Module {
  override fun init() = Di.appScope {
    autoWireSingleton(::AppHolder)
    autoWire(::AppAppHolder)
  }
}