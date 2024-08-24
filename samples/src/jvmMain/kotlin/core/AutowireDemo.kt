package core

import ivy.di.Di
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import setup.*

fun main() {
    Di.appScope {
        autoWireSingleton(::HttpClient)
        autoWire(::ArticlesRemoteDataSource)
        autoWire(::ArticlesLocalDataSource)
        autoWire(::CombinedArticlesRepository)
    }

    Di.get<ArticlesRepository>()
}