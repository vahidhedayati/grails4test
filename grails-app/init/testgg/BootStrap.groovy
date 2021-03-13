package testgg

import enums.security.RoleType
import enums.security.UserType
import grails.gorm.transactions.Transactional
import org.grails.datastore.gorm.GormEntity
import uni.course.Course
import uni.security.Role
import uni.security.User
import uni.security.UserRole

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.MonthDay
import java.time.format.DateTimeFormatter

class BootStrap {

    private final static FAIL_ON_ERROR = [failOnError: true]

    def init = { servletContext ->
        initSecurity()
        initCourse()
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
