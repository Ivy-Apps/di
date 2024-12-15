package ivy.di.benchmark.fixtures


interface DispatchersProvider
class RealDispatchersProvider : DispatchersProvider

interface Logger
class LoggerImpl : Logger

interface Serialization
class Json
class KotlinXSerialization(val json: Json) : Serialization

class HttpClient(
  val serialization: Serialization,
  val logger: Logger,
  val dispatchersProvider: DispatchersProvider,
)