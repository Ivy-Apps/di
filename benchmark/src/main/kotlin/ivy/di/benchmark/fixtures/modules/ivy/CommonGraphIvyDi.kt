package ivy.di.benchmark.fixtures.modules.ivy

import ivy.di.Di
import ivy.di.Di.bind
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import ivy.di.benchmark.fixtures.*

object CommonGraphIvyDi : Di.Module {
  override fun init() = Di.appScope {
    autoWire(::RealDispatchersProvider)
    bind<DispatchersProvider, RealDispatchersProvider>()
    autoWire(::LoggerImpl)
    bind<Logger, LoggerImpl>()
    autoWireSingleton(::Json)
    autoWire(::KotlinXSerialization)
    bind<Serialization, KotlinXSerialization>()
    autoWireSingleton(::HttpClient)
  }
}