package module

import ivy.di.Di
import ivy.di.Di.register
import ivy.di.Di.singleton
import support.Context
import support.dataStore

object AndroidModule : Di.Module {
    override fun init() = Di.appScope {
        singleton { Context(platform = "Android") }
        register { Di.get<Context>().dataStore }
    }
}