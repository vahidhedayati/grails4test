package uni.tutor

import grails.converters.JSON
import grails.rest.RestfulController
import tutor.TutorService
import uni.course.Course
import uni.student.Student

class TutorController extends RestfulController {
    static responseFormats = ['json', 'xml']

    TutorService tutorService

    TutorController() {
        super(Tutor)
    }

    Map<Course,List<Student>> getStudentsFromTutor() {
        render tutorService.getStudentsFromTutor(Tutor.first()) as JSON
    }

}
