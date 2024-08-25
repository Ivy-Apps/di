[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Maven Central Version](https://img.shields.io/maven-central/v/com.ivy-apps/di)](https://central.sonatype.com/artifact/com.ivy-apps/di)

# Ivy DI

A simple, lightweight and efficient Dependency Injection (DI) container for Kotlin Multiplatform.

**Instalation:**
```gradle
implementation("com.ivy-apps:di:0.0.0")
```

## Usage

Ivy DI implements a runtime DI container where you `Di.register { A() }` factory methods and `Di.get<A>()` dependency instances.
Each call to `Di.get()` creates a new instance. If you want to have the same instance use `Di.singleton { A() }`.

### 1. Register a dependency

```kotlin
class A
class B(val a: A)

Di.appScope {
  register { A() }
  register { B(a = Di.get() }
}
```

Instances of `A` and `B` won't be created until these dependencies are requested.

### 2. Get dependency instance

```kotlin
// instances of B and its dependencies will be created
Di.get<B>() // isntance 1 of B
Di.get<B>() // instance 2 of B
```

Each call to `Di.get()` creates a new instance for non-singleton dependencies.

## Examples

**Basic usage example:**
```kotlin
class RemoteDataSource(val client: HttpClient)
interface LocalPersistence
class DataStoreLocalPersistence : LocalPersistence
class Repository(val remoteSource: RemoteDataSource, val localSource: LocalPersistence)

fun main() = runBlocking {
    Di.appScope {
        singleton { HttClient(CIO) }
        register { RemoteDataSource(Di.get()) }
        register<LocalPersistence> { DataStoreLocalPersistence() }
        register { Repository(Di.get(), Di.get()) }
    }

    val repo = Di.get<Repository>()
}

```

### Modules

Modules help to consolidate DI logic together and reuse it when initing new Ivy DI graphs.

**DI modules example:**
```kotlin
object SharedModule : Di.Module {
    override fun init() = Di.appScope {
        singleton<Platform> { platform() }
        singletonJson()
        singleTonKtorClient()
        register<ServerUrlProvider> { HerokuServerUrlProvider() }
        register { LessonDataSource(Di.get(), Di.get()) }
        register { TopicsDataSource(Di.get(), Di.get()) }
        register { CoursesDataSource(Di.get(), Di.get()) }
        register { LottieAnimationLoader(Di.get()) }
    }
}
object DataModule : Di.Module {
    override fun init() = Di.appScope {
        register { Database() }
        register { LessonContentDataSource(Di.get(), Di.get()) }
        register { LessonsRepository(Di.get()) }
        register { CoursesRepository() }
        register { TopicsRepository() }
    }
}
fun main(args: Array<String>) {
    Di.init(
        modules = setOf(
            SharedModule,
            DataModule,
            StartupModule(args),
        )
    )
    val server = Di.get<LearnServer>()

    val port = System.getenv("PORT")?.toInt() ?: 8081
    println("Starting server on port $port...")
    embeddedServer(
        Netty,
        port = port,
        host = "0.0.0.0",
        module = {
            server.init(this).onLeft {
                throw ServerInitializationException(reason = it)
            }
        },
    ).start(wait = true)
}
```

### Scopes

Dependencies in Ivy DI are scope-based, allowing efficient memory management of the dependency graph.
It comes with built-in **AppScope** and **FeatureScope** but you can easily create your own ones using `Di.scope(Scope("my-scope")`.

**Scopes example:**
```kotlin
abstract class Screen {
    private lateinit var job: CompletableJob
    protected lateinit var screenScope: CoroutineScope

    protected abstract fun Di.Scope.onInit()

    /**
     * Called when the screen is added to the backstack.
     */
    fun initialize() {
        job = SupervisorJob()
        screenScope = CoroutineScope(Dispatchers.Main + job)
        FeatureScope.onInit()
    }

    /**
     * Called when the screen is no longer in the backstack.
     */
    fun destroy() {
        job.cancel()
        Di.clearInstances(FeatureScope) // destroy all singleton instances
    }
    
    @Composable
    abstract fun Content()
}

class HomeScreen : Screen() {
    override fun Di.Scope.onInit() {
        register { HomeViewModel(Di.get(), Di.get()) }
    }

    @Composable
    override fun Content() {
        val viewModel = remember { Di.get<HomeViewModel>() }
        HomeContent(
            viewState = viewModel.viewState(),
            onEvent = viewModel::onEvent,
        )
    }
}
```

**Custom scope example:**
```kotlin
class TotalSum {
    private var sum = 0
    fun add(value: Int) {
        sum += value
    }
    fun get() = sum
}
class IterationContext(val index: Int)
class IterationHandler(
    private val iteration: IterationContext,
    private val totalSum: TotalSum,
) {
    fun process() {
        totalSum.add(iteration.index)
    }
}

val IterationScope = Di.newScope("iteration")

fun main() {
    Di.appScope {
        // Dependencies here live as long as the app lives
        singleton { TotalSum() }
    }

    for (i in 0..100) {
        Di.scope(IterationScope) {
            // Dependencies here live only for one iteration
            singleton { IterationContext(index = i) }
            register { IterationHandler(Di.get(), Di.get()) }
        }
        Di.get<IterationHandler>().process()
        Di.clearInstances(IterationScope) // remove all instances for this iteration
    }

    println(Di.get<TotalSum>().get())
}
```
