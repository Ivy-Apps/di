package ivy.di

import ivy.di.Di.Scope
import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

typealias Factory = () -> Any

/**
 * Built-in [Di.Scope] for registering dependencies for the lifetime of the application.
 * __Note:__ you need to manually manage the lifecycle by clearing when no longer needed
 * via [Di.clear].
 */
val AppScope = Di.newScope("app")

/**
 * Same like [AppScope] but for the lifetime of a feature.
 */
val FeatureScope = Di.newScope("feature")

object Di {
  private val scopes = mutableSetOf<Scope>()

  @DiInternalApi
  val factories = mutableMapOf<DependencyKey, Factory>()

  @DiInternalApi
  val singletons = mutableSetOf<KClass<*>>()

  @DiInternalApi
  val singletonInstances = mutableMapOf<DependencyKey, Any>()

  /**
   * Initializes [modules] by calling their [Module.init] functions.
   * @param modules an array of modules to be initialized one by one
   */
  fun init(vararg modules: Module) {
    modules.forEach(Module::init)
  }

  /**
   * Scope used to register dependencies in [AppScope],
   * intended for the entire lifetime of the application.
   */
  fun appScope(block: Scope.() -> Unit) = AppScope.block()

  /**
   * Scope used to register dependencies in [FeatureScope]
   * intended for the lifetime of the feature for a feature.
   */
  fun featureScope(block: Scope.() -> Unit) = FeatureScope.block()

  /**
   * Registers a DI new scope.
   * @param name Unique identifier for the scope.
   * @return The newly created [Scope].
   */
  fun newScope(name: String): Scope = Scope(name).also(scopes::add)

  /**
   * Utility function for registering dependencies in a specific [scope].
   * @param scope the [Di.Scope] in which the dependencies will be registered
   */
  fun inScope(scope: Scope, block: Scope.() -> Unit) = scope.block()

  /**
   * Registers a factory for a dependency [T].
   * A new instance of [T] will be created only after the first call to [get].
   * Subsequent calls to [get] will create a new instance.
   * @param named An optional qualifier to distinguish between multiple dependencies of the same type.
   */
  inline fun <reified T : Any> Scope.register(
    named: Any? = null,
    noinline factory: () -> T,
  ) {
    factories[DependencyKey(this, T::class, named)] = factory
  }

  /**
   * Registers a singleton factory for a dependency [T].
   * A single instance of [T] will be created only after the first call to [get].
   * Subsequent calls to [get] will return the same instance.
   * @param named An optional qualifier to distinguish between multiple dependencies of the same type.
   */
  inline fun <reified T : Any> Scope.singleton(
    named: Any? = null,
    noinline factory: () -> T,
  ) {
    val classKey = T::class
    factories[DependencyKey(this, classKey, named)] = factory
    singletons.add(classKey)
  }

  /**
   * Binds an interface (or a base class) [Base] to an implementation [Impl].
   */
  inline fun <reified Base : Any, reified Impl : Base> Scope.bind(
    named: Any? = null,
  ) {
    register<Base> { get<Impl>(named = named) }
  }

  /**
   * The same as [get] but returns a [Lazy] instance.
   * @param named An optional qualifier to distinguish between multiple dependencies of the same type.
   * @param affinity preferred scope in which to look for the dependency first
   * @throws DependencyInjectionError if no factory for [T] with qualifier [named] is registered.
   */
  @Throws(DependencyInjectionError::class)
  inline fun <reified T : Any> getLazy(
    named: Any? = null,
    affinity: Scope? = null,
  ): Lazy<T> {
    val classKey = T::class
    // ensure that factory exists
    affinity?.factoryOrNull(classKey, named)
      ?: factoryOrThrow(classKey, named) // this will throw in case of null
    return lazy { get<T>(named, affinity) }
  }

  /**
   * Returns an instance of a dependency [T].
   * Each call to [get] will return a new instance using your registered factory.
   * If [T] is a [singleton], the same instance will be returned on subsequent calls.
   * @param named An optional qualifier to distinguish between multiple dependencies of the same type.
   * @param affinity preferred scope in which to look for the dependency first
   * @throws DependencyInjectionError if no factory for [T] with qualifier [named] is registered.
   */
  @Throws(DependencyInjectionError::class)
  inline fun <reified T : Any> get(
    named: Any? = null,
    affinity: Scope? = null,
  ): T {
    val classKey = T::class
    val (scope, factory) = affinity?.factoryOrNull(classKey, named)
      ?: factoryOrThrow(classKey, named)
    val depKey = DependencyKey(scope, classKey, named)
    return if (classKey in singletons) {
      if (depKey in singletonInstances) {
        // single instance already created
        singletonInstances[depKey] as T
      } else {
        // create a new instance for the singleton
        val instance = (factory() as T).also {
          singletonInstances[depKey] = it
        }
        instance
      }
    } else {
      // create a new instance
      factory() as T
    }
  }

  /**
   * Returns a factory for a dependency identified by [classKey] and [named].
   * @throws DependencyInjectionError if no factory is registered.
   */
  @DiInternalApi
  @Throws(DependencyInjectionError::class)
  fun factoryOrThrow(
    classKey: KClass<*>,
    named: Any?,
  ): Pair<Scope, Factory> = scopes
    .firstNotNullOfOrNull { scope ->
      scope.factoryOrNull(classKey, named)
    } ?: throw DependencyInjectionError(diErrorMsg(classKey, named))

  @DiInternalApi
  fun Scope.factoryOrNull(
    classKey: KClass<*>,
    named: Any?,
  ): Pair<Scope, Factory>? = scopedFactoryOrNull(this, classKey, named)

  @DiInternalApi
  fun scopedFactoryOrNull(
    scope: Scope,
    classKey: KClass<*>,
    named: Any?,
  ): Pair<Scope, () -> Any>? = factories[DependencyKey(scope, classKey, named)]
    ?.let { factory ->
      scope to factory
    }

  private fun diErrorMsg(classKey: KClass<*>, named: Any?): String = buildString {
    append("No factory")
    if (named != null) {
      append(" with qualifier named \"$named\"")
    }
    append(" found: ")
    append('[')
    append(classKey.toString())
    append(']')
    val dependencyId = buildString {
      append(classKey.toString())
      if (named != null) {
        append("(named=\"$named\")")
      }
    }
    append("\nDid you forget to register '$dependencyId' in Ivy DI?")
  }

  /**
   * Clears all instances in the given [Di.Scope].
   */
  fun clear(scope: Scope) {
    singletonInstances.keys.forEach { instanceKey ->
      if (instanceKey.scope == scope) {
        singletonInstances.remove(instanceKey)
      }
    }
  }

  /**
   * Resets the DI container by clearing all instances, singletons and factories.
   */
  fun reset() {
    singletonInstances.clear()
    factories.clear()
    singletons.clear()

    // Reset scopes to the default state
    scopes.clear()
    scopes.add(AppScope)
    scopes.add(FeatureScope)
  }

  /**
   * A key used to identify a dependency in the DI container.
   * @param scope The scope in which the dependency is registered.
   * @param klass The type of the dependency. Note: Generic types are not supported (Lazy<A> == Lazy<B> true).
   * @param qualifier An optional qualifier to distinguish between multiple dependencies of the same type.
   * _The qualifier must support hashCode and equals._
   */
  data class DependencyKey(
    val scope: Scope,
    val klass: KClass<*>,
    val qualifier: Any?,
  )

  /**
   * Scopes are used to group dependencies together and manage their lifecycle.
   *
   * __Note:__ A dependency (class) can be registered into multiple scopes and its factory
   * will be picked based on affinity or scopes registration order.
   *
   * ```kotlin
   * Di.appScope {
   *   register { "hello" }
   * }
   * Di.featureScope {
   *   register { "world" }
   * }
   *
   * Di.get<String>(affinity = AppScope) // "hello"
   * Di.get<String>(affinity = FeatureScope) // "world"
   * Di.get<String>() // not deterministic
   * ```
   *
   * An example for managing lifecycle using DI scopes:
   *
   * ```kotlin
   * data class UserInfo(val id: String, val name: String)
   *
   * val UserScope = Di.newScope("user")
   * fun Di.userScope(block: Di.Scope.() -> Unit) = Di.inScope(UserScope, block) // helper function (optional)
   *
   * suspend fun login() {
   *   val userInfo = loginInternally() // UserInfo("1", "John")
   *   Di.userScope {
   *     // Register dependencies for the lifecycle of a user
   *     singleton { userInfo }
   *   }
   * }
   *
   * // Note: This function must be called only for logged-in users,
   * // otherwise Di.get() will throw an exception.
   * suspend fun dashboard() {
   *   // Use user related dependencies
   *   val userInfo = Di.get<UserInfo>()
   *   println("Hello, ${userInfo.name}") // "Hello, John"
   * }
   *
   * suspend fun logout() {
   *   logoutInternally()
   *   // Frees all dependencies in UserScope
   *   Di.clear(UserScope) // UserInfo("1", "John") gets cleared
   * }
   * ```
   */
  @JvmInline
  value class Scope internal constructor(val value: String)

  /**
   * DI module that you can use to group and re-use dependency injection logic.
   *
   * _Tip: creating DI modules by layer (e.g. data, domain) or features (login, main) is a good idea!_
   */
  interface Module {
    /**
     * Register your DI dependencies in this function.
     */
    fun init()
  }
}

/**
 * Same as [Di.get] but with affinity set to the receiver [Di.Scope] - [this].
 * Read **[Di.get]**.
 */
inline fun <reified T : Any> Scope.get(
  named: Any? = null,
): T = Di.get(named, this)

/**
 * An exception thrown when a factory for a dependency is not found in the DI container.
 */
class DependencyInjectionError(msg: String) : IllegalStateException(msg)

/**
 * Internal API, please don't use it.
 */
annotation class DiInternalApi