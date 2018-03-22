package hello

import spock.lang.Specification

class RequestRestockSpec extends Specification {

    def "request restock if inventory low"() {
        given: "A machine"
        SoftMachine machine = new SoftMachine();
        and: "An inventory"
        InventoryManager inventory = new InventoryManager();
        when: "The inventory is 3 or less"
        inventory.vend("A1", machine);
        then: "Make request restock call"
        machine.getErrorMsg() == null
    }
//        def "request restock"() {
//            given: "A machine"
//            SoftMachine machine = new SoftMachine();
//            when: "Product is low"
//            machine.requestRestock();
//            then: "Send a request"
//            1 * machine.requestRestock();
//        }

//    def "request restock failed"() {
//        given: "A machine"
//        SoftMachine machine = new SoftMachine();
//        when: "Product is low"
//        machine.requestRestock();
//        then: "Send a request"
//        0 * machine.requestRestock();
//    }
}