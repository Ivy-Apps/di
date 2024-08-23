package ivy.di

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ivy.di.Di.register
import ivy.di.Di.singleton
import ivy.di.testsupport.*
import ivy.di.testsupport.di.FakeDiModule
import kotlin.test.BeforeTest
import kotlin.test.Test

class DiContainerTest {

    @BeforeTest
    fun setup() {
        Di.reset()
    }

    @Test
    fun createsAnInstance_in_appScope() {
        // Given
        Di.appScope { register { FakeStateHolder() } }
        Di.get<FakeStateHolder>().number = 42

        // When
        val stateHolder = Di.get<FakeStateHolder>()

        // Then
        stateHolder.number shouldBe 0
    }

    @Test
    fun creates_a_singleton_in_appScope() {
        // Given
        Di.appScope { singleton { FakeStateHolder() } }
        Di.get<FakeStateHolder>().number = 42

        // When
        val stateHolder = Di.get<FakeStateHolder>()

        // Then
        stateHolder.number shouldBe 42
    }

    @Test
    fun moreComplex_DI_graph_1() {
        // Given
        Di.appScope {
            singleton { FakeStateHolder() }
            singleton { HttpClient() }
            register { FakeDataSource(Di.get()) }
            register { FakeRepository(Di.get()) }
            register { FakeViewModel(Di.get(), Di.get()) }
        }

        // When
        val viewModel: FakeViewModel = Di.get()

        // Then
        viewModel.shouldNotBeNull()
    }

    @Test
    fun throws_for_not_registered_classes() {
        // When
        val thrownException = shouldThrow<DependencyInjectionError> {
            Di.get<FakeStateHolder>()
        }

        // Then
        thrownException.message.shouldNotBeNull()
    }

    @Test
    fun binds_interface() {
        // Given
        Di.appScope {
            register<FakeAbstraction> { FakeImplOne() }
        }

        // When
        val instance = Di.get<FakeAbstraction>()

        // Then
        instance.shouldNotBeNull()
    }

    @Test
    fun createsAnInstance_in_featureScope() {
        // Given
        Di.featureScope {
            register { FakeStateHolder() }
        }
        Di.get<FakeStateHolder>().number = 42

        // When
        val stateHolder = Di.get<FakeStateHolder>()

        // Then
        stateHolder.number shouldBe 0
    }

    @Test
    fun creates_a_singleton_in_featureScope() {
        // Given
        Di.featureScope {
            singleton { FakeStateHolder() }
        }
        Di.get<FakeStateHolder>().number = 42

        // When
        val stateHolder = Di.get<FakeStateHolder>()

        // Then
        stateHolder.number shouldBe 42

        // When the scope is reset
        Di.clearInstances(FeatureScope)

        // Then after the reset
        Di.get<FakeStateHolder>().number shouldBe 0
    }

    @Test
    fun moduleRegistration() {
        // Given
        Di.init(setOf(FakeDiModule))

        // When
        val instance = Di.get<FakeModuleDep>()

        // Then
        instance.shouldNotBeNull()
    }

    @Test
    fun create_DI_scope() {
        // Given
        val customScope = Di.newScope("new")

        // When
        Di.scope(customScope) {
            register { FakeStateHolder() }
        }
        val instance = Di.get<FakeStateHolder>()

        // Then
        instance.shouldNotBeNull()
    }
}