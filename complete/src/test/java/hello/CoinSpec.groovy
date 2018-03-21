package hello

import spock.lang.Specification


class CoinSpec extends Specification {
    def "Inserts valid coin" (){
        given: "A machine"
        SoftMachine machine = new SoftMachine();
        and: "Valid coin"
        double validCoin = Coin.getQUARTER();
        when: "Customer inserts a valid coin"
        machine.insertCoin(validCoin)
        then: "Add to coin inventory"
        double result = machine.getTotalAmount();
        and: "Show the total amount customer has inserted"
        result == 0.25;
    }

    def "Inserts invalid coin" (){
        given: "A machine"
        SoftMachine machine = new SoftMachine();
        and: "Invalid coin"
        double invalidCoin = 0.01;
        when: "Customer inserts a invalid coin"
        machine.insertCoin(invalidCoin)
        then: "Machine returns invalid coin"
        double result = machine.getTotalAmount();
        and: "Show error message"
        result == 0.0;
    }

}