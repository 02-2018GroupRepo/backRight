package hello;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class InventoryManager {

    private HashMap<String,Queue> map;
    private Queue<Double> A1;
    private Queue<Double> A2;

    public InventoryManager() {
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

    public void vend(String selection) {
        //map.get(selection).peek()
    }
}
