package ivy.di.benchmark.fixtures

class Holder1(
  val app: App,
  val serverApp: ServerApp,
)

class Holder2(
  val app: App,
  val serverApp: ServerApp,
  val holder1: Holder1,
)

class Holder3(
  val holder1: Holder1,
  val holder2: Holder2,
)

class Holder4(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
)

class Holder5(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
  val holder4: Holder4,
)

class Holder6(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
  val holder4: Holder4,
  val holder5: Holder5,
)

class Holder7(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
  val holder4: Holder4,
  val holder5: Holder5,
  val holder6: Holder6,
)

class Holder8(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
  val holder4: Holder4,
  val holder5: Holder5,
  val holder6: Holder6,
  val holder7: Holder7,
)

class Holder9(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
  val holder4: Holder4,
  val holder5: Holder5,
  val holder6: Holder6,
  val holder7: Holder7,
  val holder8: Holder8,
)

class Holder10(
  val holder1: Holder1,
  val holder2: Holder2,
  val holder3: Holder3,
  val holder4: Holder4,
  val holder5: Holder5,
  val holder6: Holder6,
  val holder7: Holder7,
  val holder8: Holder8,
  val holder9: Holder9,
)

interface MultiHolder
class MultiHolderImpl(
  val holderA: Holder10,
  val holderB: Holder10,
  val holderC: Holder10,
  val holderD: Holder10,
  val holderE: Holder10,
  val holderF: Holder10,
  val holderG: Holder10,
  val holderH: Holder10,
  val holderI: Holder10,
  val holderJ: Holder10,
  val holderK: Holder10,
  val holderL: Holder10,
  val holderM: Holder10,
  val holderN: Holder10,
) : MultiHolder

class MultiHolderFinal(
  val holder1: MultiHolder,
  val holder2: MultiHolder,
  val holder3: MultiHolder,
  val holder4: MultiHolder,
  val holder5: MultiHolder,
)