package advanced

import ivy.di.Di
import ivy.di.Di.register
import support.data.Person
import support.data.PersonDto
import support.data.PersonViewState
import support.mapper.Mapper
import support.mapper.PersonDtoMapper
import support.mapper.PersonViewStateMapper

fun main() {
    Di.appScope {
        register<Mapper<PersonDto, Person>> { PersonDtoMapper() }
        register<Mapper<Person, PersonViewState>> { PersonViewStateMapper() }
    }

    val dto = PersonDto("John", "Doe")
    val person = Di.get<Mapper<PersonDto, Person>>().map(dto)
    val viewState = Di.get<Mapper<Person, PersonViewState>>().map(person)
    println(viewState)
}