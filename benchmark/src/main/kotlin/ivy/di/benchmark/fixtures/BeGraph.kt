package ivy.di.benchmark.fixtures

class AwsUrlProvider : UrlProvider
interface UrlProvider

interface Environment
class RealEnvironment : Environment

class ServerConfig(
  val urlProvider: UrlProvider
)

class KtorApp
class RateLimiter(val ktorApp: KtorApp)

class Database(
  val config: ServerConfig,
  val logger: Logger,
)

class AuthRepository(val database: Database)
class GoogleLoginUseCase(val database: Database, val httpClient: HttpClient)
class AuthService(
  val repository: AuthRepository,
  val googleLoginUseCase: GoogleLoginUseCase,
)
class AuthApi(
  val authService: AuthService,
  val logger: Logger,
  val rateLimiter: RateLimiter
)

class CarsRepository(
  val database: Database,
  val logger: Logger,
)
class CarsService(
  val authService: AuthService,
  val repository: CarsRepository,
)
class CarsApi(val service: CarsService)

class Apis(
  val rateLimiter: RateLimiter,
  val authApi: AuthApi,
  val carsApi: CarsApi,
)
class ServerApp(
  val serverConfig: ServerConfig,
  val ktorApp: KtorApp,
  val logger: Logger,
  val apis: Apis,
)
