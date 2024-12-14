package ivy.di.benchmark.fixtures.android

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val AndroidCommonModuleKoin = module {
  factoryOf(::AndroidDispatchersProvider) { bind<DispatchersProvider>() }

  single { HttpClient() }
  factoryOf(::LocalStorage)

  singleOf(::SessionManager)

  factory<ArticlesDataSource> {
    RemoteArticlesDataSource(
      httpClient = get(),
      sessionManger = get()
    )
  }

  singleOf(::ArticlesRepositoryImpl) { bind<ArticlesRepository>() }

  single { ArticlesUseCase(get(), get()) }

  factoryOf(::AuthorDataSource)
  factoryOf(::AuthorRepository)

  factoryOf(::ArticlesViewModel)
  factoryOf(::AuthorViewModel)
}