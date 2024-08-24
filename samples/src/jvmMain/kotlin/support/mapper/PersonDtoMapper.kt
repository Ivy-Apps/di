package support.mapper

import support.data.Person
import support.data.PersonDto

class PersonDtoMapper : Mapper<PersonDto, Person> {
    override fun map(a: PersonDto): Person {
        return Person(a.firstName, a.lastName)
    }
}