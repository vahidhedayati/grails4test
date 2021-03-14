package test

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Person {

    String firstName
    String lastName
    Date birthDate
    Integer alternativeAge

    //static transients=['age']

    Integer getAge() {
        if (birthDate) {
            LocalDate today = LocalDate.now()
            LocalDate BirthDay = new java.sql.Date(birthDate.getTime()).toLocalDate()
            return  ChronoUnit.YEARS.between(BirthDay, today) as int
        }
        return 0
    }
    static def checkAge={val,object,errors->
        if (val && !object.age(0..120)) {
            return errors.rejectValue('age.invalid')
        }
    }

    static constraints = {
        age range: 0..120
        alternativeAge range: 0..120
        birthDate validator:this.checkAge
        //firstName minSize: 2
    }
}
