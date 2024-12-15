@file:Suppress("unused")

package ivy.di.benchmark.fixtures

class Context

class LocalStorage

class SessionManager(val localStorage: LocalStorage, val logger: Logger)

class Backstack(val initialRoute: String)
class Navigation(
  val backstack: Backstack,
  val logger: Logger,
  val context: Context,
)

interface ArticlesDataSource
class RemoteArticlesDataSource(
  val httpClient: Lazy<HttpClient>,
  val sessionManger: SessionManager
) : ArticlesDataSource

interface ArticlesRepository
class ArticlesRepositoryImpl(
  val dataSource: ArticlesDataSource,
  val logger: Logger,
) : ArticlesRepository

class ArticlesUseCase(
  val dispatchers: DispatchersProvider,
  val articlesRepo: ArticlesRepository,
  val logger: Logger,
)

class AuthorDataSource(val httpClient: HttpClient)
class AuthorRepository(val dataSource: AuthorDataSource)

class ArticlesViewModel(
  val navigation: Navigation,
  val dispatchers: DispatchersProvider,
  val articlesUseCase: ArticlesUseCase,
  val authorRepository: AuthorRepository,
  val logger: Logger,
)
class AuthorViewModel(
  val navigation: Navigation,
  val dispatchers: DispatchersProvider,
  val articlesUseCase: ArticlesUseCase,
  val authorRepository: AuthorRepository,
  val sessionManger: SessionManager,
  val context: Context,
)

class ContentScreen(
  val authorViewModel: AuthorViewModel,
  val articlesViewModel: ArticlesViewModel,
  val context: Context,
)

class AuthorScreen(
  val authorViewModel: AuthorViewModel,
  val context: Context,
)

class ArticlesScreen(
  val articlesViewModel: ArticlesViewModel,
  val context: Context,
)

class App(
  val context: Context,
  val navigation: Navigation,
  val contentScreen: ContentScreen,
  val authorScreen: AuthorScreen,
  val articlesScreen: ArticlesScreen,
  val logger: Logger,
)