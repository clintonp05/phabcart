package helloworld

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class SimpleServiceSpec extends Specification implements ServiceUnitTest<SimpleService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
