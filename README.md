[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Maven Central Version](https://img.shields.io/maven-central/v/com.ivy-apps/di)](https://central.sonatype.com/artifact/com.ivy-apps/di)

# Ivy DI

A simple and lightweight Dependency Injection (DI) container for Kotlin Multiplatform.

**Dependency:**
```gradle
implementation("com.ivy-apps:di:0.0.0")
```

## Usage

Ivy DI implements a runtime DI container where you `Di.register {...}` factory methods and `Di.get()` dependency instances.
Each call to `Di.get()` creates a new instance. If you want to have the same instance use `Di.singleton {...}`.

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
