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

    fun init(modules: Set<Module>) {
        modules.forEach(Module::init)
    }

    fun appScope(block: Scope.() -> Unit) = AppScope.block()
    fun featureScope(block: Scope.() -> Unit) = FeatureScope.block()
    fun newScope(name: String): Scope = Scope(name).also(scopes::add)
    fun scope(scope: Scope, block: Scope.() -> Unit) = scope.block()

    inline fun <reified T : Any> Scope.register(
        named: Any? = null,
        noinline factory: () -> T
    ) {
        factories[DependencyKey(this, T::class, named)] = factory
    }

    inline fun <reified T : Any> Scope.singleton(
        named: Any? = null,
        noinline factory: () -> T
    ) {
        val classKey = T::class
        factories[DependencyKey(this, classKey, named)] = factory
        singletons.add(classKey)
    }

    inline fun <reified T : Any> get(named: Any? = null): T {
        val classKey = T::class
        val (scope, factory) = factory(classKey, named)
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

    fun factory(
        classKey: KClass<*>,
        named: Any?,
    ): Pair<Scope, Factory> = scopes
        .firstNotNullOfOrNull { scope ->
            scopedFactoryOrNull(scope, classKey, named)
        } ?: throw DependencyInjectionError("No factory found for class $classKey")

    private fun scopedFactoryOrNull(
        scope: Scope,
        classKey: KClass<*>,
        named: Any?,
    ): Pair<Scope, () -> Any>? = factories[DependencyKey(scope, classKey, named)]
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
    }

    data class DependencyKey(
        val scope: Scope,
        val klass: KClass<*>,
        val qualifier: Any?,
    )

    @JvmInline
    value class Scope internal constructor(val value: String)

    interface Module {
        fun init()
    }
}

class DependencyInjectionError(msg: String) : IllegalStateException(msg)