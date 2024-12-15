package ivy.di.benchmark.fixtures.modules.koin

import ivy.di.benchmark.fixtures.AppAppHolder
import ivy.di.benchmark.fixtures.AppHolder
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ComplexGraphKoin = module {
  singleOf(::AppHolder)
  factoryOf(::AppAppHolder)
}