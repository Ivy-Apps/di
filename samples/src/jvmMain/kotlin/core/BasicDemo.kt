package core

import ivy.di.Di
import ivy.di.Di.singleton
import setup.HttpClient

fun main() {
    Di.appScope {
        singleton { HttpClient() }
    }
}