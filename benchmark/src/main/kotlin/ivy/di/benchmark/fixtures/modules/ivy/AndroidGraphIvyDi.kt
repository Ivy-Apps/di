package ivy.di.benchmark.fixtures.modules.ivy

import ivy.di.Di
import ivy.di.Di.bind
import ivy.di.Di.register
import ivy.di.Di.singleton
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import ivy.di.benchmark.fixtures.*

object AndroidGraphIvyDi : Di.Module {
  override fun init() = Di.appScope {
    autoWireSingleton(::Context)

    autoWire(::RealDispatchersProvider)
    bind<DispatchersProvider, RealDispatchersProvider>()

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
  }
}