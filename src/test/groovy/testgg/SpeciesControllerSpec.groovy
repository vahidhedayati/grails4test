package testgg

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification


class SpeciesControllerSpec extends Specification implements ControllerUnitTest<SpeciesController> {
    void "test animal population"() {
        when:
        params.height = 12
        params.name = 'tiger'
        def model = controller.createSpecies()
        def animal = model?.animal

        then:
        animal.height == 12
        animal.name == 'tiger'
    }

    void "test empty strings not converted to null"() {
        when: 'conversion of empty strings to null is disabled in application.yml'
        params.height = 172
        params.name = ''
        def model = controller.createSpecies()
        def animal = model?.animal

        then: 'unit tests respect that config value'
        !animal.hasErrors()
        animal.height == 172
        animal.name == ''
    }

}
