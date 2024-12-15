package ivy.di.benchmark.fixtures.modules.koin

import ivy.di.benchmark.fixtures.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val BeGraphKoin = module {
  factoryOf(::AwsUrlProvider) { bind<UrlProvider>() }
  singleOf(::RealEnvironment) { bind<Environment>() }

  factoryOf(::ServerConfig)
  singleOf(::KtorApp)
  singleOf(::RateLimiter)

  singleOf(::Database)

  factoryOf(::AuthRepository)
  factoryOf(::GoogleLoginUseCase)
  factoryOf(::AuthService)
  factoryOf(::AuthApi)

  factoryOf(::CarsRepository)
  factoryOf(::CarsService)
  factoryOf(::CarsApi)

  factoryOf(::Apis)
  factoryOf(::ServerApp)
}