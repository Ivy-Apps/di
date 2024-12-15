package ivy.di.benchmark.fixtures.modules.koin

import ivy.di.benchmark.fixtures.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val CommonGraphKoin = module {
  factoryOf(::RealDispatchersProvider) { bind<DispatchersProvider>() }
  factoryOf(::LoggerImpl) { bind<Logger>() }
  singleOf(::Json)
  factoryOf(::KotlinXSerialization) { bind<Serialization>() }
  singleOf(::HttpClient)
}