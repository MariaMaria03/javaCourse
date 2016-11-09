package javalab1;

import java.util.Collection;

abstract class Container extends Item {
    public Collection<Item> containerList;
    public int currentWeight = 0;

    Container(String n, int w, String ... props) {
        super(n, w, props);
        currentWeight = super.getWeight();
    }

    public void addItem(Item elem) throws ItemsException, AlreadyPlacedException {
        String contName = this.getName();
        
        if (elem == this) {
            throw new ItemsException("Ошибка! Предмет сам в себя класть нельзя");
        }
        if (this.getInContainer()) {
            throw new ItemsException(parentItemPlaced(elem, contName));
        }
        if (elem.getInContainer()) {
           throw new AlreadyPlacedException(itemAlreadyPlaced(elem)); 
        }
        containerList.add(elem);
        elem.itemToContainer();
    }

    public void showAll() {
        if (containerList.isEmpty()) {
            System.out.println("Предметов в контейнере нет");
        } else {
            System.out.println("Все предметы в контейнере " + this.getName());
            containerList.forEach((Item i) -> System.out.println("Предмет: " + i.getName()));
        }
    }

    public boolean isFlat(Item elem) {
        return elem.getProps().contains("плоский");
    }
    
    public String itemNotFlat(Item elem) {
        return "Предмет " + elem.getName() + " не плоский, в стопку помещать нельзя";
    }

    public String itemAlreadyPlaced(Item elem) {
        return "Предмет '" + elem.getName() + "' уже лежит в другом контейнере";
    }
    
    public String parentItemPlaced(Item elem, String parentItem) {
        return "Ошибка! Предмет " + elem.getName() + " нельзя поместить в контейнер '"
                + parentItem + "', так как " + parentItem + " уже лежит в другом контейнере";
    }
    
    @Override
    int getWeight() {
        return this.currentWeight;
    }

    @Override
    public String toString() {
        return "Информация о предмете " + this.getInfo();
    }
    
}
