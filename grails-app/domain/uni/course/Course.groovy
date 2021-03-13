package uni.course

import uni.security.User

class Course {

    String name
    Date startDate
    Date endDate

    Date dateCreated
    Date lastUpdated
    User createdBy
    User lastUpdatedBy
    boolean disabled

    static constraints = {
        name: size:0..100
        endDate validator: { val, obj, errors->
            if (val < obj.startDate) {
                errors.rejectvalue('endDate', 'dateStartExceedsEnd')
            }
        }
    }
}
