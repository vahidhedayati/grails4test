package uni.student

import grails.compiler.GrailsCompileStatic
import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.codehaus.groovy.util.HashCodeHelper
import uni.course.Course

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class StudentCourse implements Serializable {

    private static final long serialVersionUID = 1

    Student student
    Course course

    @Override
    boolean equals(other) {
        if (other instanceof StudentCourse) {
            other.studentId  == student?.id && other.courseId == course?.id
        }
    }

    @Override
    int hashCode() {
        int hashCode = HashCodeHelper.initHash()
        if (student) {
            hashCode = HashCodeHelper.updateHash(hashCode, student.id)
        }
        if (course) {
            hashCode = HashCodeHelper.updateHash(hashCode, course.id)
        }
        hashCode
    }

    static StudentCourse get(long studentId, long courseId) {
        criteriaFor(studentId, courseId).get()
    }

    static boolean exists(long studentId, long courseId) {
        criteriaFor(studentId, courseId).count()
    }

    private static DetachedCriteria criteriaFor(long studentId, long courseId) {
        StudentCourse.where {
            student == student.load(studentId) &&
                    course == Course.load(courseId)
        }
    }

    static StudentCourse create(Student student, Course course, boolean flush = false) {
        def instance = new StudentCourse(student: student, course: course)
        instance.save(flush: flush)
        instance
    }

    static boolean remove(Student t, Course c) {
        if (t != null && c != null) {
            StudentCourse.where { student == t && course == c }.deleteAll()
        }
    }

    static int removeAll(Student t) {
        t == null ? 0 : StudentCourse.where { student == t }.deleteAll() as int
    }

    static int removeAll(Course c) {
        c == null ? 0 : StudentCourse.where { course == c }.deleteAll() as int
    }

    static constraints = {
        student nullable: false
        course nullable: false, validator: { Course c, StudentCourse tc ->
            if (tc.student?.id) {
                if (StudentCourse.exists(tc.student.id, c.id)) {
                    return ['studentCourse.exists']
                }
            }
        }
    }

    static mapping = {
        id composite: ['student', 'course']
        version false
    }

}
