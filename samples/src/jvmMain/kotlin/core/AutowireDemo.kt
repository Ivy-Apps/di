package core

import ivy.di.Di
import ivy.di.autowire.autoWire
import setup.ArticlesRemoteDataSource
import setup.HttpClient

fun main() {
    Di.appScope {
        autoWire(::HttpClient)
        autoWire(::ArticlesRemoteDataSource)
    }
}