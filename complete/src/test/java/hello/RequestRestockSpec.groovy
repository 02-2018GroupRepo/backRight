package hello

import spock.lang.Specification

class RequestRestockSpec extends Specification {

    def "request restock if inventory low"() {
        given: "A machine"
        SoftMachine machine = new SoftMachine("001");
        and: "An inventory"
        InventoryManager inventory = new InventoryManager();
        when: "The inventory is 3 or less"
        inventory.vend("A1", machine);
        then: "Make request restock call"
        machine.getErrorMsg() == null
    }

    def "request restock failed"() {
        given: "A machine"
        SoftMachine machine = new SoftMachine("001");
        and: "An inventory"
        InventoryManager inventory = new InventoryManager();
        when: "The inventory is 3 or less"
        and: "The machine sends out a restock call"
        inventory.vend("A1", machine)
        then: "The request fails to be sent and receive error message"
        machine.getErrorMsg() != null;
    }
}