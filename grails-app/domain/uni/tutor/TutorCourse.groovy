package uni.tutor

import grails.compiler.GrailsCompileStatic
import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.codehaus.groovy.util.HashCodeHelper
import uni.course.Course

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class TutorCourse implements Serializable {

    private static final long serialVersionUID = 1

    Tutor tutor
    Course course

    @Override
    boolean equals(other) {
        if (other instanceof TutorCourse) {
            other.tutorId  == tutor?.id && other.courseId == course?.id
        }
    }

    @Override
    int hashCode() {
        int hashCode = HashCodeHelper.initHash()
        if (tutor) {
            hashCode = HashCodeHelper.updateHash(hashCode, tutor.id)
        }
        if (course) {
            hashCode = HashCodeHelper.updateHash(hashCode, course.id)
        }
        hashCode
    }

    static TutorCourse get(long tutorId, long courseId) {
        criteriaFor(tutorId, courseId).get()
    }

    static boolean exists(long tutorId, long courseId) {
        criteriaFor(tutorId, courseId).count()
    }

    private static DetachedCriteria criteriaFor(long tutorId, long courseId) {
        TutorCourse.where {
            tutor == Tutor.load(tutorId) &&
                    course == Course.load(courseId)
        }
    }

    static TutorCourse create(Tutor tutor, Course course, boolean flush = false) {
        def instance = new TutorCourse(tutor: tutor, course: course)
        instance.save(flush: flush)
        instance
    }

    static boolean remove(Tutor t, Course c) {
        if (t != null && c != null) {
            TutorCourse.where { tutor == t && course == c }.deleteAll()
        }
    }

    static int removeAll(Tutor t) {
        t == null ? 0 : TutorCourse.where { tutor == t }.deleteAll() as int
    }

    static int removeAll(Course c) {
        c == null ? 0 : TutorCourse.where { course == c }.deleteAll() as int
    }

    static constraints = {
        tutor nullable: false
        course nullable: false, validator: { Course c, TutorCourse tc ->
            if (tc.tutor?.id) {
                if (TutorCourse.exists(tc.tutor.id, c.id)) {
                    return ['tutorCourse.exists']
                }
            }
        }
    }

    static mapping = {
        id composite: ['tutor', 'course']
        version false
    }

}
