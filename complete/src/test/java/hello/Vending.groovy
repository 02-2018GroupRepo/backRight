package hello

import spock.lang.Specification

class Vending extends Specification {

    def "Product is in stock and dispensed, exact change"() {
        given: "a vending machine with products"
        SoftMachine machine = new SoftMachine()
        InventoryManager im = new InventoryManager()
        and: "exact change in customer cash"
        for (int i = 0; i < 4; i ++)
            machine.insertCoin(0.25)
        when: "a selection is made"
        im.vend("A1",machine)
        then: "product is vended"
        im.getAllStock()[0] == 0
    }

    def "Product is in stock and dispensed with change" () {
        given: "a vending machine with products"
        SoftMachine machine = new SoftMachine()
        InventoryManager inventoryManager = new InventoryManager()
        and: "customer cash has excess coins"
        for (int i = 0; i < 6; i++)
            machine.insertCoin(0.25);
        machine.getTotalAmount() > inventoryManager.getItemPrice("A1")
        when: "a selection is made"
        inventoryManager.vend("A1",machine)
        then: "product is dispensed"
        inventoryManager.getAllStock()[0] == 0
        and: "change is returned"
        machine.getTotalAmount() == 0
    }

    def "Product is not in stock"() {
        given: "a vending machine without a certain product"
        SoftMachine machine = new SoftMachine()
        InventoryManager im = new InventoryManager()
        and: "sufficient funds in customer cash"
        machine.insertCoin(0.25)
        when: "a selection is made"
        im.vend("A2",machine)
        and: "item is out of stock"
        im.getAllStock()[1] < 1
        then: "funds are returned"
        machine.getTotalAmount() == 0
        and: "no product is dispensed"
        im.getAllStock()[1] == 0
    }

    def "Product is in stock, insufficient funds"() {
        given: "a stocked vending machine"
        SoftMachine machine = new SoftMachine()
        InventoryManager inventoryManager = new InventoryManager()
        and: "customer cash"
        for (int i = 0; i < 3; i++)
            machine.insertCoin(0.25)
        when: "a selection is made"
        inventoryManager.vend("A1",machine)
        and: "insufficient funds"
        inventoryManager.getItemPrice("A1") > machine.getTotalAmount()
        then: "cash is returned"
        machine.getTotalAmount() == 0
    }

    def "retrieve products"() {
        given: "An inventory manager and product API"
        InventoryManager inventoryManager = new InventoryManager();
        ArrayList<Product> temp = new ArrayList<>();
        when: "Retrieves products from product API"
        temp = inventoryManager.retrieveProducts();
        then: "List of products is returned"
        !temp.isEmpty();

    }
}