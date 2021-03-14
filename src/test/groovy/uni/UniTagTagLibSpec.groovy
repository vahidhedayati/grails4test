package uni

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class UniTagTagLibSpec extends Specification implements TagLibUnitTest<UniTagTagLib> {

    void "test sayHello"() {
        expect:
        applyTemplate('<uni:welcome name="Dave"/>') == 'Welcome Dave'
    }

    void "testStudent"() {
        applyTemplate(contents:'<uni:getStudent id=1/>') == 'Welcome John Smith'
    }

}
