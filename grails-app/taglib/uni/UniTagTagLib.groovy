package uni

import uni.student.Student

class UniTagTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def studentService

    static namespace="uni"

    def welcome={ attrs ->
        out << "Welcome ${attrs.name}"
    }

    def getStudent={ attrs ->
        Student student = studentService.get(attrs.id)
        out << "Welcome ${student.firstName} ${student.lastName}"
    }
}
