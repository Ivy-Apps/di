package ivy.di.benchmark.fixtures

class AppHolder(
  val app: App,
  val context: Context,
  val logger: Logger,
)

class AppAppHolder(
  val app: App,
  val appHolder: AppHolder,
)