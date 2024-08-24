package core

import ivy.di.Di
import ivy.di.Di.binds
import ivy.di.Di.init
import ivy.di.autowire.autoWire
import ivy.di.autowire.autoWireSingleton
import module.AndroidModule
import support.*

fun main() {
    Di.appScope {
        init(AndroidModule)
        autoWireSingleton(::HttpClient)
        autoWire(::ArticlesRemoteDataSource)
        autoWire(::ArticlesLocalDataSource)
        autoWire(::CombinedArticlesRepository)
        binds<ArticlesRepository, CombinedArticlesRepository>()
    }

    Di.get<ArticlesRepository>()
}