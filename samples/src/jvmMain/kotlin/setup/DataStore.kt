package setup

interface DataStore<T>
class Preferences

class Context

val Context.dataStore: DataStore<Preferences>
    get() = object : DataStore<Preferences> {}