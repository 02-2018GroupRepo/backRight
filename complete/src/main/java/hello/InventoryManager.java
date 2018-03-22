package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class InventoryManager {

    private ArrayList<Product> productList;
    private HashMap<String,Queue> map;
    private Queue<Double> A1;
    private Queue<Double> A2;

    public InventoryManager() {
        productList = new ArrayList<>();
        map = new HashMap<>();
        A1 = new LinkedList();
        A2 = new LinkedList();
        A1.add(1d);
        map.put("A1", A1);
        map.put("A2", A2);
    }

    public int[] getAllStock() {
        return new int[]{map.get("A1").size(),map.get("A2").size()};
    }

    public double getItemPrice(String selection) {
        return (double)map.get(selection).peek();
    }

    public void vend(String selection,SoftMachine machine) {
        if (map.get(selection).peek() == null || machine.getTotalAmount() < (double)map.get(selection).peek()) {
            machine.reset();
        }

        else if (machine.getTotalAmount() == (double)map.get(selection).peek()) {
            map.get(selection).remove();
            machine.reset();
        }

        else if (machine.getTotalAmount() > (double)map.get(selection).peek()) {
            machine.returnMachineCoins(machine.getTotalAmount()-(double)map.get(selection).peek());
            map.get(selection).remove();
            machine.reset();
        }
    }

    public ArrayList<Product> retrieveProducts() {
        productList = ProductApi.retrieveProductList();
        for(int i = 0; i < productList.size(); i++) {
            System.out.println(productList.get(i).getName() + " " + productList.get(i).getRetailPrice());
        }
        return productList;
    }
}
