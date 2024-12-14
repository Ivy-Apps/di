[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Maven Central Version](https://img.shields.io/maven-central/v/com.ivy-apps/di)](https://central.sonatype.com/artifact/com.ivy-apps/di)

# Ivy DI ⚡

A simple and lightweight runtime Dependency Injection (DI) container for Kotlin Multiplatform.
Ivy DI is a small dependency injection library with an intuitive API and limited features.

In a nutshell, you first register dependency factory functions in the container `Di.register { SomeClass() }` and then
get instances via `Di.get<SomeClass>()`. It also supports auto-wiring via `autoWire(::SomeClass)` that does it
automatically for you.

```kotlin
interface ArticlesDataSource
class RemoteArticlesDataSource(
  val client: HttpClient,
  val baseUrl: BaseUrlProvider
) : ArticlesDataSource
class ArticlesRepository(val source: ArticlesDataSource)

object AppModule : Di.Module {
  override fun init() = Di.appScope {
    register { BaseUrlProvider("https://ivy-apps.com") }
    singleton { HttpClient(CIO) }
    autoWire(::RemoteArticlesDataSource)
    bind<ArticlesDataSource, RemoteArticlesDataSource>()
    autoWireSingleton(::ArticlesRepository)
  }
}

fun main() {
  Di.init(AppModule) // activates the AppModule (i.e. executes its code)
  val repo = Di.get<ArticlesRepository>() // ArticlesRepository instance created
}
```

### Features

- [Lightweight & Efficient](di/src/commonMain/kotlin/ivy/di/DiContainer.kt)
- [One-line setup](https://github.com/Ivy-Apps/di?tab=readme-ov-file#0-setup)
- [Simple and easy to use](https://github.com/Ivy-Apps/di?tab=readme-ov-file#1-register-a-dependency)
- [Auto-wiring](https://github.com/Ivy-Apps/di?tab=readme-ov-file#4-auto-wiring)
- [Singletons](https://github.com/Ivy-Apps/di?tab=readme-ov-file#3-singleton-dependencies)
- [Bindings](https://github.com/Ivy-Apps/di?tab=readme-ov-file#5-bindings)
- [Qualifiers](https://github.com/Ivy-Apps/di?tab=readme-ov-file#6-named-dependencies-qualifiers)
- [Modules](https://github.com/Ivy-Apps/di?tab=readme-ov-file#7-modules)
- [Scopes](https://github.com/Ivy-Apps/di?tab=readme-ov-file#1-scopes)
- [Lazy intitliazation](https://github.com/Ivy-Apps/di?tab=readme-ov-file#3-lazy-initialization)
- [KDoc on every method](di/src/commonMain/kotlin/ivy/di/DiContainer.kt)

> [!WARNING]
> Before deciding whether to use it, make sure to check the [Limitations](https://github.com/Ivy-Apps/di?tab=readme-ov-file#%EF%B8%8F-limitations).

## Usage

### 0. Setup

You can find Ivy DI in [our Ivy DI Maven Central repository](https://central.sonatype.com/artifact/com.ivy-apps/di).

Replace `?.?.?`
with the numbers from: [![Maven Central Version](https://img.shields.io/maven-central/v/com.ivy-apps/di)](https://central.sonatype.com/artifact/com.ivy-apps/di)

**Gradle (Kotlin)**

```gradle
implementation("com.ivy-apps:di:?.?.?")
```

or

**Version Catalog**

```toml
[libraries]
ivyApps-di = { module = "com.ivy-apps:di", version = "?.?.?" }
```

```gradle
implementation(libs.ivyApps.di)
```

That's all you need! Now let's start using Ivy DI ✨️

### 1. Register a dependency

```kotlin
class A
class B(val a: A)

Di.appScope {
  register { A() }
  register { B(a = Di.get()) }
}
```

Instances of `A` and `B` won't be created until the dependencies are requested in the code (lazy creation).

### 2. Get dependency instance

```kotlin
// instances of B and its dependencies will be created
Di.get<B>() // instance 1 of B
Di.get<B>() // instance 2 of B
```

Each call to `Di.get()` creates a **new** instance for non-singleton dependencies.

### 3. Singleton dependencies

```kotlin
class Counter(var x: Int = 0) {
  init {
    print("Counter created. ")
  }
}

Di.appScope {
  singleton {
    Counter() // instance won't be created here 
  }
}

println(Di.get<Counter>().x) // Counter created. 0
Di.get<Counter>().x++
println(Di.get<Counter>().x) // 1
```

Singleton dependencies will have only one **single instance**
that will be created on the first `Di.get()` call.

To clear singleton instance and free memory use `Di.clear(scope)`.
For example, to clear the counter - `Di.clear(AppScope)`.

### 4. Auto-wiring

```kotlin
class A
class B(val a: A)
class C(val a: A, val b: B)
class D(val a: A, val b: B, val c: C)

Di.appScope {
  autoWire(::A)
  // equivalent to register { A() }
  autoWireSingleton(::B) // for singletons
  // equivalent to singleton { B(a = Di.get()) }
  autoWire(::C)
  // equivalent to register { C(Di.get(), Di.get()) }
  autoWire(::D)
  // equivalent to register { D(Di.get(), Di.get(), Di.get()) }
}
Di.get<D>() // instance of D created
```

To avoid repetitive code like `register { D(Di.get(), Di.get(), Di.get()) }`
it's recommended to use auto-wiring.

> [!TIP]
> **Always auto-wire**
> 
> When possible always use auto-wiring and fallback to
> register/singleton only when absolutely necessary.
> This way you won't have to modify the registered factories
> when you change the constructor of the dependency that's being injected.

### 5. Bindings

```kotlin
interface Platform
class AndroidPlatform : Platform

Di.appScope {
  autoWire(::AndroidPlatform)
  bind<Platform, AndroidPlatform>()
  // equivalent to:
  // register<Platform> { AndroidPlatform() }
}
Di.get<Platform>() // AndroidPlatform instance
```

To bind a specific implementation to an interface (or an abstract class) use `bind<Interface, Impl>()`. Note: `Impl`
must be registered in the dependency graph.

### 6. Named dependencies (qualifiers)

```kotlin
interface TimeFormatter
class H24TimeFormatter : TimeFormatter
class AmPmTimeFormatter : TimeFormatter

Di.appScope {
  autoWire(::H24TimeFormatter)
  autoWire(::AmPmTimeFormatter)
  bind<TimeFormatter, H24TimeFormatter>() // default
  bind<TimeFormatter, AmPmTimeFormatter>(named = "am-pm")
}

Di.get<TimeFormatter>() // H24TimeFormatter
Di.get<TimeFormatter>(named = "am-pm") // AmPmTimeFormatter
```

Sometimes we need to have different instances of the same type.
To achieve this in Ivy DI, we can set qualifiers using `named = "something"` (you're not limited only to strings because
`named: Any`).

> [!IMPORTANT]
> Your "named" qualifiers must support equality checks (hashCode + equals).

### 7. Modules

```kotlin
object DataModule : Di.Module {
  override fun init() = Di.appScope {
    singleton { HttpClient(CIO) }
    register { Json() }
    autoWire(::LoginService)
    autoWireSingleton(::AnalyticsService)
  }
}

object DomainModel : Di.Module {
  override fun init() = Di.app {
    autoWire(::LoginUseCaseImpl)
    bind<LoginUseCase, LoginUseCaseImpl>()
    autoWireSingleton(::SessionManager)
  }
}

Di.init(
  // Registers the following modules in the DI container
  DataModule,
  DomainModule
)
Di.get<LoginUseCase>() // instance of LoginUseCaseImpl created
```

To encapsulate and re-use DI logic you can create `Di.Module`.
To activate the DI module you need to call `Di.init(MyModule)`.

> [!TIP]
> On each  architecture layer or feature layer, you can create a `di` package with DI modules inside. Then in your
> application root or feature entrypoint,
> call `Di.init(ModuleA, ModuleB, ...)` with all modules that you need to use.

## Advanced Usage

### 1. Scopes

Ivy DI supports grouping your dependencies into scopes. This way you can manage their lifecycle
and free resources when they are no longer needed. **AppScope** and **FeatureScope**
are built-in, but you can easily define your own scopes using `Di.newScope("my-scope")`.

```kotlin
data class UserInfo(val id: String, val name: String)

val UserScope = Di.newScope("user")
fun Di.userScope(block: Di.Scope.() -> Unit) = Di.inScope(UserScope, block) // helper function (optional)

suspend fun login() {
  val userInfo = loginInternally() // UserInfo("1", "John")
  Di.userScope {
    // Register dependencies for the lifecycle of a user
    singleton { userInfo }
  }
}

// Note: This function must be called only for logged-in users,
// otherwise Di.get() will throw an exception.
suspend fun dashboard() {
  // Use user related dependencies
  val userInfo = Di.get<UserInfo>()
  println("Hello, ${userInfo.name}") // "Hello, John"
}

suspend fun logout() {
  logoutInternally()
  // Frees all dependencies in UserScope
  Di.clear(UserScope) // UserInfo("1", "John") gets cleared
}
```

Scopes are also extremely useful for defining multiple dependencies of the same type
and picking the most appropriate one based on the scope using **affinity**.

```kotlin
data class Screen(val name: String)
Di.appScope {
  register<String> { "Hello from app!" }
  autoWire(::Screen)
}
Di.featureScope {
  register<String> { "Hello from feature!" }
  autoWire(::Screen)
}

Di.get<String>(affinity = AppScope) // "Hello from app!"
Di.get<Screen>(affinity = AppScope) // Screen(name="Hello from app!")
Di.get<String>(affinity = FeatureScope) // "Hello from feature!"
Di.get<Screen>(affinity = FeatureScope) // Screen(name="Hello from feature!")
```

> [!NOTE]
> Auto-wiring automatically sets the affinity to the scope from which it's called.

### 2. Multi-bindings 🚧

Currently not supported, investigating this use-case and whether we can support it nicely.

> [!WARNING]
> So far, we haven't found a nice and efficient solution.
> Currently, multi-bindings are not on the roadmap. The main blocker is the limited KClass<*> support for generics.

### 3. Lazy initialization

By default, all instances in Ivy DI are lazily initialized only after `Di.get()` is called.
However, there are cases where you might want to postpone the initialization even further.
You can do that by wrapping your dependency in `Lazy<T>` and using `Di.getLazy<T>()` instead.

```kotlin
class ArticlesDataSource(val client: Lazy<HttpClient>) {
  suspend fun fetchLatest(): List<Article> = client.value.get("url") // .value gets an instance of the HttpClient
}
class ArticlesRepository(val source: ArticlesDataSource) {
  suspend fun fetchLatest(): List<Article> = source.fetchLatest()
}

Di.appScope {
  singleton { HttpClient(CIO) }
  register {
    // autoWire won't work because you need to explicitly call Di.getLazy() instead of Di.get()
    ArticlesDataSource(Di.getLazy())
  }
  autoWire(::ArticlesRepository)
}
val repo = Di.get<ArticlesRepository>() // HttpClient instance not created
repo.fetchLatest() // HttpClient instance created
```

The instance of `HttpClient` will be created only after the `ArticlesDataSource#fetchLatest()` method is called.

## ⚠️ Limitations

### Generics aren't fully supported

To avoid performance and compatibility problems we limit reflection to the bare minimum.
Ivy DI uses only `KClass<*>` which unfortunately doesn't make a difference between the generic type of the class.

**Problematic code:**

```kotlin
class Container<T>(val value: T)

Di.appScope {
  register {
    // perceived as Container<*>, information for the Int generic type is lost
    Container<Int>(42)
  }
  register { Container<String>("hello") } // will override the factory for Container<Int>
}

val intContainer = Di.get<Container<Int>>() // Container<String>("hello") instance created.
intContainer.value // "hello" (String) even tough we requested Container<Int>!
val x = intContainer.value + 1 // ClassCastException: class java.lang.String cannot be cast to class java.lang.Number
Di.get<Container<String>>() // Container<String>("hello") instance created
```

This is very weird and has some nasty implications that break type-safety.

To overcome this `KClass` limitation, the workaround is to wrap your generics in value classes.

**Workaround:**

```kotlin
class Container<T>(val value: T)

@JvmInline
value class IntContainer(val value: Container<Int>)

@JvmInline
value class StringContainer(val value: Container<String>)

Di.appScope {
  register { IntContainer(Container(42)) }
  register { StringContainer(Container("hello")) }
}

Di.get<IntContainer>().value // Container<Int>(42) instance created
Di.get<StringContainer>().value // Container<String>("hello") instance created
```

The same applies for `List<T>`, `Set<T>`, `Map<K, V>` and any generic class.
The fix is to wrap it in some value class and register + inject the value class wrapper.

### Thread-safety

The Ivy DI APIs aren't synchronized and thread-safety is a **responsibility of the API user**. We made this decision to
keep the DI container free of Java (or 3rd party) dependencies and prioritize efficiency. We recommend registering your
dependencies on the main thread.

### Maintenance

The library will be maintained as long as Ivy Apps Ltd has an interest in using it.
Given that Ivy DI currently has no community, the project may be abandoned in the future.

> **Disclaimer**
>
> _Ivy DI is provided "as is" under the [Apache 2.0 License](LICENSE),
without warranties of any kind, including but not limited to
fitness for a particular purpose or security.
Use it at your own risk. The authors are not liable for
any issues, defects, security vulnerabilities,
or damages arising from its use.
Maintenance, updates, and support are not guaranteed._