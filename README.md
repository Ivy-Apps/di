[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Maven Central Version](https://img.shields.io/maven-central/v/com.ivy-apps/di)](https://central.sonatype.com/artifact/com.ivy-apps/di)

# Ivy DI

A simple, lightweight, and efficient runtime Dependency Injection (DI) container for Kotlin Multiplatform.
Ivy DI is a small dependency injection library with an intuitive API and limited features.

In a nutshell, you first register dependency factory functions in the container **Di.register { T() }** and then get instances via **Di.get\<T>()**.

### Features

- [Lightweight]()
- [One-line setup]()
- [Easy to use?]()
- [Auto-wiring]()
- [Singletons]()
- [Bindings]()
- [Qualifiers]()
- [Modules]()
- [Scopes]()
- [Lazy intitliazation]()

## Usage

### 0. Setup

```gradle
implementation("com.ivy-apps:di:0.0.0")
```

That's all you need! Replace "0.0.0" with [![Maven Central Version](https://img.shields.io/maven-central/v/com.ivy-apps/di)](https://central.sonatype.com/artifact/com.ivy-apps/di).

### 1. Register a dependency

```kotlin
class A
class B(val a: A)

Di.appScope {
  register { A() }
  register { B(a = Di.get() }
}
```

Instances of `A` and `B` won't be created until the dependencies are requested.

### 2. Get dependency instance

```kotlin
// instances of B and its dependencies will be created
Di.get<B>() // isntance 1 of B
Di.get<B>() // instance 2 of B
```

Each call to `Di.get()` creates a new instance for non-singleton dependencies.

### 3. Singleton dependencies

```kotlin
class Counter(var value = 0) {
  init { println("Counter created.")
}
Di.appScope {
  singleton { Counter() }
}

println(Di.get<Counter>().x) // Counter created. 0
Di.get<Counter>.x = 42
println(Di.get<Counter>()) // 42
```

Singleton dependencies will have only one **single instance** that will be created on the first `Di.get()` call.

### 4. Auto-wiring

```kotlin
class A
class B(val a: A)
class C(val a: A, val b: B)

Di.appScope {
  autoWireSingleton(::A)
  autoWire(::B)
  autoWire(::C)
}
```

To avoid repetitive code like `register { C(Di.get(), Di.get()) }` it's recommended to use auto-wiring.

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

To bind a specific implementation to an interface (or an abstract class) use `binds<Interface, Impl>()`. Note: `Impl` must be registered in the decency graph.

### 6. Named dependencies

TBD

### 7. Modules

TBD

## Advanced Usage

### 1. Scopes

### 2. Multibindings 🚧

Currently not supported, investigating this use-case and whether we can support it nicely.

### 3. Lazy initialization

By default, all instances in Ivy DI are lazily initialized only after `Di.get()` is called.
However, there are cases where you might want to postpone the initialization further.
You can do that by wrapping your dependency in `Lazy<T>` and using `Di.getLazy()` instead.

```kotlin
class ArticlesDataSource(val client: Lazy<HttpClient>) {
  suspend fun fetchLatest(): List<Article> = client.value.get("url)
}
class ArticlesRepository(val source: ArticlesDataSource)

Di.appScope {
  singleton { HttpClient(CIO) }
  register {
    // autoWire won't work because you need to explicitly call Di.getLazy() instead of Di.get()
    ArticlesDatSource(Di.getLazy())
  }
  autoWire(::ArticlesRepository)
}
```

The instance of `HttpClient` will be created only after the `ArticlesDataSource#fetchLatest()` function is called.

## ⚠️ Limitations

### Generics aren't supported

To avoid performance and compatibility problems we limit reflection to the bare minimum. 
Ivy DI uses only `KClass<*>` which unfortunately doesn't make a difference between the generic type of a class.

For example, if you register a factory for `Container<A>` and `Container<B>`, KClass will both treat them as just `Container`.
As an implication, only the factory for `Container<B>` will be registered.

### Maintenance

The library will be maintained as long as Ivy Apps Ltd has an interest in using it.
Given that Ivy DI currently has no community, the project may be abandoned in the future.
