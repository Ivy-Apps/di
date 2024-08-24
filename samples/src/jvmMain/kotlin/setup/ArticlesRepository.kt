package setup

interface ArticlesRepository

class CombinedArticlesRepository(
    private val localSource: ArticlesLocalDataSource,
    private val remoteSource: ArticlesRemoteDataSource,
)

class OfflineArticlesRepository(
    private val localSource: ArticlesLocalDataSource,
)

class RemoteArticlesRepository(
    private val remoteSource: ArticlesRemoteDataSource,
)