package student

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import grails.util.Holders
import uni.course.Course
import uni.security.Role
import uni.security.User
import uni.security.UserRole
import uni.student.Student
import uni.student.StudentCourse
import uni.tutor.Tutor

@Service(Student)
abstract class StudentService implements StudentInterfaceService {

    Student isAuthenticatedStudent(User user=null) {
        if (!user) {
            user = Holders.applicationContext.springSecurityService.currentUser
        }
        if (user) {
            return Student.findByUser(user)
        }
        return null
    }

    Set<Student> getStudentsByCourse(Course course) {
        (StudentCourse.findAllByCourse(course) as List<StudentCourse>)*.student as Set<Student>
        //String query = "select student from StudentCourse where course in :courses"
        //return StudentCourse.executeQuery(query,[courses:tutor.courses],[max:-1, readOnly:true]) as Set<Student>
    }
}
