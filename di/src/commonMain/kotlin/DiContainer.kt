import kotlin.jvm.JvmInline
import kotlin.reflect.KClass


val AppScope = Di.Scope("app")
val FeatureScope = Di.Scope("feature")

object Di {

    val singletons = mutableSetOf<KClass<*>>()
    val instances = mutableMapOf<DependencyKey, Any>()
    val factories = mutableMapOf<DependencyKey, () -> Any>()

    fun init(modules: Set<DiModule>) {
        modules.forEach(DiModule::init)
    }

    fun appScope(block: Scope.() -> Unit) {
        AppScope.block()
    }

    fun featureScope(block: Scope.() -> Unit) {
        FeatureScope.block()
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

    inline fun factory(
        classKey: KClass<*>
    ): Pair<Scope, () -> Any> = scopedFactory(FeatureScope, classKey)
        ?: scopedFactory(AppScope, classKey)
        ?: throw DependencyInjectionError("No factory found for class $classKey")

    inline fun scopedFactory(
        scope: Scope,
        classKey: KClass<*>
    ): Pair<Scope, () -> Any>? = factories[DependencyKey(scope, classKey)]?.let {
        scope to it
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