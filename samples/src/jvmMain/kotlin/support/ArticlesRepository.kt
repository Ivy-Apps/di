package support

interface ArticlesRepository

class CombinedArticlesRepository(
    private val localSource: ArticlesLocalDataSource,
    private val remoteSource: ArticlesRemoteDataSource,
) : ArticlesRepository

class OfflineArticlesRepository(
    private val localSource: ArticlesLocalDataSource,
) : ArticlesRepository

class RemoteArticlesRepository(
    private val remoteSource: ArticlesRemoteDataSource,
) : ArticlesRepository