package test

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class PersonSpec extends Specification implements DomainUnitTest<Person> {

    @Unroll('validate on a person with alternativeAge #personAlternativeAge should have returned #shouldBeValid')
    void "test alternativeAge validation"() {
        expect:
            new Person(alternativeAge: personAlternativeAge).validate(['alternativeAge']) == shouldBeValid
        where:
            personAlternativeAge | shouldBeValid
            -2                   | false
            -1                   | false
            0                    | true
            1                    | true
            119                  | true
            120                  | true
            121                  | false
            122                  | false
    }

    /**
     * Neither working - although at the moment not quite sure why
     */
/*
    @Unroll('validate on a person with birthDate #personBirthDate should have returned #shouldBeValid via birthDate')
    void "test birthDate validation"() {
        expect:
            new Person(birthDate: personBirthDate).validate(['birthDate']) == shouldBeValid
        where:
            personBirthDate           | shouldBeValid
            getDateFromAge(-2)        | false
            getDateFromAge(-1)        | false
            getDateFromAge(0)         | true
            getDateFromAge(1)         | true
            getDateFromAge(119)       | true
            getDateFromAge(120)       | true
            getDateFromAge(121)       | false
            getDateFromAge(122)       | false
    }

/*
    @Unroll('validate on a person with birthDate #personBirthDate should have returned #shouldBeValid via Age validation')
    void "test age validation"() {
        expect:
        new Person(birthDate: personBirthDate).validate(['age']) == shouldBeValid
        where:
        personBirthDate           | shouldBeValid
        getDateFromAge(-2)        | false
        getDateFromAge(-1)        | false
        getDateFromAge(0)         | true
        getDateFromAge(1)         | true
        getDateFromAge(119)       | true
        getDateFromAge(120)       | true
        getDateFromAge(121)       | false
        getDateFromAge(122)       | false
    }
    */

    Date getDateFromAge(age) {
        Calendar cal = Calendar.getInstance()
        Date today = cal.getTime()
        cal.add(Calendar.YEAR, age)
        return  cal.getTime()
    }
}
