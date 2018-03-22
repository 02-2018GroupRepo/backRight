package hello

import spock.lang.Specification

class RequestRestockSpec extends Specification {

    def "request restock"() {
        given: "A machine"
        SoftMachine machine = new SoftMachine();
        when: "Product is low"
        machine.requestRestock();
        then: "Send a request"
        1 * machine.requestRestock();
    }

//    def "request restock failed"() {
//        given: "A machine"
//        SoftMachine machine = new SoftMachine();
//        when: "Product is low"
//        machine.requestRestock();
//        then: "Send a request"
//        0 * machine.requestRestock();
//    }

}