package setup

interface DataStore<T>
class Preferences

class Context(val platform: String)

val Context.dataStore: DataStore<Preferences>
    get() = object : DataStore<Preferences> {}