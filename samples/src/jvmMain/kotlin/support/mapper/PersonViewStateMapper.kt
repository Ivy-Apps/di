package support.mapper

import support.data.Person
import support.data.PersonViewState

class PersonViewStateMapper : Mapper<Person, PersonViewState> {
    override fun map(a: Person): PersonViewState {
        return PersonViewState(a.firstName, a.lastName)
    }
}