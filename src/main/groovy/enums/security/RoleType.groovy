package enums.security

import groovy.transform.CompileStatic

@CompileStatic
enum RoleType {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_TUTOR("ROLE_TUTOR"),
    ROLE_STUDENT("ROLE_STUDENT")

    private final String value

    RoleType(String value) {
        this.value=value
    }

    String getValue() {
        return value
    }
}
