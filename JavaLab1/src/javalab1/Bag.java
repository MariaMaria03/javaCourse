package javalab1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Bag extends Container {
    private int maxWeight = 10000;
    
    public Bag(String n, int w, String ... props) {
        super(n, w, props);
        containerList = new ArrayList<Item>();
    }
   
    @Override
    public void addItem(Item elem) throws ItemsException, AlreadyPlacedException {
        if ((currentWeight + elem.getWeight()) > maxWeight) {
            String messageError = "Мешок не выдержит, вес превышает допустимую норму,"
                                + "предмет " + elem.getName() + " не попадёт в мешок";
            throw new ItemsException(messageError);
        }
        super.addItem(elem);
        
        System.out.println("Текущий вес мешка: " + currentWeight);
    }
    
    @Override
    public Item pullItem() throws ItemsException{
        super.pullItem();
        Random random = new Random();
        int deletingElem = random.nextInt(containerList.size());
        Item elem = ((List<Item>)containerList).get(deletingElem);
        removeElement(elem);
        return elem;
    }

}
