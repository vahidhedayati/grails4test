package uni.student

import grails.converters.JSON
import grails.rest.RestfulController
import student.StudentService

class StudentController extends RestfulController {

   static responseFormats = ['json', 'xml']

    StudentService studentService

    StudentController() {
        super(Student)
    }

    def getStudents() {
        Student student = Student.first()
        render studentService.getStudentsByCourse(student.courses.first()) as JSON
    }

}
