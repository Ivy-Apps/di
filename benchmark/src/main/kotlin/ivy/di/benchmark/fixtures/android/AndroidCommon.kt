@file:Suppress("unused")

package ivy.di.benchmark.fixtures.android

interface DispatchersProvider
class AndroidDispatchersProvider : DispatchersProvider

class HttpClient
class LocalStorage

class SessionManager(val localStorage: LocalStorage)

class Backstack(val initialRoute: String)
class Navigation(val backstack: Backstack)

interface ArticlesDataSource
class RemoteArticlesDataSource(
  val httpClient: Lazy<HttpClient>,
  val sessionManger: SessionManager
) : ArticlesDataSource

interface ArticlesRepository
class ArticlesRepositoryImpl(val dataSource: ArticlesDataSource) : ArticlesRepository

class ArticlesUseCase(
  val dispatchers: DispatchersProvider,
  val articlesRepo: ArticlesRepository,
)

class AuthorDataSource(val httpClient: HttpClient)
class AuthorRepository(val dataSource: AuthorDataSource)

class ArticlesViewModel(
  val navigation: Navigation,
  val dispatchers: DispatchersProvider,
  val articlesUseCase: ArticlesUseCase,
  val authorRepository: AuthorRepository,
)
class AuthorViewModel(
  val navigation: Navigation,
  val dispatchers: DispatchersProvider,
  val articlesUseCase: ArticlesUseCase,
  val authorRepository: AuthorRepository,
  val sessionManger: SessionManager,
)

class ContentScreen(
  val authorViewModel: AuthorViewModel,
  val articlesViewModel: ArticlesViewModel,
)

class AuthorScreen(
  val authorViewModel: AuthorViewModel,
)

class ArticlesScreen(
  val articlesViewModel: ArticlesViewModel,
)

class App(
  val navigation: Navigation,
  val contentScreen: ContentScreen,
  val authorScreen: AuthorScreen,
  val articlesScreen: ArticlesScreen,
)

class AppHolder(val app: App)