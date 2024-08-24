package module

import ivy.di.Di
import ivy.di.Di.register
import ivy.di.autowire.autoWire
import setup.Context
import setup.dataStore

object AndroidModule : Di.Module {
    override fun init() = Di.appScope {
        autoWire(::Context)
        register { Di.get<Context>().dataStore }
    }
}