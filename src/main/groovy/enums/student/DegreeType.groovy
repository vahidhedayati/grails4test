package enums.student

import groovy.transform.CompileStatic

@CompileStatic
enum DegreeType {
    ASSOCIATE('ASSOCIATE'),
    BACHELOR('BACHELOR'),
    MASTER('MASTER'),
    DOCTORIAL('DOCTORIAL')

    private final String value

    DegreeType(String value) {
        this.value = value
    }

    String getValue() {
        return value
    }
}