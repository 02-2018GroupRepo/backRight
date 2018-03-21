package hello

import spock.lang.Specification

class Vending extends Specification {

    def "Product is in stock and dispensed, exact change"() {
        given: "a vending machine with products"
        and: "exact change in customer cash"
        when: "a selection is made"
        then: "product is vended"
    }

    def "Product is in stock and dispensed with change" () {
        given: "a vending machine with products"
        and: "customer cash has excess coins"
        when: "a selection is made"
        then: "product is dispensed"
        and: "change is returned"
    }

    def "Product is not in stock"() {
        given: "a vending machine without a certain product"
        SoftMachine machine = new SoftMachine()
        and: "sufficient funds in customer cash"
        when: "a selection is made"
        machine.vend("A1")
        and: "item is out of stock"

        then: "funds are returned"

        and: "no product is dispensed"

    }

    def "Product is in stock, insufficient funds"() {
        given: "a stocked vending machine"
        and: "customer cash"
        when: "a selection is made"
        and: "insufficient funds"
        then: "cash is returned"
    }
}
