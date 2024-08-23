package ivy.di

import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

val AppScope = Di.Scope("app")
val FeatureScope = Di.Scope("feature")

typealias Factory = () -> Any

object Di {
    private val DEFAULT_SCOPES = setOf(AppScope, FeatureScope)
    private val scopes = DEFAULT_SCOPES.toMutableSet()

    val factories = mutableMapOf<DependencyKey, Factory>()
    val singletons = mutableSetOf<KClass<*>>()
    val singletonInstances = mutableMapOf<DependencyKey, Any>()

    fun init(modules: Set<Module>) {
        modules.forEach(Module::init)
    }

    fun appScope(block: Scope.() -> Unit) = scope(AppScope, block)

    fun featureScope(block: Scope.() -> Unit) = scope(FeatureScope, block)

    fun scope(scope: Scope, block: Scope.() -> Unit) {
        scopes.add(scope)
        scope.block()
    }

    inline fun <reified T : Any> Scope.register(noinline factory: () -> T) {
        factories[DependencyKey(this, T::class)] = factory
    }

    inline fun <reified T : Any> Scope.singleton(noinline factory: () -> T) {
        val classKey = T::class
        factories[DependencyKey(this, classKey)] = factory
        singletons.add(classKey)
    }

    inline fun <reified T : Any> get(): T {
        val classKey = T::class
        val (scope, factory) = factory(classKey)
        val depKey = DependencyKey(scope, classKey)
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

    fun factory(
        classKey: KClass<*>
    ): Pair<Scope, Factory> = scopes
        .firstNotNullOfOrNull { scope ->
            scopedFactoryOrNull(scope, classKey)
        } ?: throw DependencyInjectionError("No factory found for class $classKey")

    private fun scopedFactoryOrNull(
        scope: Scope,
        classKey: KClass<*>
    ): Pair<Scope, () -> Any>? = factories[DependencyKey(scope, classKey)]
        ?.let { factory ->
            scope to factory
        }

    fun clearInstances(scope: Scope) {
        singletonInstances.keys.forEach { instanceKey ->
            if (instanceKey.scope == scope) {
                singletonInstances.remove(instanceKey)
            }
        }
    }

    fun reset() {
        singletonInstances.clear()
        factories.clear()
        singletons.clear()
        scopes.apply {
            clear()
            addAll(DEFAULT_SCOPES)
        }
    }

    data class DependencyKey(
        val scope: Scope,
        val klass: KClass<*>
    )

    @JvmInline
    value class Scope(val value: String)

    interface Module {
        fun init()
    }
}

class DependencyInjectionError(msg: String) : IllegalStateException(msg)