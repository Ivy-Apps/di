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
    val instances = mutableMapOf<DependencyKey, Any>()

    fun init(modules: Set<DiModule>) {
        modules.forEach(DiModule::init)
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
            if (depKey in instances) {
                // single instance already created
                instances[depKey] as T
            } else {
                // create a new instance for the singleton
                val instance = (factory() as T).also {
                    instances[depKey] = it
                }
                instance
            }
        } else {
            // create a new instance
            val instance = (factory() as T).also {
                instances[depKey] = it
            }
            instance
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
        instances.keys.forEach {
            if (it.scope == scope) {
                instances.remove(it)
            }
        }
    }

    fun reset() {
        instances.clear()
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
}

class DependencyInjectionError(msg: String) : IllegalStateException(msg)

interface DiModule {
    fun init()
}