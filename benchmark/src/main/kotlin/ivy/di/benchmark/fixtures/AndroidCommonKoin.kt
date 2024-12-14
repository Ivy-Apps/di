package ivy.di.benchmark.fixtures

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val AndroidCommonModuleKoin = module {
  // DispatchersProvider
  singleOf(::AndroidDispatchersProvider) { bind<DispatchersProvider>() }

  // Basic dependencies
  single { HttpClient() }
  single { LocalStorage() }

  // SessionManager
  single { SessionManager(get()) }

  // ArticlesDataSource
  single<ArticlesDataSource> {
    RemoteArticlesDataSource(
      httpClient = get(), // Lazy resolution in Koin happens by default
      sessionManger = get()
    )
  }

  // ArticlesRepository
  singleOf(::ArticlesRepositoryImpl) { bind<ArticlesRepository>() }

  // ArticlesUseCase
  single { ArticlesUseCase(get(), get()) }

  // Author-related dependencies
  singleOf(::AuthorDataSource)
  singleOf(::AuthorRepository)

  // ArticlesViewModel
  single { ArticlesViewModel(get(), get(), get()) }
}