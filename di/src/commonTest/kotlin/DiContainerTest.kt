import Di.register
import Di.singleton
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

class DiContainerTest {

    @BeforeTest
    fun setup() {
        Di.reset()
    }

    @Test
    fun `creates an instance in app scope`() {
        // Given
        Di.appScope { register { FakeStateHolder() } }
        Di.get<FakeStateHolder>().number = 42

        // When
        val stateHolder = Di.get<FakeStateHolder>()

        // Then
        stateHolder.number shouldBe 0
    }

    @Test
    fun `creates a singleton in app scope`() {
        // Given
        Di.appScope { singleton { FakeStateHolder() } }
        Di.get<FakeStateHolder>().number = 42

        // When
        val stateHolder = Di.get<FakeStateHolder>()

        // Then
        stateHolder.number shouldBe 42
    }

    @Test
    fun `constructs a more complex DI graph`() {
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
    fun `throws an exception for not registered classes`() {
        // When
        val thrownException = shouldThrow<DependencyInjectionError> {
            Di.get<FakeStateHolder>()
        }

        // Then
        thrownException.message.shouldNotBeNull()
    }

    @Test
    fun `binds an interface`() {
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
    fun `creates an instance in feature scope`() {
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
    fun `creates a singleton in feature scope`() {
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
    fun `di module registration works`() {
        // Given
        Di.init(setOf(FakeModule))

        // When
        val instance = Di.get<FakeModuleDep>()

        // Then
        instance.shouldNotBeNull()
    }

    @Test
    fun `creates a new scope and dependency in it`() {
        // Given
        val newScope = Di.Scope("new")

        // When
        Di.scope(newScope) {
            register { FakeStateHolder() }
        }
        val instance = Di.get<FakeStateHolder>()

        // Then
        instance.shouldNotBeNull()
    }
}

interface FakeAbstraction
class FakeImplOne : FakeAbstraction

@Suppress("unused")
class FakeImplTwo : FakeAbstraction
class FakeStateHolder {
    var number = 0
}

class FakeDataSource(@Suppress("unused") val httpClient: HttpClient)
class HttpClient
class FakeRepository(@Suppress("unused") val dataSource: FakeDataSource)
class FakeViewModel(
    @Suppress("unused")
    val repository: FakeRepository,
    @Suppress("unused")
    val stateHolder: FakeStateHolder
)

class FakeModuleDep
object FakeModule : DiModule {
    override fun init() = Di.appScope {
        register { FakeModuleDep() }
    }
}