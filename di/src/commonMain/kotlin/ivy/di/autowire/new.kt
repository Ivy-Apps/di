package ivy.di.autowire

import ivy.di.Di

/**
 * Inspired by [Koin](https://github.com/InsertKoinIO/koin).
 */

inline fun <reified R> new(
    constructor: () -> R,
): R = constructor()

/**
 * @see new
 */
inline fun <reified R, reified T1> new(
    constructor: (T1) -> R,
): R = constructor(Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2> new(
    constructor: (T1, T2) -> R,
): R = constructor(Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3> new(
    constructor: (T1, T2, T3) -> R,
): R = constructor(Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4> new(
    constructor: (T1, T2, T3, T4) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> new(
    constructor: (T1, T2, T3, T4, T5) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> new(
    constructor: (T1, T2, T3, T4, T5, T6) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): R = constructor(Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get())

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27, reified T28> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28) -> R,
): R = constructor(
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get(),
    Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27, reified T28, reified T29> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29) -> R,
): R = constructor(
    Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(),
    Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(),
    Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get()
)

/**
 * @see new
 */
inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22, reified T23, reified T24, reified T25, reified T26, reified T27, reified T28, reified T29, reified T30> new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30) -> R,
): R = constructor(
    Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(),
    Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(),
    Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get(), Di.get()
)