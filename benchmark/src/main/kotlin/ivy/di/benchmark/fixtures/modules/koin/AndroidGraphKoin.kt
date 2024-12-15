package ivy.di.benchmark.fixtures.modules.koin

import ivy.di.benchmark.fixtures.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val AndroidGraphKoin = module {
  singleOf(::Context)

  factoryOf(::RealDispatchersProvider) { bind<DispatchersProvider>() }

  factoryOf(::LocalStorage)

  single { Backstack("/") }
  singleOf(::Navigation)

  singleOf(::SessionManager)

  factory<ArticlesDataSource> {
    RemoteArticlesDataSource(
      httpClient = lazy { get() },
      sessionManger = get()
    )
  }

  singleOf(::ArticlesRepositoryImpl) { bind<ArticlesRepository>() }

  singleOf(::ArticlesUseCase)

  factoryOf(::AuthorDataSource)
  factoryOf(::AuthorRepository)

  factoryOf(::ArticlesViewModel)
  factoryOf(::AuthorViewModel)

  singleOf(::ContentScreen)
  singleOf(::ArticlesScreen)
  singleOf(::AuthorScreen)

  factoryOf(::App)
}