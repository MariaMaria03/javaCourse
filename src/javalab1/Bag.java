package javalab1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Bag extends Container {
    private int maxWeight = 12000;
    
    public Bag(String n, int w, String ... props) {
        super(n, w, props);
        containerList = new ArrayList<Item>();
    }
   
    @Override
    public void addItem(Item elem) throws OverFlowException, AlreadyPlacedException {
        if ((currentWeight + elem.getWeight()) > maxWeight) {
            String messageError = "Мешок не выдержит, вес превышает допустимую норму,"
                                + "предмет " + elem.getName() + " не попадёт в мешок";
            throw new OverFlowException(messageError);
        }
            currentWeight += elem.getWeight();
            super.addItem(elem);
        
        System.out.println("Текущий вес мешка: " + currentWeight);
    }
    
    public void pullItem() {
        if (containerList.isEmpty()) {
            System.out.println("Мешок пустой");
        }
        else {
            Random random = new Random();
            int deletingElem = random.nextInt(containerList.size());
            Item elem = ((List<Item>)containerList).get(deletingElem);
//            Iterator iter = containerList.iterator();
//            for (int i = 0; i < containerList.size(); i++ ) {
//                iter.next();
//            }
            //iter.remove();
            currentWeight -= elem.getWeight();
            System.out.println("Из мешка вытащили предмет: " + elem.getName());
            elem.locationContainer();
            containerList.remove(elem);      
        }
    }
}
