package ivy.di.benchmark.fixtures.android

import ivy.di.Di
import ivy.di.Di.bind
import ivy.di.Di.register
import ivy.di.Di.singleton
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton

object AndroidCommonModuleIvyDi : Di.Module {
  override fun init() = Di.appScope {
    autoWireSingleton(::Context)

    autoWire(::AndroidDispatchersProvider)
    bind<DispatchersProvider, AndroidDispatchersProvider>()

    autoWire(::AndroidLogger)
    bind<Logger, AndroidLogger>()

    singleton { HttpClient() }
    autoWire(::LocalStorage)

    singleton { Backstack("/") }
    autoWireSingleton(::Navigation)

    autoWireSingleton(::SessionManager)

    register<ArticlesDataSource> {
      RemoteArticlesDataSource(
        httpClient = Di.getLazy(),
        sessionManger = Di.get()
      )
    }

    autoWire(::ArticlesRepositoryImpl)
    bind<ArticlesRepository, ArticlesRepositoryImpl>()

    autoWire(::ArticlesUseCase)

    autoWire(::AuthorDataSource)
    autoWire(::AuthorRepository)

    autoWire(::ArticlesViewModel)
    autoWire(::AuthorViewModel)

    autoWire(::ContentScreen)
    autoWire(::ArticlesScreen)
    autoWire(::AuthorScreen)

    autoWire(::App)
    autoWireSingleton(::AppHolder)
    autoWire(::AppAppHolder)
  }
}