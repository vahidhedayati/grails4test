package uni.student

import enums.student.DegreeType
import grails.compiler.GrailsCompileStatic
import uni.course.Course
import uni.security.User

@GrailsCompileStatic
class Student {

    String firstName
    String middleName
    String lastName

    Date birthDate

    User user
    DegreeType degreeType

    String institutionName
    String speciality

    String email
    String phone

    Date dateCreated
    Date lastUpdated
    User createdBy
    User lastUpdatedBy
    boolean disabled


    Set<Course> getCourses() {
        (StudentCourse.findAllByStudent(this) as List<StudentCourse>)*.course as Set<Course>
    }


    static constraints = {
    }
}
