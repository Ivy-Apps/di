package ivy.di

import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

typealias Factory = () -> Any

val AppScope = Di.newScope("app")
val FeatureScope = Di.newScope("feature")

object Di {
    private val scopes = mutableSetOf<Scope>()

    val factories = mutableMapOf<DependencyKey, Factory>()
    val singletons = mutableSetOf<KClass<*>>()
    val singletonInstances = mutableMapOf<DependencyKey, Any>()

    /**
     * Initializes a set of modules by calling their [Module.init] functions.
     */
    fun init(modules: Set<Module>) {
        modules.forEach(::init)
    }

    /**
     * Initializes a module by calling its [Module.init] function.
     */
    fun init(module: Module) = module.init()

    /**
     * Scope used to register dependencies for the entire lifetime of the application.
     */
    fun appScope(block: Scope.() -> Unit) = AppScope.block()

    /**
     * Scope used to register dependencies for a feature.
     */
    fun featureScope(block: Scope.() -> Unit) = FeatureScope.block()

    /**
     * Registers a DI new scope.
     * @param name Unique identifier for the scope.
     * @return The newly created [Scope].
     */
    fun newScope(name: String): Scope = Scope(name).also(scopes::add)
    fun scope(scope: Scope, block: Scope.() -> Unit) = scope.block()

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
     * @throws DependencyInjectionError if no factory for [T] with qualifier [named] is registered.
     */
    @Throws(DependencyInjectionError::class)
    inline fun <reified T : Any> getLazy(named: Any? = null): Lazy<T> {
        factoryOrThrow(T::class, named) // ensure that factory exists
        return lazy { get<T>(named) }
    }

    /**
     * Returns an instance of a dependency [T].
     * Each call to [get] will return a new instance using your registered factory.
     * If [T] is a [singleton], the same instance will be returned on subsequent calls.
     * @param named An optional qualifier to distinguish between multiple dependencies of the same type.
     * @throws DependencyInjectionError if no factory for [T] with qualifier [named] is registered.
     */
    @Throws(DependencyInjectionError::class)
    inline fun <reified T : Any> get(named: Any? = null): T {
        val classKey = T::class
        val (scope, factory) = factoryOrThrow(classKey, named)
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
    @Throws(DependencyInjectionError::class)
    fun factoryOrThrow(
        classKey: KClass<*>,
        named: Any?,
    ): Pair<Scope, Factory> = scopes
        .firstNotNullOfOrNull { scope ->
            scopedFactoryOrNull(scope, classKey, named)
        } ?: throw DependencyInjectionError(diErrorMsg(classKey, named))

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

    private fun scopedFactoryOrNull(
        scope: Scope,
        classKey: KClass<*>,
        named: Any?,
    ): Pair<Scope, () -> Any>? = factories[DependencyKey(scope, classKey, named)]
        ?.let { factory ->
            scope to factory
        }

    /**
     * Clears all instances in the given [scope].
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
     * Note: [scopes] aren't clear for performance reasons.
     */
    fun reset() {
        singletonInstances.clear()
        factories.clear()
        singletons.clear()
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
     * A DI scope. Scopes are used to group dependencies and manage their lifecycle.
     */
    @JvmInline
    value class Scope internal constructor(val value: String)

    interface Module {
        /**
         * Register your DI dependencies in this function.
         */
        fun init()
    }
}

class DependencyInjectionError(msg: String) : IllegalStateException(msg)