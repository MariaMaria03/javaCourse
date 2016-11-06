package javalab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Box extends Container {
    private final int maxPile = 3;
    private final int maxCount = 3;
    // map заменить на list
    ArrayList<ArrayList> piles = new ArrayList<ArrayList>();
    //Map<Integer, List> piles = new HashMap<Integer, List>();
    
    public Box(String n, int w, String ... props) {
        super(n, w, props);
        
        // Создаем в коробке максимум пустых стопок
        for (int i = 0; i < maxPile; i++) {
            ArrayList<Item> pileColl = new ArrayList<Item>();
            piles.add(pileColl);
        }
    }
   
    public void addItem(Item elem, int numP) throws OverFlowException, AlreadyPlacedException {
        if (numP > maxPile) {
            System.out.println("Максимальное количество стопок: " + maxPile);
        }
        else if ((piles.get(numP)).size() > maxCount) {
            throw new OverFlowException("В коробку в стопку №" + numP + " больше складывать нельзя");
        }
        else if (elem.getInContainer()) {
            throw new AlreadyPlacedException(itemAlreadyPlaced(elem));
        }
        else {
            putInBox(elem, numP);
        }
//        else if (piles.isEmpty() || piles.get(numP) == null ||
//                            (piles.get(numP)).size() < maxCount) {
//            putInBox(elem, numP); 
//        }
//        else if (elem.getInContainer()) {
//            throw new AlreadyPlacedException(itemAlreadyPlaced(elem));
//        }
//        else {
//            throw new OverFlowException("В коробку в стопку №" + numP + " больше складывать нельзя");
//        }
    }
    
    private void putInBox(Item elem, int numP) {
        if (isFlat(elem)) {
//            if (piles.get(numP) == null) {
//                ArrayList<Item> pileColl = new ArrayList<Item>();
//                pileColl.add(elem);
//            }
                //piles.get(numP).add(pileColl);
            piles.get(numP).add(elem);
            currentWeight += elem.getWeight();
            elem.locationContainer();
        }
        else {
            System.out.println("Предмет " + elem.getName() +
                                    " не плоский, в стопку помещать нельзя");
        }
        
    }
    
    public void pullItem(int numP) {
        ArrayList<Item> pileColl = new ArrayList<Item>();
        pileColl = piles.get(numP);
        final int n = piles.get(numP).size();
        final Item lastElem = pileColl.get(n - 1);
        this.fromContainer(lastElem); 
        currentWeight -= lastElem.getWeight(); 
        piles.get(numP).remove(n - 1); 
    }
    
    @Override
    public void showAll() {
        System.out.println("Все предметы в контейнере " + this.getName());
        for (int x = 1; x < piles.size(); x++) {
            ArrayList<Item> pileColl = new ArrayList<Item>();
            System.out.println("Стопка № " + x);
            pileColl = piles.get(x);
            pileColl.forEach(i -> System.out.println("Предмет: " + i.getName()));
        }
    }   
}
