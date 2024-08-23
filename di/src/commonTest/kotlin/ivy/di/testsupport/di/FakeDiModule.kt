package ivy.di.testsupport.di

import ivy.di.Di
import ivy.di.Di.register
import ivy.di.testsupport.FakeModuleDep

object FakeDiModule : Di.Module {
    override fun init() = Di.appScope {
        register { FakeModuleDep() }
    }
}