package ivy.di.benchmark.fixtures.modules.koin

import ivy.di.benchmark.fixtures.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ComplexGraphKoin = module {
  factoryOf(::Holder1)
  singleOf(::Holder2)
  factoryOf(::Holder3)
  singleOf(::Holder4)
  factoryOf(::Holder5)
  singleOf(::Holder6)
  factoryOf(::Holder7)
  singleOf(::Holder8)
  factoryOf(::Holder9)
  singleOf(::Holder10)

  factoryOf(::MultiHolderImpl) { bind<MultiHolder>() }
  factoryOf(::MultiHolderFinal)
}