package hello;

import java.util.*;

public class InventoryManager {

    private ArrayList<Product> productList;
    private HashMap<Integer,Queue> map;
//    private Queue<Product> A1;
//    private Queue<Product> A2;

    public InventoryManager() {
        productList = new ArrayList<>();
        map = new HashMap<>();
//        A1 = new LinkedList();
//        A2 = new LinkedList();
//       // A1.add(P); // Pretty sure this is the stub
//        map.put("A1", A1);
//        map.put("A2", A2);
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
            machine.returnMachineCoins(machine.getTotalAmount()-(double)map.get(selection).remove());
            machine.reset();
        }
        checkForRestock(machine);
    }

    public ArrayList<Product> retrieveProducts() {
        productList = ProductApi.retrieveProductList();
        for(int i = 0; i < productList.size(); i++) {
            Queue<Product> currentQueue = new ArrayDeque<Product>();
            for (int j = 0; j < 6; j++) {
                currentQueue.add(productList.get(i));
            }
            map.put(i + 1, currentQueue);
              System.out.println(productList.get(i).getName() + " " + productList.get(i).getRetailPrice());
        }
        System.out.println(map.toString());
        return productList;
    }
    public void checkForRestock(SoftMachine machine) {
        for (int i = 0; i < getAllStock().length; i++) {
            if (getAllStock()[i] <= 3) {
                System.out.println(getAllStock()[i]);
                machine.requestRestock();
            }
        }
    }
}
