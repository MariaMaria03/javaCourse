package javalab1;

import java.util.ArrayList;
import java.util.Collection;

public class Box extends Container {
    private final int maxPile = 3;
    private final int maxCount = 3;
    ArrayList<ArrayList> piles = new ArrayList<ArrayList>();
    
    public Box(String n, int w, String ... props) {
        super(n, w, props);
        
        // Создаем в коробке максимум возможных пустых стопок
        for (int i = 0; i < maxPile; i++) {
            ArrayList<Item> pileColl = new ArrayList<Item>();
            piles.add(pileColl);
        }
        containerList = new ArrayList<Item>();
    }
   
    public void addItem(Item elem, int numP) throws ItemsException, AlreadyPlacedException {
        if (numP > maxPile) {
            System.out.println("Максимальное количество стопок: " + maxPile);
        }
        if (this.getInContainer()) {
            throw new ItemsException(parentItemPlaced(elem, this.getName()));
        }
        if ((piles.get(numP)).size() > maxCount) {
            throw new ItemsException("В коробку в стопку №" + numP + " больше складывать нельзя");
        }
        else if (elem.getInContainer()) {
            throw new AlreadyPlacedException(itemAlreadyPlaced(elem));
        }
        else {
            putInBox(elem, numP);
        }
    }
    
    private void putInBox(Item elem, int numP) throws ItemsException {
        if (!isFlat(elem)) {
            throw new ItemsException(itemNotFlat(elem));
        }
        piles.get(numP).add(elem);
        containerList.add(elem);
        currentWeight += elem.getWeight();
        elem.itemToContainer();
    }
    
    public void pullItem(int numP) {
        ArrayList<Item> pileColl = new ArrayList<Item>();
        pileColl = piles.get(numP);
        final int n = piles.get(numP).size();
        final Item lastElem = pileColl.get(n - 1);
        lastElem.itemFromContainer();
        currentWeight -= lastElem.getWeight(); 
        piles.get(numP).remove(n - 1); 
    }
    
    @Override
    public void showAll() {
        System.out.println("Все предметы в контейнере " + this.getName() + " по стопкам");
        for (int x = 1; x < piles.size(); x++) {
            ArrayList<Item> pileColl = new ArrayList<Item>();
            System.out.println("Стопка № " + x);
            pileColl = piles.get(x);
            pileColl.forEach(i -> System.out.println("Предмет: " + i.getName()));
        }
        
        // вывод предметов подряд
        super.showAll();
    }   
}
