package ivy.di.benchmark.fixtures.modules.ivy

import ivy.di.Di
import ivy.di.Di.bind
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import ivy.di.benchmark.fixtures.*

object BeGraphIvyDi : Di.Module {
  override fun init() = Di.appScope {
    autoWire(::AwsUrlProvider)
    bind<UrlProvider, AwsUrlProvider>()

    autoWireSingleton(::RealEnvironment)
    bind<Environment, RealEnvironment>()

    autoWire(::ServerConfig)
    autoWireSingleton(::KtorApp)
    autoWireSingleton(::RateLimiter)

    autoWireSingleton(::Database)

    autoWire(::AuthRepository)
    autoWire(::GoogleLoginUseCase)
    autoWire(::AuthService)
    autoWire(::AuthApi)

    autoWire(::CarsRepository)
    autoWire(::CarsService)
    autoWire(::CarsApi)

    autoWire(::Apis)
    autoWire(::ServerApp)
  }
}