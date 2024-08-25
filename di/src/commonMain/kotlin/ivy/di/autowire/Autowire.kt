package ivy.di.autowire

import ivy.di.Di.Scope
import ivy.di.Di.register
import kotlin.jvm.JvmName

/**
 * Automatically registers a factory for a dependency [R] with the given constructor.
 * [ivy.di.Di.get] will be called for each constructor parameter of [R].
 *
 * Make sure to **import [R] before** calling `autoWire(::R)`,
 * otherwise your IDE might not recognize the function.
 *
 * @param constructor A function reference to the constructor of the dependency like `::R`.
 * [R] must be imported before calling this function.
 *
 * ```
 * class Repository(val a: A, val b: B, val c: C)
 *
 * Di.appScope {
 *   autoWire(::Repository)
 *   // equivalent to:
 *   // register { Repository(Di.get(), Di.get(), Di.get()) }
 * }
 * ```
 */
inline fun <reified R : Any> Scope.autoWire(
    crossinline constructor: () -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire1")
inline fun <reified R : Any, reified T1> Scope.autoWire(
    crossinline constructor: (T1) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire2")
inline fun <reified R : Any, reified T1, reified T2> Scope.autoWire(
    crossinline constructor: (T1, T2) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire3")
inline fun <reified R : Any, reified T1, reified T2, reified T3> Scope.autoWire(
    crossinline constructor: (T1, T2, T3) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire4")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire5")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire6")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire7")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire8")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire9")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire10")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire11")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire12")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire13")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire14")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire15")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire16")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire17")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire18")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire19")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire20")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire21")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire22")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire23")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire24")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire25")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire26")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire27")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire28")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27, reified T28> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire29")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27, reified T28, reified T29> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}

/**
 * See [autoWire].
 */
@JvmName("autoWire30")
inline fun <reified R : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27, reified T28, reified T29, reified T30> Scope.autoWire(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30) -> R,
    named: Any? = null,
) {
    register(named = named) { new(constructor) }
}