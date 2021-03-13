package testgg

import enums.security.RoleType
import enums.security.UserType
import enums.student.DegreeType
import grails.gorm.transactions.Transactional
import org.grails.datastore.gorm.GormEntity
import uni.course.Course
import uni.security.Role
import uni.security.User
import uni.security.UserRole
import uni.student.Student
import uni.student.StudentCourse
import uni.tutor.Tutor
import uni.tutor.TutorCourse

import java.text.SimpleDateFormat

class BootStrap {

    private final static FAIL_ON_ERROR = [failOnError: true]

    def init = { servletContext ->
        initSecurity()
        initCourse()
        initStudent()
        initTutor()
    }
    def destroy = {
    }

    @Transactional
    private initSecurity() {
        log.debug 'Security init start'

        def adminRole = new Role(authority: RoleType.ROLE_ADMIN.value).save(FAIL_ON_ERROR)

        def adminUser = new User(
                username: 'admin',
                password: 'password',
                userType: UserType.ADMIN
        ).save(FAIL_ON_ERROR)

        UserRole.create(adminUser, adminRole)

        def tutorRole = new Role(authority: RoleType.ROLE_TUTOR.value).save(FAIL_ON_ERROR)

        def tutorUser = new User(
                username: 'tutor',
                password: 'password',
                userType: UserType.TUTOR
        ).save(FAIL_ON_ERROR)
        UserRole.create(tutorUser, tutorRole)


        def studentRole = new Role(authority: RoleType.ROLE_STUDENT.value).save(FAIL_ON_ERROR)

        def studentUser = new User(
                username: 'student',
                password: 'yn6Nxk',
                userType: UserType.STUDENT
        ).save(FAIL_ON_ERROR)

        UserRole.create(studentUser, studentRole)

        log.debug 'security init end'
    }

    @Transactional
    private initCourse() {
        log.debug 'Start init security'
        def coursesMap = [
                [
                        name            : 'Programming: Learn Java basics',
                        startDate       : getDateFromString("2020-09-01"),
                        endDate         : getDateFromString("2021-07-21")
                ],

                [
                        name            : 'Vue Js For beginners',
                        startDate       : getDateFromString("2020-09-17"),
                        endDate         : getDateFromString("2021-06-21")
                ],

                [
                        name            : 'Reactive Web',
                        startDate       : getDateFromString("2020-09-07"),
                        endDate         : getDateFromString("2021-06-21")
                ],

                [
                        name            : 'Node.js for beginners',
                        startDate       : getDateFromString("2020-09-07"),
                        endDate         : getDateFromString("2021-06-21")
                ],

                [
                        name            : 'Python for beginners',
                        startDate       : getDateFromString("2020-09-07"),
                        endDate         : getDateFromString("2021-06-21")
                ],
        ]

        coursesMap.each {
            def course = new Course(it)
            setDefaultFields(course)
            course.save(FAIL_ON_ERROR)
        }
        log.debug 'Finish init courses'
    }


    @Transactional
    private initStudent() {
        log.debug 'student init'

        Student student = new Student(
                firstName: 'John',
                lastName: 'Smith',
                birthDate: getDateFromString("2000-09-01"),
                user: User.findByUsername('student'),
                degreeType: DegreeType.BACHELOR
        )
        setDefaultFields(student)
        student.save(FAIL_ON_ERROR)
        StudentCourse.create(student, Course.first())
        log.debug 'student init end'
    }

    @Transactional
    private initTutor() {
        log.debug 'Start init mentor'

        Tutor tutor = new Tutor(
                firstName: 'Clogg',
                lastName: 'Clever',
                birthDate: getDateFromString("1974-09-01"),
                user: User.findByUsername('tutor')
        )
        setDefaultFields(tutor)
        tutor.save(FAIL_ON_ERROR)
        TutorCourse.create(tutor, Course.first())
        log.debug 'tutor init end'
    }

    /**
     * Converts a string date back into Date object
     * @param input dd MMM yyyy = 02 Jan 2020
     * @return Date object for 02 Jan 2020
     */
    Date getDateFromString(String input) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(input);
    }

    /**
     * Sets a default parameters for provided domainClass aka gormEntity -
     * used to update createdBy and updatedBy both user object fields
     * @param gormEntity = class in question
     * @param user = user value being set for
     * @return actual domain object (gormEntity) fed in
     */
    private GormEntity setDefaultFields(GormEntity gormEntity, User user = User.findByUsername('admin')) {
        setCreatedBy(gormEntity, user)
        setLastUpdatedBy(gormEntity, user)
        return gormEntity
    }

    private setCreatedBy(GormEntity gormEntity, User user = User.findByUsername('admin')) {
        if (gormEntity.hasProperty('createdBy')) {
            gormEntity.createdBy = user
        }
        return gormEntity
    }

    private setLastUpdatedBy(GormEntity gormEntity, User user = User.findByUsername('admin')) {
        if (gormEntity.hasProperty('lastUpdatedBy')) {
            gormEntity.lastUpdatedBy = user
        }
        return gormEntity
    }
}
