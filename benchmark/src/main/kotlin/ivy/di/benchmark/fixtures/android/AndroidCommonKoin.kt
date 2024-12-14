package ivy.di.benchmark.fixtures.android

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val AndroidCommonModuleKoin = module {
  singleOf(::Context)

  factoryOf(::AndroidDispatchersProvider) { bind<DispatchersProvider>() }

  factoryOf(::AndroidLogger) { bind<Logger>() }

  single { HttpClient() }
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
  singleOf(::AppHolder)
  factoryOf(::AppAppHolder)
}