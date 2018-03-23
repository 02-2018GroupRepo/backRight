package hello;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class InventoryManager {

    private ArrayList<Product> productList;
    private HashMap<Integer,Queue> map;

    public InventoryManager() {
        productList = new ArrayList<>();
        map = new HashMap<>();
        retrieveProducts();
    }

    public String getItemInfo(int selection) {
        ArrayDeque queue = (ArrayDeque) getMap().get(selection);
        Product selectedProduct = (Product) queue.getFirst();
        String productInfo = "Product: " + selectedProduct.getName() + " Price: " + selectedProduct.getRetailPrice();
        System.out.println(productInfo);
        return productInfo;
    }

    public String vend(int selection,SoftMachine machine) {
        ArrayDeque<Product> currentComp = (ArrayDeque)map.get(selection);
        String message = null;
        if (currentComp.size() == 0) {
            message = "Out of stock";
            machine.reset();
        } else if(machine.getTotalAmount() < currentComp.getFirst().getRetailPrice()) {
            message = "Product: " + currentComp.getFirst().getName() + " Price: " + currentComp.getFirst().getRetailPrice() + " is not vended";
            machine.reset();
        }
        else if (machine.getTotalAmount() == currentComp.getFirst().getRetailPrice()) {
            message = "Product: " + currentComp.getFirst().getName() + " Price: " + currentComp.getFirst().getRetailPrice() + " is vended";
            currentComp.remove();
            machine.reset();
        }
        else if (machine.getTotalAmount() > currentComp.getFirst().getRetailPrice()) {
            message = "Product: " + currentComp.getFirst().getName() + " Price: " + currentComp.getFirst().getRetailPrice() + " is vended " +
                    machine.returnMachineCoins(machine.getTotalAmount() - currentComp.getFirst().getRetailPrice());
            currentComp.remove();
            machine.reset();
        }
        checkForRestock(selection, machine);
        System.out.println(message);
        return message;
    }

    public ArrayList<Product> retrieveProducts() {
        productList = ProductApi.retrieveProductList();
        for(int i = 0; i < productList.size(); i++) {
            Queue<Product> currentQueue = new ArrayDeque<>();
            for (int j = 0; j < 6; j++) {
                currentQueue.add(productList.get(i));
            }
            map.put(i + 1, currentQueue);
        }
        return productList;
    }

    public void checkForRestock(int selection, SoftMachine machine) {
        if (getMap().get(selection).size() <= 3) {
            machine.requestRestock();
        }
    }

    public HashMap<Integer, Queue> getMap() {
        return map;
    }
}
