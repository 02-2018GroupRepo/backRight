package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
           SpringApplication.run(Application.class, args);
//        SoftMachine machine = new SoftMachine();
//        InventoryManager in = new InventoryManager();
//        in.retrieveProducts();
//        for(int i = 0; i < 7; i++) {
//            machine.insertCoin(0.25);
//        }
//        for(int i = 0; i < 9; i++) {
//            machine.insertCoin(0.10);
//        }
//        for(int i = 0; i < 5; i++) {
//            machine.insertCoin(0.05);
//        }
//        in.vend(1, machine);
    }
}
