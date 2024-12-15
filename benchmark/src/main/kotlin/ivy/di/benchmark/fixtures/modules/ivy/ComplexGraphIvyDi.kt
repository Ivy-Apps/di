package ivy.di.benchmark.fixtures.modules.ivy

import ivy.di.Di
import ivy.di.Di.bind
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import ivy.di.benchmark.fixtures.*

object ComplexGraphIvyDi : Di.Module {
  override fun init() = Di.appScope {
    autoWire(::Holder1)
    autoWireSingleton(::Holder2)
    autoWire(::Holder3)
    autoWireSingleton(::Holder4)
    autoWire(::Holder5)
    autoWireSingleton(::Holder6)
    autoWire(::Holder7)
    autoWireSingleton(::Holder8)
    autoWire(::Holder9)
    autoWireSingleton(::Holder10)

    autoWire(::MultiHolderImpl)
    bind<MultiHolder, MultiHolderImpl>()
    autoWire(::MultiHolderFinal)
  }
}