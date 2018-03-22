package hello;

import java.util.*;

public class InventoryManager {

    private ArrayList<Product> productList;
    private HashMap<Integer,Queue> map;

    public InventoryManager() {
        productList = new ArrayList<>();
        map = new HashMap<>();
    }

//    public int[] getAllStock() {
//        int i = 0;
//        int[] allStock = new int[map.size()];
//        Iterator<Map.Entry<Integer, Queue>> map_iter = map.entrySet().iterator();
//        while(map_iter.hasNext()) {
//            Map.Entry<Integer, Queue> map_entry = map_iter.next();
//            allStock[i] = map_entry.getValue().size();
//            System.out.print(allStock[i] + " ");
//        }
//        System.out.println();
//        return allStock;
//    }

    public double getItemPrice(int selection) {
        return (double)map.get(selection).peek();
    }

    public void vend(int selection,SoftMachine machine) {
        ArrayDeque<Product> currentComp = (ArrayDeque)map.get(selection);
        if (currentComp.size() == 0) {
            System.out.println("Out of stock");
            machine.reset();
        }

        else if(machine.getTotalAmount() < currentComp.getFirst().getRetailPrice()) {
            System.out.println(currentComp.getFirst() + " is not vended");
            machine.reset();
        }

        else if (machine.getTotalAmount() == currentComp.getFirst().getRetailPrice()) {
            System.out.println(currentComp.getFirst() + " is vended");
            currentComp.remove();
            machine.reset();
        }

        else if (machine.getTotalAmount() > currentComp.getFirst().getRetailPrice()) {
            machine.returnMachineCoins(machine.getTotalAmount() - currentComp.getFirst().getRetailPrice());
            System.out.println(currentComp.getFirst() + " is vended");
            currentComp.remove();
            machine.reset();
        }
        checkForRestock(selection, machine);
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
//        for (int i = 0; i < getAllStock().length; i++) {
//            if (getAllStock()[i] <= 3) {
//                machine.requestRestock();
//            }
//        }
    }

    public HashMap<Integer, Queue> getMap() {
        return map;
    }
}
