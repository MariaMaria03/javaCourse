package javalab1;

import java.util.Collection;
import java.util.HashSet;

abstract class Container extends Item {
    
    // коллекцию сделать
    public Collection<Item> containerList;
    public int currentWeight = 0;

    Container(String n, int w, String ... props) {
        super(n, w, props);
        currentWeight = this.getWeight();
    }

    public void addItem(Item elem) throws OverFlowException, AlreadyPlacedException {
        containerList.add(elem);
        if (elem.getInContainer()) {
           throw new AlreadyPlacedException(itemAlreadyPlaced(elem)); 
        }
        elem.locationContainer();
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

    public void fromContainer(Item elem) {
        elem.locationContainer();
    }

    public String itemAlreadyPlaced(Item elem) {
        return "Предмет '" + elem.getName() + "' уже лежит в другом контейнере";
    }

    @Override
    public String toString() {
        return "Информация о предмете " + this.getInfo();
    }
    
}
