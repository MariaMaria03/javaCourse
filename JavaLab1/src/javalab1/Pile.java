package javalab1;

import java.util.ArrayList;
import java.util.List;

public class Pile extends Container {
    private int maxItems = 3;
    private int currentCount = 0;
    
    public Pile(String n, int w, String ... props) {
        super(n, w, props);
        containerList = new ArrayList<Item>();
    }
    
    @Override
    public void addItem(Item elem) throws ItemsException, AlreadyPlacedException {
        if (isFlat(elem)) {
            if (containerList.size() > maxItems) {
                throw new ItemsException("В стопку больше складывать нельзя");
            }
            super.addItem(elem); 
        }
        else {
            throw new ItemsException(itemNotFlat(elem));
        }
        System.out.println("Текущее количество предметов в стопке: " +
                                                    containerList.size());
    }
    
    @Override
    public Item pullItem() throws ItemsException {
        super.pullItem();
        int lastItem = containerList.size() - 1;
        Item item = ((List<Item>)containerList).get(lastItem);
        removeElement(item);
        return item;
    }
}
