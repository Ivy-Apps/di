package core

import ivy.di.Di
import ivy.di.Di.bind
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
        bind<ArticlesRepository, CombinedArticlesRepository>()
    }

    Di.get<ArticlesRepository>()
}