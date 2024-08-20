import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.mockk.mockk
import ivy.di.Di.register
import ivy.di.Di.singleton
import org.junit.Before
import org.junit.Test

class DiContainerTest {

    @Before
    fun setup() {
        Di.reset()
    }

    @Test
    fun `creates an instance in app scope`() {
        // given
        Di.appScope { register { FakeStateHolder() } }
        Di.get<FakeStateHolder>().number = 42

        // when
        val stateHolder = Di.get<FakeStateHolder>()

        // then
        stateHolder.number shouldBe 0
    }

    @Test
    fun `creates a singleton in app scope`() {
        // given
        Di.appScope { singleton { FakeStateHolder() } }
        Di.get<FakeStateHolder>().number = 42

        // when
        val stateHolder = Di.get<FakeStateHolder>()

        // then
        stateHolder.number shouldBe 42
    }

    @Test
    fun `constructs a more complex DI graph`() {
        // given
        Di.appScope {
            singleton { FakeStateHolder() }
            singleton { mockk<HttpClient>() }
            register { FakeDataSource(Di.get()) }
            register { FakeRepository(Di.get()) }
            register { FakeViewModel(Di.get(), Di.get()) }
        }

        // when
        val viewModel: FakeViewModel = Di.get()

        // then
        viewModel.shouldNotBeNull()
    }

    @Test
    fun `throws an exception for not registered classes`() {
        // when
        val thrownException = shouldThrow<DiError> {
            Di.get<FakeStateHolder>()
        }

        // then
        thrownException.message.shouldNotBeNull()
    }

    @Test
    fun `binds an interface`() {
        // given
        Di.appScope {
            register<FakeAbstraction> { FakeImplOne() }
        }

        // when
        val instance = Di.get<FakeAbstraction>()

        // then
        instance.shouldNotBeNull()
    }

    @Test
    fun `creates an instance in screen scope`() {
        // given
        Di.screenScope {
            register { FakeStateHolder() }
        }
        Di.get<FakeStateHolder>().number = 42

        // when
        val stateHolder = Di.get<FakeStateHolder>()

        // then
        stateHolder.number shouldBe 0
    }

    @Test
    fun `creates a singleton in screen scope`() {
        // given
        Di.screenScope {
            singleton { FakeStateHolder() }
        }
        Di.get<FakeStateHolder>().number = 42

        // when
        val stateHolder = Di.get<FakeStateHolder>()

        // then
        stateHolder.number shouldBe 42

        // when the scope is reset
        Di.clearInstances(Di.ScreenScope)

        // then after the reset
        Di.get<FakeStateHolder>().number shouldBe 0
    }

    @Test
    fun `di module registration works`() {
        // given
        Di.init(setOf(FakeModule))

        // when
        val instance = Di.get<FakeModuleDep>()

        // then
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