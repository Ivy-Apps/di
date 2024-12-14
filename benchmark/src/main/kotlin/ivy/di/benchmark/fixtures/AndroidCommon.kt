@file:Suppress("unused")

package ivy.di.benchmark.fixtures

interface DispatchersProvider
class AndroidDispatchersProvider : DispatchersProvider

class HttpClient
class LocalStorage

class SessionManager(val localStorage: LocalStorage)

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
  val dispatchers: DispatchersProvider,
  val articlesUseCase: ArticlesUseCase,
  val authorRepository: AuthorRepository,
)