package tutor

import grails.gorm.services.Service
import grails.util.Holders
import uni.course.Course
import uni.security.User
import uni.student.Student
import uni.student.StudentCourse
import uni.tutor.Tutor

@Service(Tutor)
abstract class TutorService implements TutorInterfaceService {

    Tutor isAuthenticatedTutor(User user=null) {
        if (!user) {
            user = Holders.applicationContext.springSecurityService.currentUser
        }
        if (user) {
            return Tutor.findByUser(user)
        }
        return null
    }

    Map<Course,List<Student>> getStudentsFromTutor(Tutor tutor) {
        String query = "from StudentCourse where course in (:courses)"
        def executedQuery = StudentCourse.executeQuery(query,[courses:tutor.courses],[ readOnly:true])
        Map<Course,List<StudentCourse>> res1 =  executedQuery.groupBy{ StudentCourse sc -> sc.course }
        Map<Course,List<Student>> results = [:]
        println "-- ${res1} @@"
        res1?.each {k,v ->
            println "--- ${k} vs ${v.student}"
            results."${k}" = v.student
        }
        println "-- returns is =  ${results}"
        return results
    }
}
