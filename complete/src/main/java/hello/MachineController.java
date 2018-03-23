package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MachineController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private SoftMachine machine = new SoftMachine("001");
    private InventoryManager inventoryManager = new InventoryManager();

    @RequestMapping("/requestRestock")
    public void response() {
        machine.requestRestock();
    }

    @RequestMapping("/insertCoin")
    public String insert(@RequestParam(value="coin") double coin) {
        machine.insertCoin(coin);
        if(machine.isValidCoin(coin)) {
            return "You inserts " + coin +
                    "\nTotal amount you have inserted is " + machine.getTotalAmount();
        } else {
            return coin + " is not valid." +
                    "\nTotal amount you have inserted is " + machine.getTotalAmount();
        }
    }

    @RequestMapping("/getItemInfo")
    public String getItem(@RequestParam(value="id") int id) {
        return inventoryManager.getItemInfo(id);
    }

    @RequestMapping("/purchaseProduct")
    public String purchase(@RequestParam(value="id") int id) {
        return inventoryManager.vend(id, machine);
    }
}