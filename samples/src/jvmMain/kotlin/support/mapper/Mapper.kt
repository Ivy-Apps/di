package support.mapper

interface Mapper<A, B> {
    fun map(a: A): B
}