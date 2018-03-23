package hello

import spock.lang.Specification

class Vending extends Specification {

    ArrayDeque<Product> currentComp = new ArrayDeque<>();

    def "Product is in stock and dispensed, exact change"() {
        given: "a vending machine with products"
        SoftMachine machine = new SoftMachine()
        InventoryManager inventoryManager = new InventoryManager()
        inventoryManager.retrieveProducts();
        and: "exact change in customer cash"
        for (int i = 0; i < 6; i ++)
            machine.insertCoin(0.25)
        when: "a selection is made"
        inventoryManager.vend(1,machine)
        then: "product is vended"
        inventoryManager.getMap().get(1).size() == 5
        and: "customer money is taken by the machine"
        machine.currentMoney() == 5.5
        and:"customer money is transferred"
        machine.getTotalAmount() == 0
    }

    def "Product is in stock and dispensed with change" () {
        given: "a vending machine with products"
        SoftMachine machine = new SoftMachine()
        InventoryManager inventoryManager = new InventoryManager()
        inventoryManager.retrieveProducts();
        double difference;
        and: "customer cash has excess coins"
        for (int i = 0; i < 8; i++)
            machine.insertCoin(0.25);
        currentComp =  inventoryManager.getMap().get(1);
        machine.getTotalAmount() > currentComp.getFirst().getRetailPrice();
        difference = machine.getTotalAmount() - currentComp.getFirst().getRetailPrice();
        when: "a selection is made"
        inventoryManager.vend(29, machine)
        then: "product is dispensed"
        inventoryManager.getMap().get(29).size() == 5
        and: "change is returned"
        machine.returnMachineCoins(difference).equals("Change 0.50 is returned: Quarter(2) Dime(0) Nickel(0)")
        and: "set value that customer has inserted to 0"
        machine.getTotalAmount() == 0
        and: "machine has customer money but returned due change"
        machine.currentMoney() == 5.0
    }

    def "Product is not in stock"() {
        given: "a vending machine without a certain product"
        SoftMachine machine = new SoftMachine()
        InventoryManager inventoryManager = new InventoryManager()
        and: "item is out of stock"
        inventoryManager.getMap().put(1, new ArrayDeque<Product>());
        and: "sufficient funds in customer cash"
        machine.insertCoin(0.25)
        when: "a selection is made"
        inventoryManager.vend(1, machine);
        then: "funds are returned"
        machine.getTotalAmount() == 0
    }

    def "Product is in stock, insufficient funds"() {
        given: "a stocked vending machine"
        SoftMachine machine = new SoftMachine()
        InventoryManager inventoryManager = new InventoryManager()
        inventoryManager.retrieveProducts();
        and: "customer cash"
        for (int i = 0; i < 3; i++)
            machine.insertCoin(0.25)
        when: "a selection is made"
        inventoryManager.vend(1,machine)
        and: "insufficient funds"
        currentComp = inventoryManager.getMap().get(1);
        currentComp.getFirst().getRetailPrice() > machine.getTotalAmount()
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
