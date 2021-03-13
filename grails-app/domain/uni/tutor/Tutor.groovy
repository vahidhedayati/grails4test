package uni.tutor

import uni.course.Course
import uni.security.User

class Tutor {
    String firstName
    String middleName
    String lastName

    Date birthDate

    User user

    Set<Course> getCourses() {
        (TutorCourse.findAllByTutor(this) as List<TutorCourse>)*.course as Set<Course>
    }

    static constraints = {
        middleName nullable:true
    }
}
