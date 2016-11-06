package javalab1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Item {
    private final String name;
    private final int weight;
    private final Set<String> otherProps;
    private boolean inContainer;
    
    public Item(String n, int w, String ... props) {
        name = n;
        weight = w;
        otherProps = new HashSet<String>(Arrays.asList(props));
        inContainer = false;
    }
    
    String getInfo() {
       return "Имя: " + name +
               "; Вес: " + weight +
               " Другие свойства" + otherProps; 
    }
    
    void locationContainer() {
        inContainer = !this.inContainer;
    }
    boolean getInContainer() {return inContainer;}
    Set<String> getProps() {return otherProps;}
    public String getName() {return name;}
    int getWeight() {return weight;}
    
    @Override
    public String toString() {
        return "Информация о предмете " + getInfo();
    }
}
